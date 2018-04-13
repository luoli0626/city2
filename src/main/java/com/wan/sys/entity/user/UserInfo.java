package com.wan.sys.entity.user;

import com.wan.sys.common.BaseEntity;
import com.wan.sys.entity.image.Image;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "sys_user2")
public class UserInfo extends BaseEntity {

    private String loginAcct;
    private String nickName;
    private List<Image> avatar;

    public String getLoginAcct() {
        return loginAcct;
    }

    public void setLoginAcct(String loginAcct) {
        this.loginAcct = loginAcct;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    @OneToMany(targetEntity = Image.class, cascade = CascadeType.ALL)
    @Fetch(FetchMode.JOIN)
    @JoinColumn(name = "BELONG_ID", updatable = false)
    public List<Image> getAvatar() {
        return avatar;
    }

    public void setAvatar(List<Image> avatar) {
        this.avatar = avatar;
    }
}
