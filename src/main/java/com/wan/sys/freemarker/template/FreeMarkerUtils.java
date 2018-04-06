package com.wan.sys.freemarker.template;

import freemarker.template.SimpleObjectWrapper;
import freemarker.template.TemplateModel;
import freemarker.template.TemplateModelException;
/**
 * 
 * 文件名称： FreeMarker模板参数处理
 * 内容摘要： 
 * 创建人： 唐君左
 * 创建日期： 2017-6-26
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
public class FreeMarkerUtils
{
	private static final SimpleObjectWrapper objectWrapper = new SimpleObjectWrapper();

	public static Object unwrap(Object model) throws TemplateModelException
	{
		if (model instanceof TemplateModel)
		{
			return objectWrapper.unwrap((TemplateModel) model);
		}
		else return model;
	}
	
	@SuppressWarnings("unchecked")
	public static <T> T unwrap(Object model, Class<T> clazz) throws TemplateModelException
	{
		if (model instanceof TemplateModel)
		{
			return ((T) objectWrapper.unwrap((TemplateModel) model, clazz));
		}
		else return null;
	}
}
