package com.wan.sys.freemarker.template;

import java.io.IOException;
import java.util.Map;

import org.apache.log4j.Logger;
import org.codehaus.jackson.map.ObjectMapper;



import freemarker.core.Environment;
import freemarker.template.TemplateDirectiveBody;
import freemarker.template.TemplateDirectiveModel;
import freemarker.template.TemplateException;
import freemarker.template.TemplateModel;

/**
 * FreeMarker自定义指令，用于将数据对象转换成json格式
 * 用法如下:<@json item=data />
 * 
 * @author Matt.U
 *
 */
public class JsonDirective implements TemplateDirectiveModel
{
	@SuppressWarnings("unused")
	private static final Logger logger = Logger.getLogger(JsonDirective.class);

	private static final String KEY_ITEM = "item";

	public final static String DIRECTIVE_NAME = "json";

	private ObjectMapper objectMapper;
	
	public JsonDirective()
	{
		objectMapper = new ObjectMapper();
		//objectMapper.registerModule(new HibernateModule());
	}

	@SuppressWarnings("rawtypes")
	public void execute(Environment env, Map params, TemplateModel[] loopVars, TemplateDirectiveBody body)
		throws TemplateException, IOException
	{
		Object item = params.get(KEY_ITEM);
		
		if (item != null)
		{
			item = FreeMarkerUtils.unwrap(item);

			String json = objectMapper.writeValueAsString(item);
			env.getOut().append(json);
		}
	}

}
