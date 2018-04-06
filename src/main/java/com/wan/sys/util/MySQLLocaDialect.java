/**
 *
 */
package com.wan.sys.util;

import org.hibernate.dialect.MySQL5Dialect;
import org.hibernate.dialect.function.SQLFunctionTemplate;
import org.hibernate.type.StringType;

/**   
 * 文件名称： 
 * 内容摘要： 
 * 创建人： 唐君左
 * 创建日期： 2017-3-3
 * 版本号： v1.0.0
 * 公  司：金科物业服务有限公司
 * 版权所有： (C)2016-2017     
 * 修改记录1 
 * 修改日期：
 * 版本号：
 * 修改人：
 * 修改内容：  
 **/
public class MySQLLocaDialect extends MySQL5Dialect {
	public MySQLLocaDialect(){  
        super();  
        registerFunction("convert_gbk", new SQLFunctionTemplate(StringType.INSTANCE, "convert(?1 using gbk)"));  
    }  

}
