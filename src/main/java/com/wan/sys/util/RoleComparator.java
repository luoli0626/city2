package com.wan.sys.util;

import java.util.Comparator;

import org.apache.log4j.Logger;

import com.wan.sys.entity.Role;

/**
 * 角色排序
 * 
 * @author  
 * 
 */
public class RoleComparator implements Comparator<Role> {

	private static final Logger logger = Logger.getLogger(RoleComparator.class);

	public int compare(Role o1, Role o2) {
		int i1 = o1.getSeq() != null ? o1.getSeq().intValue() : -1;
		int i2 = o2.getSeq() != null ? o2.getSeq().intValue() : -1;
		logger.debug("i1[" + i1 + "]-i2[" + i2 + "]=" + (i1 - i2));
		return i1 - i2;
	}

}
