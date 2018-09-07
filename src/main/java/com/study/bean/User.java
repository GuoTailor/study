package com.study.bean;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.sql.Date;
import java.util.*;

public class User implements UserDetails {
    private int id = 0;
    private String password;
    private String username;
    private String phone;
    private String sex;
    private String name;
    private Date creationTime;
    private Date updateTime;
    private Set<Role> authorities;

    public User() {}

    public User(String password, String username, String phone, String sex, String name) {
        this.password = password;
        this.username = username;
        this.phone = phone;
        this.sex = sex;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getCreationTime() {
        return creationTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setAuthorities(Set<Role> authorities) {
        this.authorities = authorities;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<GrantedAuthority> authorities = new ArrayList<>();
        for (Role role : this.authorities) {
            authorities.add(new SimpleGrantedAuthority(role.getName()));
            System.out.println(role.getName());
        }
        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    @JsonIgnore
    public String getRole() throws JsonProcessingException {
        String[] ss = authorities.stream().map(r -> r.getName().replace("ROLE_", "")).toArray(String[]::new);
        Arrays.stream(ss).forEach(s -> System.out.println(s + " >>>>>>>>>>>>>>>>>>>>>"));
        return new ObjectMapper().writeValueAsString(ss);
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", password='" + password + '\'' +
                ", username='" + username + '\'' +
                ", phone='" + phone + '\'' +
                ", sex='" + sex + '\'' +
                ", name='" + name + '\'' +
                ", creationTime=" + creationTime +
                ", updateTime=" + updateTime +
                ", authorities=" + authorities +
                '}';
    }
}
