package com.mebay.bean;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;

@Api(description = "权限")
public class Role implements StreamTree<Role>{
    @ApiModelProperty(hidden = true)
    private Long id;
    @ApiModelProperty(value = "权限名称", required = true)
    private String name;
    @ApiModelProperty(value = "权限的中文名称（权限描述）")
    private String nameZh;
    @ApiModelProperty(hidden = true)
    private List<String> method;
    @ApiModelProperty(value = "权限的上一级id")
    private Long pid;
    private List<Role> children;

    public Role(String name) {
        this.name = name;
    }

    public Role() {}

    public String getNameZh() {
        return nameZh;
    }

    public void setNameZh(String nameZh) {
        this.nameZh = nameZh;
    }

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

    public List<String> getMethod() {
        return method;
    }

    public void setMethod(List<String> method) {
        this.method = method;
    }

    public List<Role> getChildren() {
        return children;
    }

    public void setChildren(List<Role> children) {
        this.children = children;
    }

    public Long getPid() {
        return pid;
    }

    public void setPid(Long pid) {
        this.pid = pid;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Role that = (Role) o;
        return name != null ? name.equals(that.name) : that.name == null;
    }

    public boolean equalsRole(String roleName) {
        return name != null ? name.equals(roleName) : roleName == null;
    }

    @Override
    public String toString() {
        return "Role{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", nameZh='" + nameZh + '\'' +
                ", method=" + method +
                '}';
    }

}
