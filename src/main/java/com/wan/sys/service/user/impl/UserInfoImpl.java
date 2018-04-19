package com.wan.sys.service.user.impl;

import com.wan.sys.dao.user.IUserInfoDao;
import com.wan.sys.entity.user.UserInfo;
import com.wan.sys.service.user.IUserInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserInfoImpl implements IUserInfoService {

    @Autowired
    IUserInfoDao userInfoDao;

    @Override
    public UserInfo getById(Long id) {
        if (id != null && id > 0) {
            return userInfoDao.get(UserInfo.class, id);
        }

        return null;
    }
}
