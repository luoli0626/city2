package com.wan.sys.util;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.*;

import net.coobird.thumbnailator.Thumbnails;
import org.apache.commons.fileupload.*;
import org.apache.commons.fileupload.FileUploadBase.InvalidContentTypeException;
import org.apache.commons.fileupload.FileUploadBase.SizeLimitExceededException;
import org.apache.commons.fileupload.util.*;
import org.apache.commons.fileupload.servlet.*;
import org.apache.commons.fileupload.FileItemIterator;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;


import org.apache.commons.lang.StringUtils;
import org.codehaus.jackson.map.ObjectMapper;

import javax.servlet.http.HttpServletRequest;

/**
 * Justin Qiong
 */
public class Uploader {

	private static final String physicalRootPath = "/data/uploads";
	private static final String urlRootPath = "data/uploads";
	private static final ObjectMapper mapper = new ObjectMapper();

	// 文件大小限制，单位KB
	private int maxSize = 10000;

	// 文件允许格式
	private String[] allowFiles = { ".rar", ".doc", ".docx", ".zip", ".pdf",".txt", ".swf", ".wmv", ".gif", ".png", ".jpg", ".jpeg", ".bmp" };

	private HttpServletRequest request = null;

	private ServletFileUpload fileUpload;

	private String subPath;
	private String physicalPath;
	private String urlPath;

	public Uploader(HttpServletRequest request) {
		this.request = request;
		generatePhysicalPath();
		generateUrlPath();
		DiskFileItemFactory itemFactory = new DiskFileItemFactory();
		itemFactory.setRepository(new File(physicalPath));
		fileUpload = new ServletFileUpload(itemFactory);
		fileUpload.setSizeMax(maxSize * 1024);
		fileUpload.setHeaderEncoding(StandardCharsets.UTF_8.name());
	}

	public String upload() {
		boolean isMultipart = ServletFileUpload.isMultipartContent(request);
		UploadResult result = new UploadResult();
		if (!isMultipart) {
			result.setState(UploadResultEnum.NO_FILE.message());
		} else {
			try {
				FileItemIterator fileItems = fileUpload.getItemIterator(request);
				String originalFileName;
				while (fileItems.hasNext()) {
					FileItemStream fis = fileItems.next();
					if (!fis.isFormField()) {
						originalFileName = getOriginalName(fis.getName());
						if (!isFileTypeIncluded(originalFileName)) {
							result.setState(UploadResultEnum.TYPE.message());
							continue;
						}
						String newFileName = generateFileName(originalFileName);
						String url = urlPath + "/" + newFileName;
						BufferedInputStream in = new BufferedInputStream(fis.openStream());
                        String absFilePath = physicalPath + File.separator + newFileName;
						File file = new File(absFilePath);
						BufferedOutputStream output = new BufferedOutputStream(new FileOutputStream(file));
						Streams.copy(in, output, true);

                        //同时保存一份缩略图
                        Thumbnails.of(file).forceSize(50, 50).outputFormat("jpg").toFile(absFilePath + "_50x50");

						result.setState(UploadResultEnum.SUCCESS.message());
						result.setSize(file.length());
						result.setOriginalName(originalFileName);
						result.setName(newFileName);
						result.setType(getFileExt(originalFileName));
						result.setUrl(url);
						break;
					}
				}
			} catch (SizeLimitExceededException e) {
				result.setState(UploadResultEnum.SIZE.message());
			} catch (InvalidContentTypeException e) {
				result.setState(UploadResultEnum.EN_TYPE.message());
			} catch (FileUploadException e) {
				result.setState(UploadResultEnum.REQUEST.message());
			} catch (Exception e) {
				result.setState(UploadResultEnum.UNKNOWN.message());
			}
		}

		try {
			return mapper.writeValueAsString(result);
		} catch (IOException e) {
			e.printStackTrace();
			return StringUtils.EMPTY;
		}
	}

	private String getOriginalName(String filePath) {
		return filePath.substring(filePath.lastIndexOf(File.separator) + 1);
	}
	
	/**
	 * 文件类型判断
	 * 
	 * @param fileName
	 * @return
	 */
	private boolean isFileTypeIncluded(String fileName) {
		for (String ext : allowFiles) {
			if (fileName.toLowerCase().endsWith(ext)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * 获取文件扩展名
	 * 
	 * @return string
	 */
	private String getFileExt(String fileName) {
		return fileName.substring(fileName.lastIndexOf("."));
	}

	/**
	 * 依据原始文件名生成新文件名
	 * @return
	 */
	private String generateFileName(String originalName) {
		String fileName = UUID.randomUUID() + getFileExt(originalName);
		return fileName;
	}

	/**
	 *
	 */
	private String generateSubPath() {
		SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
		return format.format(new Date());
	}

	/**
	 *
	 */
	private void generatePhysicalPath() {

		if (StringUtils.isEmpty(subPath)) {
			subPath = generateSubPath();
		}

		String path = physicalRootPath + File.separator + subPath;
		File dir = new File(path);
		if (!dir.exists()) {
			dir.mkdirs();
		}

		physicalPath = path;
	}

	/**
	 *
	 */
	private void generateUrlPath() {

		if (StringUtils.isEmpty(subPath)) {
			subPath = generateSubPath();
		}
		urlPath = urlRootPath + "/" + subPath;
	}

	/**
	 *
	 * @param allowFiles
	 */
	public void setAllowFiles(String[] allowFiles) {
		this.allowFiles = allowFiles;
	}

	/**
	 *
	 * @param size
	 */
	public void setMaxSize(int size) {
		this.maxSize = size;
	}
}
