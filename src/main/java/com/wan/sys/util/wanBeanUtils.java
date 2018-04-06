package com.wan.sys.util;

import java.beans.PropertyDescriptor;
import java.beans.PropertyEditor;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.net.URI;
import java.net.URL;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.WeakHashMap;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeansException;
import org.springframework.beans.FatalBeanException;
import org.springframework.core.MethodParameter;
import org.springframework.util.Assert;
import org.springframework.util.ClassUtils;
import org.springframework.util.ReflectionUtils;
import org.springframework.util.StringUtils;

/**
 * Static convenience methods for JavaBeans: for instantiating beans, checking
 * bean property types, copying bean properties, etc.
 * 
 * <p>
 * Mainly for use within the framework, but to some degree also useful for
 * application classes.
 * 
 * @author Rod Johnson
 * @author Juergen Hoeller
 * @author Rob Harrop
 * @author Sam Brannen
 */
public abstract class wanBeanUtils {
	/**
	 * 
	 * @Description 对象属性复制 忽略部分属性的值
	 * @param source
	 * @param target
	 * @param ignoreProperties
	 * @throws BeansException void
	 */
	public static void copyProperties(Object source, Object target, String... ignoreProperties) throws BeansException {
		copyProperties(source, target, null, ignoreProperties);
	}
	/**
	 * 
	 * @Description 两个对象的属性完全复制
	 * @param source
	 * @param target
	 * @throws BeansException void
	 */
	public static void copyProperties(Object source, Object target) throws BeansException {
		copyProperties(source, target, null, (String[]) null);
	}
	/**
	 * 
	 * @Description  两个对象属性的复制 忽略掉为null的字段 
	 * @param source
	 * @param target
	 * @throws BeansException void
	 */
	public static void copyPropertiesIgnoreNull(Object source, Object target) throws BeansException {
		copyProperties(source, target, null, (String[]) null);
	}
	private static void copyProperties(Object source, Object target,
			Class<?> editable, String... ignoreProperties)
			throws BeansException {

		Assert.notNull(source, "Source must not be null");
		Assert.notNull(target, "Target must not be null");

		Class<?> actualEditable = target.getClass();
		if (editable != null) {
			if (!editable.isInstance(target)) {
				throw new IllegalArgumentException("Target class ["
						+ target.getClass().getName()
						+ "] not assignable to Editable class ["
						+ editable.getName() + "]");
			}
			actualEditable = editable;
		}
		PropertyDescriptor[] targetPds = BeanUtils.getPropertyDescriptors(actualEditable);
		List<String> ignoreList = (ignoreProperties != null) ? Arrays
				.asList(ignoreProperties) : null;

		for (PropertyDescriptor targetPd : targetPds) {
			Method writeMethod = targetPd.getWriteMethod();
			if (writeMethod != null
					&& (ignoreProperties == null || (!ignoreList
							.contains(targetPd.getName())))) {
				PropertyDescriptor sourcePd = BeanUtils.getPropertyDescriptor(source
						.getClass(), targetPd.getName());
				if (sourcePd != null) {
					Method readMethod = sourcePd.getReadMethod();
					if (readMethod != null
							&& ClassUtils.isAssignable(writeMethod
									.getParameterTypes()[0], readMethod
									.getReturnType())) {
						try {
							if (!Modifier.isPublic(readMethod
									.getDeclaringClass().getModifiers())) {
								readMethod.setAccessible(true);
							}
							Object value = readMethod.invoke(source);
							if (!Modifier.isPublic(writeMethod
									.getDeclaringClass().getModifiers())) {
								writeMethod.setAccessible(true);
							}
							//忽略空值属性
							if(value!=null && !value.equals("")){
								writeMethod.invoke(target, value);
							}
						} catch (Throwable ex) {
							throw new FatalBeanException(
									"Could not copy property '"
											+ targetPd.getName()
											+ "' from source to target", ex);
						}
					}
				}
			}
		}
	}
	

}