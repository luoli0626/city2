package com.wan.sys.util;

import java.lang.reflect.Field;

import com.wan.sys.annotation.NotBlank;
import org.springframework.util.StringUtils;

public class ObjectBlankCheck{
	public static String check(Object obj){
		try{
			Field[] fields=obj.getClass().getDeclaredFields();
			for(Field field : fields){
				NotBlank meta=field.getAnnotation(NotBlank.class);
				if(null!=meta){
					field.setAccessible(true);
					Object value=field.get(obj);
					if(null==value|| StringUtils.isEmpty(value.toString())){
						return meta.value();
					}
				}
			}
		}catch(Exception e){
			return e.getMessage();
		}
		return null;
	}
}
