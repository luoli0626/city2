package com.wan.sys.freemarker.template;

import java.io.IOException;
import java.util.Map;

import com.wan.sys.freemarker.template.OverrideDirective.TemplateDirectiveBodyOverrideWraper;

import freemarker.core.Environment;
import freemarker.template.TemplateDirectiveBody;
import freemarker.template.TemplateDirectiveModel;
import freemarker.template.TemplateException;
import freemarker.template.TemplateModel;

/**
 * 
 * 文件名称： FreeMarker自定义指令，在模板中定义块，可以被子模板用@override指令覆盖显示<br>
 * 内容摘要：用法如下：<@block name="header">this is header</@block><br> 引自RapidFramework项目
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
public class BlockDirective implements TemplateDirectiveModel
{
	public final static String DIRECTIVE_NAME = "block";

	@SuppressWarnings("rawtypes")
	public void execute(Environment env, Map params, TemplateModel[] loopVars, TemplateDirectiveBody body)
		throws TemplateException, IOException
	{
		String name = DirectiveUtils.getRequiredParam(params, "name");
		TemplateDirectiveBodyOverrideWraper overrideBody = DirectiveUtils.getOverrideBody(env, name);
		if (overrideBody == null)
		{
			if (body != null)
			{
				body.render(env.getOut());
			}
		}
		else
		{
			DirectiveUtils.setTopBodyForParentBody(env, new TemplateDirectiveBodyOverrideWraper(body, env),
				overrideBody);
			overrideBody.render(env.getOut());
		}
	}

}
