package com.mebay.bean;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiParam;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.sql.Date;
import java.util.*;
import java.util.stream.Collectors;

@ApiModel(description = "用户")
public class User implements UserDetails {
    @ApiModelProperty(hidden = true)
    private Long id;
    @ApiModelProperty(value = "用户名", required = true, example = "test")
    private String username;
    @ApiModelProperty(value = "用户密码", required = true, example = "admin")
    private String password;
    @ApiModelProperty(value = "手机号")
    private String phone;
    @ApiModelProperty(value = "性别")
    private String sex;
    @ApiModelProperty(value = "真实姓名", required = true)
    private String name;
    @ApiModelProperty(hidden = true)
    private Date creationTime;
    @ApiModelProperty(hidden = true)
    private Date updateTime;
    @ApiModelProperty(value = "属于哪个单位（单位id）", required = true)
    private Long depId;
    @ApiModelProperty(value = "权限集合", hidden = true)
    private List<Role> authorities;
    @ApiModelProperty(value = "logo路径", hidden = true)
    private String logo;
    @ApiModelProperty(value = "邮箱")
    private String email;
    @ApiModelProperty(value = "上级单位名", hidden = true)    //仅提供前端显示用
    private String superiorName;
    @JsonIgnore
    @ApiModelProperty(value = "旧密码")    //仅提供前端修改密码用
    private String oldPassword;

    public User() {
    }

    public User(String password, String username, String phone, String sex, String name) {
        this.password = password;
        this.username = username;
        this.phone = phone;
        this.sex = sex;
        this.name = name;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
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

    public Long getDepId() {
        return depId;
    }

    public void setDepId(Long depId) {
        this.depId = depId;
    }

    public void setAuthorities(List<Role> authorities) {
        this.authorities = authorities;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSuperiorName() {
        return superiorName;
    }

    public void setSuperiorName(String superiorName) {
        this.superiorName = superiorName;
    }

    public String getOldPassword() {
        return oldPassword;
    }

    public void setOldPassword(String oldPassword) {
        this.oldPassword = oldPassword;
    }

    @Override
    @JsonIgnore
    @ApiModelProperty(hidden = true)
    public Collection<? extends GrantedAuthority> getAuthorities() {
        if (this.authorities == null)
            return null;
        return authorities.parallelStream().map(a -> new SimpleGrantedAuthority(a.getName())).collect(Collectors.toList());

    }

    @ApiModelProperty(hidden = true)
    @JsonIgnore
    public static Collection<? extends GrantedAuthority> creationAut(List<String> aut) {
        List<GrantedAuthority> authorities = new ArrayList<>();
        for (String role : aut) {
            authorities.add(new SimpleGrantedAuthority(role));
        }
        return authorities;
    }

    @ApiModelProperty(hidden = true)
    @ApiParam(name = "role",hidden = true)
    public List<Role> getRole() {
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

    @ApiModelProperty(hidden = true)
    @Override
    @JsonIgnore
    public boolean isAccountNonExpired() {
        return true;
    }

    @ApiModelProperty(hidden = true)
    @Override
    @JsonIgnore
    public boolean isAccountNonLocked() {
        return true;
    }

    @ApiModelProperty(hidden = true)
    @Override
    @JsonIgnore
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @ApiModelProperty(hidden = true)
    @Override
    @JsonIgnore
    public boolean isEnabled() {
        return true;
    }

    @ApiModelProperty(hidden = true)
    @JsonIgnore
    public String getRoleString() throws JsonProcessingException {
        String[] ss = authorities.stream().map(Role::getName).toArray(String[]::new);
        String s = new ObjectMapper().writeValueAsString(ss);
        System.out.println(s);
        return s;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(id, user.id) &&
                Objects.equals(username, user.username);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, username);
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
