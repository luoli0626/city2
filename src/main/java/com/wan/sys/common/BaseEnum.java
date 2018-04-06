package com.wan.sys.common;


/**
 * 
 * 文件名称：基本枚举 
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
public interface BaseEnum<K> {

    /** 
     * 得到枚举对应的id,一般保存这个id至数据库
     * @return K
     */
    public K getId();
    /**
     * 得到枚举描述 
     * @return String
     */
    public String getName();
    /**
     * 枚举名称 
     * @return String
     */
    public String name();
    
}

