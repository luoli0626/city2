package com.wan.sys.util;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFClientAnchor;
import org.apache.poi.hssf.usermodel.HSSFComment;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFPatriarch;
import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.util.CellRangeAddress;
import org.apache.poi.hssf.util.Region;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.DateUtil;
/**
 * 
 * 文件名称： excel 工具类
 * 内容摘要： 对excel单元格操作或是获取数据
 * 创建人： tangjunzuo
 * 创建日期： 2017年2月15日
 * 版本号： v1.0.0
 * 公  司：金科物业服务有限公司
 * 版权所有： (C)2016-2017     
 * 修改记录1 
 * 修改日期：
 * 版本号：
 * 修改人：
 * 修改内容：  
 *
 */
public class PoiUtil {

    /** 
    * 把单元格内的类型转换至String类型 
    */
   public static String ConvertCellStr(Cell cell) {
	   String cellStr="";
       switch (cell.getCellType()) {  
          
       case Cell.CELL_TYPE_STRING:  
           // 读取String  
           cellStr = cell.getStringCellValue().toString();
           break;  
       case Cell.CELL_TYPE_BOOLEAN:  
           // 得到Boolean对象的方法  
           cellStr = String.valueOf(cell.getBooleanCellValue());  
           break;  
  
       case Cell.CELL_TYPE_NUMERIC:  
 
           // 先看是否是日期格式  
           if (DateUtil.isCellDateFormatted(cell)) {  
  
               // 读取日期格式  
               cellStr = com.wan.sys.util.DateUtil.format(cell.getDateCellValue(), "yyyy-MM-dd");  
  
           } else {  
  
               // 读取数字  
               cellStr = Double.toString(cell.getNumericCellValue());  
           }  
           break;  
  
       case Cell.CELL_TYPE_FORMULA:  
           // 读取公式  
           cellStr = cell.getCellFormula().toString();  
           break;  
       }  
       return cellStr;  
   } 
   
   private static void setCellComment(HSSFPatriarch p,Cell cell,String value)
   {
	 //设置批注对象
		HSSFComment comment = p.createCellComment(new HSSFClientAnchor(0,0,0,0,(short)3,3,(short)5,6));
		//批注内容
		comment.setString(new HSSFRichTextString(value));
		//设置批注
		cell.setCellComment(comment);
		
		return;
   }
   
   /**
    * 构造学生报表表头,并添加例子 不分班级的所有学生
    * @param sheet 
    * @param isBlank 传入true为构造例子,false不构造
    */
   public static void buildTitleall(HSSFSheet sheet,boolean isBlank){
	 //报表标题字体
		 HSSFFont font = sheet.getWorkbook().createFont();
		 font.setFontName("黑体");
		 font.setFontHeightInPoints((short) 64);//设置字体大小
		 font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);//粗体显示
		//标题单元格格式
		 sheet.addMergedRegion(new CellRangeAddress(0,0,0,6));//标题合并列
		//创建报表
		HSSFRow row = sheet.createRow(0);
		row.setHeightInPoints(42);
		
		HSSFCell titlecel = row.createCell(0);
		titlecel.setCellValue("学生信息列表");
		HSSFCellStyle columnStyle = sheet.getWorkbook().createCellStyle();
		columnStyle.setFont(font);
		columnStyle.setAlignment(CellStyle.ALIGN_CENTER);
		
		//设置批注
		//创建绘图对象
		HSSFPatriarch p = sheet.createDrawingPatriarch(); 
		
		//表头行
		row = sheet.createRow(1);
		for(int i=0;i<8;++i)
		{
			HSSFCell cel = row.createCell(i);
			
			switch (i) {
				case 0:
					sheet.setColumnWidth(i, (short)((35.7)*140));
					cel.setCellValue("班级名称");
					break;
				case 1:
					sheet.setColumnWidth(i, (short)((35.7)*140));
					cel.setCellValue("姓名");
					break;
				case 2:
					cel.setCellValue("性别");
					setCellComment(p,cel,"男/女");
					break;
				
				case 3:
					sheet.setColumnWidth(i, (short)((35.7)*140));
					cel.setCellValue("入学月份");

					break;
				case 4:
					sheet.setColumnWidth(i, (short)((35.7)*140));
					cel.setCellValue("学制");
					break;
				
				case 5:
					sheet.setColumnWidth(i, (short)((35.7)*140));
					cel.setCellValue("备注");
					break;
				case 6:
					sheet.setColumnWidth(i, (short)((35.7)*200));
					cel.setCellValue("证件号码(尾数没有X请在号码前加')");
					break;
			}
		}
		//是否到处空白报表
		if(!isBlank)//是
		{
			return;
		}
		//例子
		row = sheet.createRow(2);
		for(int i=0;i<8;++i)
		{
			HSSFCell cel = row.createCell(i);
			switch (i) {
			case 0:
				cel.setCellValue("汽车维修（教改）2010-1");
				break;
			case 1:
				cel.setCellValue("李四");
				break;
			case 2:
				cel.setCellValue("男");
				break;
			
			case 3:
				cel.setCellValue("3或者9");
				break;
			case 4:
				cel.setCellValue("3");
				break;
			case 5:
				cel.setCellValue("新生");
				break;
			case 6:
				cel.setCellValue("500229198001013210");
				break;
				
			}
		}
   }
   
   /**
    * 表头
    * @param sheet
    * @param isBlank
    */
   public static void buildTitleError(HSSFSheet sheet,boolean isBlank){

		 //报表标题字体
			 HSSFFont font = sheet.getWorkbook().createFont();
			 font.setFontName("黑体");
			 font.setFontHeightInPoints((short) 64);//设置字体大小
			 font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);//粗体显示
			//标题单元格格式
			// sheet.addMergedRegion(new CellRangeAddress(0,0,0,3));//标题合并列
			//创建报表
			HSSFRow row = sheet.createRow(0);
			//设置批注
			//创建绘图对象
			HSSFPatriarch p = sheet.createDrawingPatriarch(); 
			
			//表头行
			row = sheet.createRow(0);
			for(int i=0;i<4;++i)
			{
				HSSFCell cel = row.createCell(i);
				
				switch (i) {
					
					case 0:
						sheet.setColumnWidth(i, (short)((35.7)*140));
						cel.setCellValue("登录名");
						break;
					case 1:
						sheet.setColumnWidth(i, (short)((35.7)*140));
						cel.setCellValue("项目名称");
						break;
					
					case 2:
						sheet.setColumnWidth(i, (short)((35.7)*140));
						cel.setCellValue("场景名称");

						break;
					case 3:
						sheet.setColumnWidth(i, (short)((35.7)*140));
						cel.setCellValue("角色名称");
						break;
				}
			}
			//是否到处空白报表
			if(!isBlank)//是
			{
				return;
			}
			//例子
			row = sheet.createRow(2);
			for(int i=0;i<4;++i)
			{
				HSSFCell cel = row.createCell(i);
				switch (i) {
				
				case 0:
					cel.setCellValue("tjz");
					break;
				case 1:
					cel.setCellValue("10年城");
					break;
				
				case 2:
					cel.setCellValue("工程标准");
					break;
				case 3:
					cel.setCellValue("项目管理人员");
					break;
					
				}
			}
	   
   }
   
   
   /**
    * 构造学生报表表头,并添加例子
    * @param sheet 
    * @param isBlank 传入true为构造例子,false不构造
    */
   public static void buildTitle(HSSFSheet sheet,boolean isBlank){
	 //报表标题字体
		 HSSFFont font = sheet.getWorkbook().createFont();
		 font.setFontName("黑体");
		 font.setFontHeightInPoints((short) 64);//设置字体大小
		 font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);//粗体显示
		//标题单元格格式
		 sheet.addMergedRegion(new CellRangeAddress(0,0,0,5));//标题合并列
		//创建报表
		HSSFRow row = sheet.createRow(0);
		row.setHeightInPoints(42);
		
		HSSFCell titlecel = row.createCell(0);
		titlecel.setCellValue("学生信息列表");
		HSSFCellStyle columnStyle = sheet.getWorkbook().createCellStyle();
		columnStyle.setFont(font);
		columnStyle.setAlignment(CellStyle.ALIGN_CENTER);
		
		//设置批注
		//创建绘图对象
		HSSFPatriarch p = sheet.createDrawingPatriarch(); 
		
		//表头行
		row = sheet.createRow(1);
		for(int i=0;i<6;++i)
		{
			HSSFCell cel = row.createCell(i);
			
			switch (i) {
				
				case 0:
					sheet.setColumnWidth(i, (short)((35.7)*140));
					cel.setCellValue("姓名");
					break;
				case 1:
					cel.setCellValue("性别");
					setCellComment(p,cel,"男/女");
					break;
				
				case 2:
					sheet.setColumnWidth(i, (short)((35.7)*140));
					cel.setCellValue("入学月份");

					break;
				case 3:
					sheet.setColumnWidth(i, (short)((35.7)*140));
					cel.setCellValue("学制");
					break;
				
				case 4:
					sheet.setColumnWidth(i, (short)((35.7)*140));
					cel.setCellValue("备注");
					break;
				case 5:
					sheet.setColumnWidth(i, (short)((35.7)*200));
					cel.setCellValue("证件号码(尾数没有X请在号码前加')");
					break;
			}
		}
		//是否到处空白报表
		if(!isBlank)//是
		{
			return;
		}
		//例子
		row = sheet.createRow(2);
		for(int i=0;i<6;++i)
		{
			HSSFCell cel = row.createCell(i);
			switch (i) {
			
			case 0:
				cel.setCellValue("李四");
				break;
			case 1:
				cel.setCellValue("男");
				break;
			
			case 2:
				cel.setCellValue("3或者9");
				break;
			case 3:
				cel.setCellValue("3");
				break;
			case 4:
				cel.setCellValue("新生");
				break;
			case 5:
				cel.setCellValue("500229198001013210");
				break;
				
			}
		}
   }
   /**
    * 构造课程报表表头,并添加例子
    * @param sheet 
    * @param isBlank 传入true为构造例子,false不构造
    */
   public static void buildTitle2(HSSFSheet sheet,boolean isBlank){
	 //报表标题字体
		 HSSFFont font = sheet.getWorkbook().createFont();
		 font.setFontName("黑体");
		 font.setFontHeightInPoints((short) 64);//设置字体大小
		 font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);//粗体显示
		//标题单元格格式
		 sheet.addMergedRegion(new CellRangeAddress(0,0,0,3));//标题合并列
		//创建报表
		HSSFRow row = sheet.createRow(0);
		row.setHeightInPoints(42);
		
		HSSFCell titlecel = row.createCell(0);
		titlecel.setCellValue("课程信息列表");
		HSSFCellStyle columnStyle = sheet.getWorkbook().createCellStyle();
		columnStyle.setFont(font);
		columnStyle.setAlignment(CellStyle.ALIGN_CENTER);
		
		//设置批注
		//创建绘图对象
		HSSFPatriarch p = sheet.createDrawingPatriarch(); 
		
		//表头行
		row = sheet.createRow(1);
		for(int i=0;i<4;++i)
		{
			HSSFCell cel = row.createCell(i);
			
			switch (i) {
				case 0:
					sheet.setColumnWidth(i, (short)((35.7)*140));
					cel.setCellValue("班级");
					break;
				case 1:
					sheet.setColumnWidth(i, (short)((35.7)*140));
					cel.setCellValue("课程名称");
					break;
				case 2:
                    sheet.setColumnWidth(i, (short)((35.7)*140));
					cel.setCellValue("学期");
					break;
				case 3:
					sheet.setColumnWidth(i, (short)((35.7)*140));
					cel.setCellValue("教师(登录帐号)");
					break;
			}
		}
		//是否到处空白报表
		if(!isBlank)//是
		{
			return;
		}
		//例子
		row = sheet.createRow(2);
		for(int i=0;i<4;++i)
		{
			HSSFCell cel = row.createCell(i);
			switch (i) {
			case 0:
				cel.setCellValue("2015计算机一班");
				break;
			case 1:
				cel.setCellValue("语文");
				break;
			case 2:
				cel.setCellValue("2015上半学期");
				break;
			case 3:
				cel.setCellValue("admin");
				break;
			}
		}
   }

   /**
    * 构造教师报表表头,并添加例子 
    * @param sheet 
    * @param isBlank 传入true为构造例子,false不构造
    */
   public static void buildTitle1(HSSFSheet sheet,boolean isBlank){
	 //报表标题字体
		 HSSFFont font = sheet.getWorkbook().createFont();
		 font.setFontName("黑体");
		 font.setFontHeightInPoints((short) 64);//设置字体大小
		 font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);//粗体显示
		//标题单元格格式
		 sheet.addMergedRegion(new CellRangeAddress(0,0,0,8));//标题合并列
		//创建报表
		HSSFRow row = sheet.createRow(0);
		row.setHeightInPoints(42);
		
		HSSFCell titlecel = row.createCell(0);
		titlecel.setCellValue("教师信息列表");
		HSSFCellStyle columnStyle = sheet.getWorkbook().createCellStyle();
		columnStyle.setFont(font);
		columnStyle.setAlignment(CellStyle.ALIGN_CENTER);
		
		//设置批注
		//创建绘图对象
		HSSFPatriarch p = sheet.createDrawingPatriarch(); 
		
		//表头行
		row = sheet.createRow(1);
		for(int i=0;i<9;++i)
		{
			HSSFCell cel = row.createCell(i);
			
			switch (i) {
				case 0:
					sheet.setColumnWidth(i, (short)((35.7)*140));
					cel.setCellValue("教师姓名");
					break;
				case 1:
					sheet.setColumnWidth(i, (short)((35.7)*140));
					cel.setCellValue("性别");
					setCellComment(p,cel,"男/女");
					break;
				case 2:
                    sheet.setColumnWidth(i, (short)((35.7)*140));
					cel.setCellValue("最高学历");
					break;
				case 3:
					sheet.setColumnWidth(i, (short)((35.7)*140));
					cel.setCellValue("职称");
					break;
				case 4:
					sheet.setColumnWidth(i, (short)((35.7)*140));
					cel.setCellValue("手机");
					break;
				case 5:
					sheet.setColumnWidth(i, (short)((35.7)*140));
					cel.setCellValue("地址");

					break;
				case 6:
					sheet.setColumnWidth(i, (short)((35.7)*140));
					cel.setCellValue("电子邮箱");
					break;
				case 7:
					sheet.setColumnWidth(i, (short)((35.7)*140));
					cel.setCellValue("身份证号码");
					break;
				case 8:
					sheet.setColumnWidth(i, (short)((35.7)*140));
					cel.setCellValue("所属系部");
					break;
			}
		}
		//是否到处空白报表
		if(!isBlank)//是
		{
			return;
		}
		//例子
		row = sheet.createRow(2);
		for(int i=0;i<9;++i)
		{
			HSSFCell cel = row.createCell(i);
			switch (i) {
			case 0:
				cel.setCellValue("张三(该行数据不会导入,仅作提示用)");
				break;
			case 1:
				cel.setCellValue("男");
				break;
			case 2:
				cel.setCellValue("博士");
				break;
			case 3:
				cel.setCellValue("高级教师");
				break;
			case 4:
				cel.setCellValue("13111098921");
				break;
			case 5:
				cel.setCellValue("重庆市");
				break;
			case 6:
				cel.setCellValue("11@qq.com");
				break;
			case 7:
				cel.setCellValue("50022519851102634X");
				break;
			case 8:
				cel.setCellValue("机械工程系");
				break;
			
				
			}
		}
   }
   
   /**
    * 构造教师报表表头,并添加例子 
    * @param sheet 
    * @param isBlank 传入true为构造例子,false不构造
    */
   public static void buildTitle3(HSSFSheet sheet,boolean isBlank){
	 //报表标题字体
		 HSSFFont font = sheet.getWorkbook().createFont();
		 font.setFontName("黑体");
		 font.setFontHeightInPoints((short) 64);//设置字体大小
		 font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);//粗体显示
		//标题单元格格式
		 sheet.addMergedRegion(new CellRangeAddress(0,0,0,4));//标题合并列
		//创建报表
		HSSFRow row = sheet.createRow(0);
		row.setHeightInPoints(42);
		
		HSSFCell titlecel = row.createCell(0);
		titlecel.setCellValue("班级信息列表");
		HSSFCellStyle columnStyle = sheet.getWorkbook().createCellStyle();
		columnStyle.setFont(font);
		columnStyle.setAlignment(CellStyle.ALIGN_CENTER);
		
		//设置批注
		//创建绘图对象
		HSSFPatriarch p = sheet.createDrawingPatriarch(); 
		
		//表头行
		row = sheet.createRow(1);
		for(int i=0;i<5;++i)
		{
			HSSFCell cel = row.createCell(i);
			
			switch (i) {
				case 0:
					sheet.setColumnWidth(i, (short)((35.7)*140));
					cel.setCellValue("班级名称");
					break;
				case 1:
					sheet.setColumnWidth(i, (short)((35.7)*140));
					cel.setCellValue("入学年份");
					break;
				case 2:
                    sheet.setColumnWidth(i, (short)((35.7)*140));
					cel.setCellValue("专业名称");
					break;
				case 3:
					sheet.setColumnWidth(i, (short)((35.7)*140));
					cel.setCellValue("班级");
					break;
				case 4:
					sheet.setColumnWidth(i, (short)((35.7)*140));
					cel.setCellValue("班主任");
					break;
			}
		}
		//是否到处空白报表
		if(!isBlank)//是
		{
			return;
		}
		//例子
		row = sheet.createRow(2);
		for(int i=0;i<5;++i)
		{
			HSSFCell cel = row.createCell(i);
			switch (i) {
			case 0:
				cel.setCellValue("电气自动化技术2011-1");
				break;
			case 1:
				cel.setCellValue("2011");
				break;
			case 2:
				cel.setCellValue("电气自动化技术");
				break;
			case 3:
				cel.setCellValue("一班");
				break;
			case 4:
				cel.setCellValue("510001");
				break;
			}
		}
   }
   
   
   public static void excelscore(HSSFSheet sheet){
		 //报表标题字体
			 HSSFFont font = sheet.getWorkbook().createFont();			 
			 font.setFontName("黑体");
			//设置字体大小
			 font.setFontHeightInPoints((short) 16);
			//粗体显示
			 font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
			//标题单元格格式
			 sheet.addMergedRegion(new CellRangeAddress(0, 0, 0,9));//标题合并列
			//创建报表
			HSSFRow row = sheet.createRow(0);
			row.setHeightInPoints(20);
			HSSFCell titlecel = row.createCell(0);
			titlecel.setCellValue("学生成绩");
			HSSFCellStyle columnStyle = sheet.getWorkbook().createCellStyle();
			columnStyle.setFont(font);
			 // 设置单元格居中对齐  
			columnStyle.setAlignment(CellStyle.ALIGN_CENTER);
			 // 设置单元格垂直居中对齐  
			columnStyle.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
		       
			row = sheet.createRow(1);
			for(int i=0;i<10;i++)
			{
				HSSFCell cel = row.createCell(i);
				switch (i) {
				case 0:
					cel.setCellValue("身份证号码");
					break;
				case 1:
					cel.setCellValue("学号");
					break;
				case 2:
					cel.setCellValue("姓名");
					break;
				case 3:
					cel.setCellValue("学期");
					break;
				case 4:
					cel.setCellValue("专业");
					break;
				case 5:
					cel.setCellValue("科目");
					break;
				case 6:
					cel.setCellValue("平时成绩");
					break;
				case 7:
					cel.setCellValue("半期成绩");
					break;								
				case 8:
					cel.setCellValue("期末成绩");
					break;
				case 9:
					cel.setCellValue("考核分数");
					break;
				
				}
			}
	   }
   public static void projectCount(HSSFSheet sheet,String projectNane){
		 //报表标题字体
		 HSSFFont font = sheet.getWorkbook().createFont();			 
		 font.setFontName("黑体");
		//设置字体大小
		 font.setFontHeightInPoints((short) 16);
		//粗体显示
		 font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
		//标题单元格格式
		 sheet.addMergedRegion(new CellRangeAddress(0, 0, 0,6));//标题合并列
		//创建报表
			HSSFRow row = sheet.createRow(0);
			row.setHeightInPoints(20);
			HSSFCell titlecel = row.createCell(0);
			titlecel.setCellValue(projectNane+"统计表");
			HSSFCellStyle columnStyle = sheet.getWorkbook().createCellStyle();
			HSSFCellStyle columnStyle1 = sheet.getWorkbook().createCellStyle();
			// 设置单元格居中对齐  
			columnStyle1.setAlignment(CellStyle.ALIGN_CENTER);
						 // 设置单元格垂直居中对齐  
			columnStyle1.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
			columnStyle.setFont(font);
			 // 设置单元格居中对齐  
			columnStyle.setAlignment(CellStyle.ALIGN_CENTER);
			 // 设置单元格垂直居中对齐  
			columnStyle.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
			titlecel.setCellStyle(columnStyle);
			row = sheet.createRow(1);
			for(int i=0;i<7;i++)
			{
				HSSFCell cel = row.createCell(i);
				cel.setCellStyle(columnStyle1);
				switch (i) {
				case 0:
					cel.setCellValue("员工姓名");
					break;
				case 1:
					cel.setCellValue("应发现问题量");
					break;
				case 2:
					cel.setCellValue("实际发现问题量	");
					break;
				case 3:
					cel.setCellValue("达标率");
					break;
				case 4:
					cel.setCellValue("基准值");
					break;
				case 5:
					cel.setCellValue("计算得分");
					break;
				case 6:
					cel.setCellValue("最终得分");
					break;
				
				}
			}
	   }
   
   
   public static void branchCount(HSSFSheet sheet,String projectNane){
		 //报表标题字体
		 HSSFFont font = sheet.getWorkbook().createFont();			 
		 font.setFontName("黑体");
		//设置字体大小
		 font.setFontHeightInPoints((short) 16);
		//粗体显示
		 font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
		//标题单元格格式
		 sheet.addMergedRegion(new CellRangeAddress(0, 0, 0,9));//标题合并列
		//创建报表
			HSSFRow row = sheet.createRow(0);
			row.setHeightInPoints(20);
			HSSFCell titlecel = row.createCell(0);
			titlecel.setCellValue(projectNane+"统计表");
			HSSFCellStyle columnStyle = sheet.getWorkbook().createCellStyle();
			HSSFCellStyle columnStyle1 = sheet.getWorkbook().createCellStyle();
			columnStyle1.setAlignment(CellStyle.ALIGN_CENTER);
			 // 设置单元格垂直居中对齐  
			columnStyle1.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
			columnStyle.setFont(font);
			 // 设置单元格居中对齐  
			columnStyle.setAlignment(CellStyle.ALIGN_CENTER);
			 // 设置单元格垂直居中对齐  
			columnStyle.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
			titlecel.setCellStyle(columnStyle); 
			row = sheet.createRow(1);
			for(int i=0;i<10;i++)
			{
				HSSFCell cel = row.createCell(i);
				cel.setCellStyle(columnStyle1);
				switch (i) {
				case 0:
					cel.setCellValue("项目名称");
					break;
				case 1:
					cel.setCellValue("应发现问题量");
					break;
				case 2:
					cel.setCellValue("实际发现问题量");
					break;
				case 3:
					cel.setCellValue("问题发现评分");
					break;
				case 4:
					cel.setCellValue("正在整改量");
					break;
				case 5:
					cel.setCellValue("整改率");
					break;
				case 6:
					cel.setCellValue("完成量");
					break;
				case 7:
					cel.setCellValue("关闭率");
					break;
				case 8:
					cel.setCellValue("得分");
					break;
				case 9:
					cel.setCellValue("名次");
					break;
					
				
				}
			}
	   }
   
   
   
   public static void export(HSSFSheet sheet){
	   	//报表标题字体
		 HSSFFont font = sheet.getWorkbook().createFont();			 
		 font.setFontName("宋体");
		//设置字体大小
		 font.setFontHeightInPoints((short) 11);
		//粗体显示
		 font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
		//标题单元格格式
		// sheet.addMergedRegion(new CellRangeAddress(0, 0, 0,2));//标题合并列
		//创建报表
			HSSFRow row = sheet.createRow(0);
			row.setHeightInPoints(20);
			//HSSFCell titlecel = row.createCell(0);
			//titlecel.setCellValue("数据导入信息表");
			HSSFCellStyle columnStyle = sheet.getWorkbook().createCellStyle();
			HSSFCellStyle columnStyle1 = sheet.getWorkbook().createCellStyle();
			columnStyle1.setAlignment(CellStyle.ALIGN_CENTER);
			columnStyle1.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
			columnStyle.setFont(font);
			 // 设置单元格居中对齐  
			columnStyle.setAlignment(CellStyle.ALIGN_CENTER);
			 // 设置单元格垂直居中对齐  
			columnStyle.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
			//titlecel.setCellStyle(columnStyle);
			row = sheet.createRow(0);
			for(int i=0;i<30;i++)
			{
				HSSFCell cel = row.createCell(i);
				cel.setCellStyle(columnStyle1);
				switch (i) {
				case 0:
					cel.setCellValue("项目名称");
					break;
				case 1:
					cel.setCellValue("设备系统");
					break;
				case 2:
					cel.setCellValue("设备类别");
					break;
				case 3:
					cel.setCellValue("设备名称");
					break;
				case 4:
					cel.setCellValue("设备编号");
					break;
				case 5:
					cel.setCellValue("所在位置");
					break;
				case 6:
					cel.setCellValue("规格");
					break;
				case 7:
					cel.setCellValue("型号");
					break;
				case 8:
					cel.setCellValue("启用日期");
					break;
				case 9:
					cel.setCellValue("责任部门");
					break;
				case 10:
					cel.setCellValue("责任人");
					break;
				case 11:
					cel.setCellValue("质保到期");
					break;
				case 12:
					cel.setCellValue("报废期限");
					break;
				case 13:
					cel.setCellValue("年审日期");
					break;
				case 14:
					cel.setCellValue("生产厂商");
					break;
				case 15:
					cel.setCellValue("产地");
					break;
				case 16:
					cel.setCellValue("出厂编号");
					break;
				case 17:
					cel.setCellValue("出厂日期");
					break;
				case 18:
					cel.setCellValue("供应商");
					break;
				case 19:
					cel.setCellValue("合同编号");
					break;
				case 20:
					cel.setCellValue("安装合同开始日期");
					break;
				case 21:
					cel.setCellValue("安装合同结束日期");
					break;
				case 22:
					cel.setCellValue("安装单位");
					break;
				case 23:
					cel.setCellValue("安装日期");
					break;
				case 24:
					cel.setCellValue("维保单位");
					break;
				case 25:
					cel.setCellValue("维保合同开始日期");
					break;
				case 26:
					cel.setCellValue("维保合同结束日期");
					break;
				case 27:
					cel.setCellValue("维修单位");
					break;
				case 28:
					cel.setCellValue("归档编号");
					break;
				case 29:
					cel.setCellValue("备注");
					break;
				}
			}
	   
   }
   
   public static void exportPoint(HSSFSheet sheet){
	   	//报表标题字体
		 HSSFFont font = sheet.getWorkbook().createFont();			 
		 font.setFontName("宋体");
		//设置字体大小
		 font.setFontHeightInPoints((short) 11);
		//粗体显示
		 font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
		//标题单元格格式
		// sheet.addMergedRegion(new CellRangeAddress(0, 0, 0,2));//标题合并列
		//创建报表
			HSSFRow row = sheet.createRow(0);
			row.setHeightInPoints(20);
			//HSSFCell titlecel = row.createCell(0);
			//titlecel.setCellValue("数据导入信息表");
			HSSFCellStyle columnStyle = sheet.getWorkbook().createCellStyle();
			HSSFCellStyle columnStyle1 = sheet.getWorkbook().createCellStyle();
			columnStyle1.setAlignment(CellStyle.ALIGN_CENTER);
			columnStyle1.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
			columnStyle.setFont(font);
			 // 设置单元格居中对齐  
			columnStyle.setAlignment(CellStyle.ALIGN_CENTER);
			 // 设置单元格垂直居中对齐  
			columnStyle.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
			//titlecel.setCellStyle(columnStyle);
			row = sheet.createRow(0);
			for(int i=0;i<30;i++)
			{
				HSSFCell cel = row.createCell(i);
				cel.setCellStyle(columnStyle1);
				switch (i) {
				case 0:
					cel.setCellValue("项目名称");
					break;
				case 1:
					cel.setCellValue("设备编号");
					break;
				case 2:
					cel.setCellValue("设备系统");
					break;
				case 3:
					cel.setCellValue("设备类别");
					break;
				case 4:
					cel.setCellValue("设备名称");
					break;
				case 5:
					cel.setCellValue("点位名称");
					break;
				case 6:
					cel.setCellValue("卡号");
					break;
				case 7:
					cel.setCellValue("位置描述");
					break;
				case 8:
					cel.setCellValue("是否启用");
					break;
				case 9:
					cel.setCellValue("备注");
					break;
				case 10:
					cel.setCellValue("错误信息");
					break;
				
				}
			}
	   
  }
  
   public static void exportDetailed(HSSFSheet sheet){
		 //报表标题字体
		 HSSFFont font = sheet.getWorkbook().createFont();			 
		 font.setFontName("黑体");
		//设置字体大小
		 font.setFontHeightInPoints((short) 16);
		//粗体显示
		 font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
		//标题单元格格式
		 sheet.addMergedRegion(new CellRangeAddress(0, 0, 0,25));//标题合并列
		//创建报表
			HSSFRow row = sheet.createRow(0);
			row.setHeightInPoints(20);
			HSSFCell titlecel = row.createCell(0);
			titlecel.setCellValue("项目明细");
			HSSFCellStyle columnStyle = sheet.getWorkbook().createCellStyle();
			HSSFCellStyle columnStyle1 = sheet.getWorkbook().createCellStyle();
			columnStyle1.setAlignment(CellStyle.ALIGN_CENTER);
			columnStyle1.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
			columnStyle.setFont(font);
			 // 设置单元格居中对齐  
			columnStyle.setAlignment(CellStyle.ALIGN_CENTER);
			 // 设置单元格垂直居中对齐  
			columnStyle.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
			titlecel.setCellStyle(columnStyle);
			row = sheet.createRow(1);
			for(int i=0;i<26;i++)
			{
				HSSFCell cel = row.createCell(i);
				cel.setCellStyle(columnStyle1);
				switch (i) {
				case 0:
					cel.setCellValue("项目名称");
					break;
				case 1:
					cel.setCellValue("场景名称");
					break;
				case 2:
					cel.setCellValue("检查人姓名");
					break;
				case 3:
					sheet.addMergedRegion(new CellRangeAddress(1, 1, 3,7));
					cel.setCellValue("检查人上传图片");
					break;
				case 8:
					cel.setCellValue("检查人描述");
					break;
				case 9:
					cel.setCellValue("检查时间");
					break;
				case 10:
					cel.setCellValue("解决人姓名");
					break;
				case 11:
					sheet.addMergedRegion(new CellRangeAddress(1, 1, 11,15));
					cel.setCellValue("解决人上传图片");
					break;
				case 16:
					cel.setCellValue("解决人描述");
					break;
				case 17:
					cel.setCellValue("解决时间");
					break;
				case 18:
					sheet.addMergedRegion(new CellRangeAddress(1, 1, 18,22));
					cel.setCellValue("知识库图片");
					break;
				case 23:
					cel.setCellValue("知识库描述");
					break;
				case 24:
					cel.setCellValue("审核时间");
					break;
				case 25:
					cel.setCellValue("审核情况");
					break;
				
				}
			}
	   }
   public static void ExcelscoreCount(HSSFSheet sheet){
		 //报表标题字体
			 HSSFFont font = sheet.getWorkbook().createFont();			 
			 font.setFontName("黑体");
			//设置字体大小
			 font.setFontHeightInPoints((short) 16);
			//粗体显示
			 font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
			//标题单元格格式
			 sheet.addMergedRegion(new CellRangeAddress(0, 0, 0,9));//标题合并列
			//创建报表
			HSSFRow row = sheet.createRow(0);
			row.setHeightInPoints(20);
			HSSFCell titlecel = row.createCell(0);
			titlecel.setCellValue("学生成绩分析表");
			HSSFCellStyle columnStyle = sheet.getWorkbook().createCellStyle();
			columnStyle.setFont(font);
			 // 设置单元格居中对齐  
			columnStyle.setAlignment(CellStyle.ALIGN_CENTER);
			 // 设置单元格垂直居中对齐  
			columnStyle.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
		       
			row = sheet.createRow(1);
			for(int i=0;i<10;i++)
			{
				HSSFCell cel = row.createCell(i);
				switch (i) {
				case 0:
					cel.setCellValue("老师");
					break;
				case 1:
					cel.setCellValue("班级");
					break;
				case 2:
					cel.setCellValue("科目");
					break;
				case 3:
					cel.setCellValue("学期");
					break;
				case 4:
					cel.setCellValue("总数");
					break;
			
				
				}
			}
	   }
}
