package com.liuyi.web.Model;

import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 权限分配角色表
 * <p>
 */
@Entity
@Table(name = "user_auth_role")
@DynamicUpdate
public class UserAuthRole {
    @Id
    @GeneratedValue
    private Long id;
    private String name; // 必须为：ROLE_ 开头，比如：ROLE_ADMIN

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
