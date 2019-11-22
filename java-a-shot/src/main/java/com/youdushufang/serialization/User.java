package com.youdushufang.serialization;

import java.beans.Transient;
import java.io.Serializable;

public class User implements Serializable {

    private Long id;

    private String name;

    private transient String password;

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

    @Transient
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Integer getAge() {
        return 18;
    }
}
