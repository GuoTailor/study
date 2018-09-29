package com.mebay.bean;


import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;

@Api(description = "权限")
public class Role {
    @ApiModelProperty(hidden = true)
    private Long id;
    @ApiModelProperty(value = "权限名称", required = true)
    private String name;
    @ApiModelProperty(value = "权限的中文名称（新增一个新权限的时候该字段必传）")
    private String nameZh;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Role that = (Role) o;

        return name != null ? name.equals(that.name) : that.name == null;
    }
}
