package com.wan.sys.service.common;

import com.wan.sys.entity.User;

/**
 * 基础业务层
 * @author lyf
 *
 */
public interface CommonServiceI {

	/**
	 * 判断token是否过期
	 * @param token
	 * @return
	 */
	public User tokenIsExpire(String token);
}
