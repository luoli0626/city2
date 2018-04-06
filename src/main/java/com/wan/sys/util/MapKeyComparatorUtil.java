package com.wan.sys.util;

import java.util.Comparator;

public class MapKeyComparatorUtil implements Comparator<String>{
    public int compare(String str1, String str2) {  
        return str1.compareTo(str2);  
    }  
}
