package com.wan.sys.freemarker.template;

import java.util.HashMap;
import java.util.Properties;
import java.util.Set;

import freemarker.ext.beans.BeansWrapper;
import freemarker.template.TemplateHashModel;

/**
 * 
 * 文件名称： freemarker调用java静态方法配置 
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
public class FreemarkerStaticModels extends HashMap<Object, Object> {

	private static final long serialVersionUID = -8629014133398061128L;
	
	private static FreemarkerStaticModels FREEMARKER_STATIC_MODELS;
    /**静态类配置文件*/
    private Properties staticModels;

    private FreemarkerStaticModels(){
       
    }

    public static FreemarkerStaticModels getInstance(){
        if(FREEMARKER_STATIC_MODELS==null){
            FREEMARKER_STATIC_MODELS=new FreemarkerStaticModels();
        }
        return FREEMARKER_STATIC_MODELS;
    }
   
    public Properties getStaticModels() {
        return staticModels;
    }

    public void setStaticModels(Properties staticModels) {
        if(this.staticModels==null&&staticModels!=null){
            this.staticModels = staticModels;
            Set<String> keys=this.staticModels.stringPropertyNames();
            for (String key : keys) {
                FREEMARKER_STATIC_MODELS.put(key, useStaticPackage(this.staticModels.getProperty(key)));
            }
        }
    }

    public static TemplateHashModel useStaticPackage(String packageName){
        try
        {
          BeansWrapper wrapper = BeansWrapper.getDefaultInstance();
          TemplateHashModel staticModels = wrapper.getStaticModels();
          TemplateHashModel fileStatics = (TemplateHashModel) staticModels.get(packageName);
          return fileStatics;
        }
        catch (Exception e)
        {
           e.printStackTrace(); 
        }
        return null;
    }

}
