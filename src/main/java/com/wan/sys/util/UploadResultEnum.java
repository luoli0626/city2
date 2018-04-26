package com.wan.sys.util;

public enum UploadResultEnum {

		SUCCESS("SUCCESS"),
        NO_FILE("未包含文件上传域"),
        TYPE("不允许的文件格式"),
        SIZE("文件大小超出限制"),
        EN_TYPE("请求类型ENTYPE错误"),
        REQUEST("上传请求异常"),
        IO("IO异常"),
        DIR("目录创建失败"),
        UNKNOWN("未知错误");

        private String message;
		UploadResultEnum(String message) {
            this.message = message;
        }

        public String message() {
		    return message;
        }
}
