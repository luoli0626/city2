package com.wan.sys.freemarker.template;

import java.io.IOException;
import java.util.Map;

import freemarker.cache.TemplateCache;
import freemarker.core.Environment;
import freemarker.template.TemplateDirectiveBody;
import freemarker.template.TemplateDirectiveModel;
import freemarker.template.TemplateException;
import freemarker.template.TemplateModel;

/**
 * 
 * FreeMarker自定义指令，继承其它模板，<b>必须放在模板的最后面</b>（注：该指令完全等价于#include指令，只是为了提供统一的语义，即extends比include更好理解）<br>
 * 用法如下：<@extends name="headr.ftl"/>  
 * 引自RapidFramework项目
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
public class ExtendsDirective implements TemplateDirectiveModel
{

	public final static String DIRECTIVE_NAME = "extends";

	@SuppressWarnings("rawtypes")
	public void execute(Environment env, Map params, TemplateModel[] loopVars, TemplateDirectiveBody body)
		throws TemplateException, IOException
	{

		String name = DirectiveUtils.getRequiredParam(params, "name");
		String encoding = DirectiveUtils.getParam(params, "encoding", null);
		String includeTemplateName = TemplateCache.getFullTemplatePath(env, getTemplatePath(env), name);
		env.include(includeTemplateName, encoding, true);
	}

	private String getTemplatePath(Environment env)
	{
		String templateName = env.getTemplate().getName();
		return templateName.lastIndexOf('/') == -1 ? "" : templateName.substring(0, templateName.lastIndexOf('/') + 1);
	}

}
