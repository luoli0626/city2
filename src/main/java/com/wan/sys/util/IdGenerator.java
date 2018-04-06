package com.wan.sys.util;
import java.io.Serializable;
import java.util.concurrent.atomic.AtomicLong;

import org.hibernate.HibernateException;
//import org.hibernate.engine.SessionImplementor;
import org.hibernate.engine.spi.SessionImplementor;
import org.hibernate.id.IdentifierGenerator;

public class IdGenerator implements IdentifierGenerator {

	private static AtomicLong id = new AtomicLong(System.currentTimeMillis());

	public Serializable generate(SessionImplementor session, Object object)
			throws HibernateException {
	if (object instanceof Persistent) {
		Persistent p = (Persistent) object;
		if (p.getId() != null)
		
			return Long.parseLong(p.getId());
		
	}
	
	//return String.valueOf(id.incrementAndGet());
	return Long.valueOf(id.incrementAndGet());

	}
	/**
	 * 返回一个跟id生成策略一样的long值
	 * @Description 
	 * @return Long
	 */
	public static Long getLongValue(){
		return id.incrementAndGet();
	}
	
}
