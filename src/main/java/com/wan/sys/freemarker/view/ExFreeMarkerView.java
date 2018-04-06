package com.wan.sys.freemarker.view;

import java.util.Locale;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.web.servlet.view.freemarker.FreeMarkerView;

import com.wan.sys.freemarker.template.BlockDirective;
import com.wan.sys.freemarker.template.ExtendsDirective;
import com.wan.sys.freemarker.template.FlexJsonDirective;
import com.wan.sys.freemarker.template.OverrideDirective;
import com.wan.sys.freemarker.template.SuperDirective;



/**
 * 扩展默认的FreeMarkerView，并加入了自定义的指令
 * 自定义指令包括如下：json、
 * 
 * @author Matt.U
 *
 */
public class ExFreeMarkerView extends FreeMarkerView
{
private static final Logger logger = Logger.getLogger(ExFreeMarkerView.class);
	
	private static final String KEY_CONTEXT_PATH = "ctx";
	private static final String KEY_STATIC_RESOURCE = "static";
	
	@Override
	protected void exposeHelpers(Map<String, Object> model, HttpServletRequest request) throws Exception
	{
		model.put(FlexJsonDirective.DIRECTIVE_NAME, new FlexJsonDirective());
		model.put(ExtendsDirective.DIRECTIVE_NAME, new ExtendsDirective());
		model.put(BlockDirective.DIRECTIVE_NAME, new BlockDirective());
		model.put(OverrideDirective.DIRECTIVE_NAME, new OverrideDirective());
		model.put(SuperDirective.DIRECTIVE_NAME, new SuperDirective());
//		model.put(AuthzDirective.DIRECTIVE_NAME, new AuthzDirective(request, getServletContext()));
		String contextPath = request.getContextPath();
		String staticResourcePath = contextPath + "/static"; //TODO 以后需要增加对该参数的设置
		model.put(KEY_CONTEXT_PATH, contextPath);
		if (logger.isDebugEnabled())
			logger.debug("Init freeMarker vars: ${ctx}=" + contextPath);
		
		Set<?> vars = getConfiguration().getSharedVariableNames();
		if (!vars.contains(KEY_STATIC_RESOURCE))
		{
			model.put(KEY_STATIC_RESOURCE, staticResourcePath);
			if (logger.isDebugEnabled())
				logger.debug("Init freeMarker vars: ${static}=" + staticResourcePath);
		}
		
		super.exposeHelpers(model, request);
	}
	
	@Override
	public boolean checkResource(Locale locale) throws Exception
	{
		return super.checkResource(locale);
	}

}
