package com.wan.sys.freemarker.template;

import java.util.Map;


import org.apache.commons.lang.StringUtils;

import com.wan.sys.freemarker.template.OverrideDirective.TemplateDirectiveBodyOverrideWraper;

import freemarker.core.Environment;
import freemarker.template.TemplateException;
import freemarker.template.TemplateModelException;

/**
 * 
 * 文件名称： 
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
public class DirectiveUtils
{

	public static String BLOCK = "__ftl_override__";

	public static String OVERRIDE_CURRENT_NODE = "__ftl_override_current_node";

	public static String getOverrideVariableName(String name)
	{
		return BLOCK + name;
	}

	@SuppressWarnings("rawtypes")
	static String getRequiredParam(Map params, String key) throws TemplateException
	{
		Object value = params.get(key);
		if (value == null || StringUtils.isEmpty(value.toString())) { throw new TemplateModelException(
			"not found required parameter:" + key + " for directive"); }
		return value.toString();
	}

	@SuppressWarnings("rawtypes")
	static String getParam(Map params, String key, String defaultValue) throws TemplateException
	{
		Object value = params.get(key);
		return value == null ? defaultValue : value.toString();
	}

	static TemplateDirectiveBodyOverrideWraper getOverrideBody(Environment env, String name)
		throws TemplateModelException
	{
		TemplateDirectiveBodyOverrideWraper value = (TemplateDirectiveBodyOverrideWraper) env
			.getVariable(DirectiveUtils.getOverrideVariableName(name));
		return value;
	}

	static void setTopBodyForParentBody(Environment env, TemplateDirectiveBodyOverrideWraper topBody,
		TemplateDirectiveBodyOverrideWraper overrideBody)
	{
		TemplateDirectiveBodyOverrideWraper parent = overrideBody;
		while (parent.parentBody != null)
		{
			parent = parent.parentBody;
		}
		parent.parentBody = topBody;
	}
}
