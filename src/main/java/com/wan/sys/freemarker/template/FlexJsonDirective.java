package com.wan.sys.freemarker.template;

import java.io.IOException;
import java.util.Map;

import org.apache.log4j.Logger;
import freemarker.core.Environment;
import freemarker.template.TemplateDirectiveBody;
import freemarker.template.TemplateDirectiveModel;
import freemarker.template.TemplateException;
import freemarker.template.TemplateModel;

/**
 * FreeMarker自定义指令，用于将数据对象转换成json格式<br> 
 * 用法如下:<@json item=data include=["id"] exclude=["*"] /><br>
 * include的优先级高于exclude，所以上面这个json只会输出id
 * 
 * @author Matt.U
 */
public class FlexJsonDirective implements TemplateDirectiveModel
{
	@SuppressWarnings("unused")
	private static final Logger logger = Logger.getLogger(FlexJsonDirective.class);

	private static final String KEY_ITEM = "item";

	private static final String KEY_INCLUDE = "include";

	private static final String KEY_EXCLUDE = "exclude";

	public final static String DIRECTIVE_NAME = "json";

	@SuppressWarnings("rawtypes")
	public void execute(Environment env, Map params, TemplateModel[] loopVars, TemplateDirectiveBody body)
		throws TemplateException, IOException
	{
		Object item = params.get(KEY_ITEM);
		String[] include = null;
		String[] exclude = null;

		if (item != null)
		{
			item = FreeMarkerUtils.unwrap(item);
			Object v = FreeMarkerUtils.unwrap(params.get(KEY_INCLUDE), String[].class);
			if (v instanceof String[]) include = (String[]) v;
			
			v = FreeMarkerUtils.unwrap(params.get(KEY_EXCLUDE), String[].class);
			if (v instanceof String[]) exclude = (String[]) v;

			String json = toJson(item, include, exclude);
			env.getOut().append(json);
		}
	}

	protected String toJson(Object item, String[] include, String[] exclude)
	{
//		JSONSerializer jsonSerializer = new JSONSerializer();
//		jsonSerializer.exclude("*.class");
//		if (include != null && include.length > 0) jsonSerializer.include(include);
//		if (exclude != null && exclude.length > 0) jsonSerializer.exclude(exclude);
//		return jsonSerializer.serialize(item);
		return "";
	}

}
