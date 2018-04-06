package com.wan.sys.freemarker.template;
import java.io.IOException;
import java.io.Writer;
import java.util.Map;

import freemarker.core.Environment;
import freemarker.template.TemplateDirectiveBody;
import freemarker.template.TemplateDirectiveModel;
import freemarker.template.TemplateException;
import freemarker.template.TemplateModel;

/**
 * FreeMarker自定义指令，覆盖@block指令显示的内容<br>
 * 用法如下：<@override name="header">this is another header</@override><br>
 * 引自RapidFramework项目
 * 
 * @author Matt.U
 */
public class OverrideDirective implements TemplateDirectiveModel
{

	public final static String DIRECTIVE_NAME = "override";

	@SuppressWarnings("rawtypes")
	public void execute(Environment env, Map params, TemplateModel[] loopVars, TemplateDirectiveBody body)
		throws TemplateException, IOException
	{
		String name = DirectiveUtils.getRequiredParam(params, "name");
		String overrideVariableName = DirectiveUtils.getOverrideVariableName(name);

		TemplateDirectiveBodyOverrideWraper override = DirectiveUtils.getOverrideBody(env, name);
		TemplateDirectiveBodyOverrideWraper current = new TemplateDirectiveBodyOverrideWraper(body, env);
		if (override == null)
		{
			env.setVariable(overrideVariableName, current);
		}
		else
		{
			DirectiveUtils.setTopBodyForParentBody(env, current, override);
		}
	}

	static class TemplateDirectiveBodyOverrideWraper implements TemplateDirectiveBody, TemplateModel
	{
		private TemplateDirectiveBody body;

		public TemplateDirectiveBodyOverrideWraper parentBody;

		public Environment env;

		public TemplateDirectiveBodyOverrideWraper(TemplateDirectiveBody body, Environment env)
		{
			super();
			this.body = body;
			this.env = env;
		}

		public void render(Writer out) throws TemplateException, IOException
		{
			if (body == null) return;
			TemplateDirectiveBodyOverrideWraper preOverridy = (TemplateDirectiveBodyOverrideWraper) env
				.getVariable(DirectiveUtils.OVERRIDE_CURRENT_NODE);
			try
			{
				env.setVariable(DirectiveUtils.OVERRIDE_CURRENT_NODE, this);
				body.render(out);
			}
			finally
			{
				env.setVariable(DirectiveUtils.OVERRIDE_CURRENT_NODE, preOverridy);
			}
		}
	}

}
