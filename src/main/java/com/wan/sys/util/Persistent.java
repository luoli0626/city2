package com.wan.sys.util;

import java.io.Serializable;

@SuppressWarnings("serial")
public abstract class Persistent implements Serializable {
	public abstract String getId();
	
	public abstract void setId(String id);

    public boolean equals (Object x){
        if (x == null || "".equals(((Persistent) x).getId())) return false;
        
        return ((Persistent) x).getId() == getId();
    }
    
}
