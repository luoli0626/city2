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
 * FreeMarker自定义指令，和@override指令联用，用于在子模板中引用父模板中block的内容<br>
 * 用法如下：<@override name="header"><br>
 * 				this is another header<br>
 * 				<@super /><br>
 * 			</@override><br>
 * 引自RapidFramework项目
 * 
 * @author Matt.U
 */
public class SuperDirective implements TemplateDirectiveModel
{
	public final static String DIRECTIVE_NAME = "super";

	@SuppressWarnings("rawtypes")
	public void execute(Environment env, Map params, TemplateModel[] loopVars, TemplateDirectiveBody body)
		throws TemplateException, IOException
	{

		TemplateDirectiveBodyOverrideWraper current = (TemplateDirectiveBodyOverrideWraper) env
			.getVariable(DirectiveUtils.OVERRIDE_CURRENT_NODE);
		if (current == null) { throw new TemplateException("<@super/> direction must be child of override", env); }
		TemplateDirectiveBody parent = current.parentBody;
		
		if (parent == null) { throw new TemplateException("not found parent for <@super/>", env); }
		parent.render(env.getOut());

	}

}
