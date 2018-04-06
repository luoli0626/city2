package com.wan.sys.freemarker.template;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.ObjectUtils;
import org.apache.log4j.Logger;

import freemarker.core.Environment;
import freemarker.template.Template;
import freemarker.template.TemplateDirectiveBody;
import freemarker.template.TemplateDirectiveModel;
import freemarker.template.TemplateException;
import freemarker.template.TemplateModel;

public class RenderDirective implements TemplateDirectiveModel
{
	@SuppressWarnings("unused")
	private static final Logger logger = Logger.getLogger(RenderDirective.class);

	private static final String KEY_NAME = "name";
	
	private static final String KEY_CONTEXT = "context";
	
	public final static String DIRECTIVE_NAME = "render";

	@SuppressWarnings("rawtypes")
	public void execute(Environment env, Map params, TemplateModel[] loopVars, TemplateDirectiveBody body)
		throws TemplateException, IOException
	{
		String templateName = ObjectUtils.toString(params.get(KEY_NAME));
		Object context = params.get(KEY_CONTEXT);
		context = FreeMarkerUtils.unwrap(context);
		Map<String, Object> rootMap = new HashMap<String, Object>();
		rootMap.put("good", context);
		Template template = env.getConfiguration().getTemplate(templateName);
		template.process(rootMap, env.getOut());
		env.getOut().flush();
	}

}
