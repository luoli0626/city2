package com.wan.sys.entity;

import java.util.Date;

public interface CreateModifiedable {
    void setCreateTime(Date date);
    void setModifyTime(Date date);
}
