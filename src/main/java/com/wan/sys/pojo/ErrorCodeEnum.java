package com.wan.sys.pojo;

public enum ErrorCodeEnum {
	SUCCESS("操作成功","200"),
	FAIL("操作失败","1000"),
	FAIL_PARAMSISNULL("参数为空！","1001"),
	FAIL_NOREGISTER("没有注册","1002"),
	FAIL_GETTOKEN("获取token失败","1003"),
	FAIL_NOGRANT("没有权限","1004"),
	FAIL_TOKENLOSE("token失效","1005"),
	FAIL_DEVICELIMIT("设备已达上限","1006"),
	FAIL_ADDDEVICE("添加设备失败","1007"),
	FAIL_NOTEXISTENT("对象不存在","1008"),
	FAIL_NOUSER("没有该用户","1009"),
	FAIL_NOPROJECT("项目不存在","1010"),
	FAIL_PASS("密码错误","1011"),
	FAIL_CODE("验证码错误","1012"),
	FAIL_EXISTPHONE("手机号已被注册","1013"),
	FAIL_USER("用户名或密码错误","1014"),

    FAIL_INVALIDPARAM("参数验证失败","3001"),

    FAIL_IOERR("IO异常", "4001"),
	FAIL_NULLFILE("未读取到文件", "4002"),
	FAIL_NORESULT("没有匹配的结果", "5001"),
	FAIL_REMOVEPHOTO("只有未被处理的随手拍可以删除","2001");
	
    // 成员变量  
    private String value;
    private String code;
    // 构造方法  
    private ErrorCodeEnum(String value, String code) {
    	this.value = value;
    	this.code = code;
    }
    // 普通方法  
    public static String getValue(String code) {  
        for (ErrorCodeEnum e : ErrorCodeEnum.values()) {  
            if (e.getCode().equals(code)) {  
                return e.getValue();  
            }  
        }  
        return null;  
    }  
    // get set 方法  
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
}
