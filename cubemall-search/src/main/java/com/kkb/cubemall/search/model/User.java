package com.kkb.cubemall.search.model;

import java.util.Date;

/**
 *
 */
public class User {


    private Integer id;
    private String name;
    private String address;
    private Date birthday;

    public User(Integer id, String name, String address) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.birthday = null;
    }
    public User(Integer id, String name, String address, Date birthday) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.birthday = birthday;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
