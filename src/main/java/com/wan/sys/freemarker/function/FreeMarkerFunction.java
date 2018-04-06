package com.wan.sys.freemarker.function;

import java.util.List;

import com.wan.sys.common.GlobalContext;
import com.wan.sys.entity.Menu;
import com.wan.sys.util.StringUtil;

/**
 * 
 ** 文件名称：ftl文件中调用的自定义静态方法 
 * 内容摘要： ftl调用示例:fmfn.方法名（参数） 
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
public class FreeMarkerFunction {


	/**
	 * 校验按钮权限 
	 * @param url 当前页面uri
	 * @param flag 当前按钮标识,与easyui按钮的iconCls属性值一致
	 * @return boolean
	 */
	public static boolean checkButton (String url,String flag){
		final String FUNCTION_FLAG = "1";
		List<Menu> menus  = GlobalContext.getCurrentUser().getMenus();
		Long pid = null;
		String contextPath = GlobalContext.getRequest().getContextPath();
		for (Menu menu : menus) {
			if(StringUtil.isEquals(contextPath + menu.getSrc(), url)){
				pid = menu.getId();
				break;
			}
		}
		if(pid!=null){
			for (Menu menu : menus) {
				if(pid.equals(menu.getPid())&&StringUtil.isEquals(menu.getFunction(), FUNCTION_FLAG)&&StringUtil.isEquals(menu.getIconcls(), flag)){
					return true;
				}
			}
		}
		return false;
	}
}
