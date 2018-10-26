package com.mebay.bean;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotNull;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by GYH on 2018/9/3.
 */
@ApiModel(description = "单位")
public class Department implements StreamTree<Department> {
    @ApiModelProperty(hidden = true)
    private Long id;
    @ApiModelProperty(value = "单位名称", required = true)
    @NotNull(message = "不能为空")
    private String name;
    @ApiModelProperty(value = "父节点id")
    private Long parentId;
    @ApiModelProperty(hidden = true)
    private Boolean enabled;
    @ApiModelProperty(hidden = true)
    private Long creationUid;
    @ApiModelProperty(value = "单位logo")
    private String logo;
    @ApiModelProperty(value = "单位类型", required = true)
    @NotNull(message = "不能为空")
    private String type;
    @ApiModelProperty(value = "创建时间")
    private String creationTime;
    @ApiModelProperty(value = "单位地址")
    private String deptSite;
    @ApiModelProperty(value = "上级单位名", hidden = true)    //仅提供前端显示用
    private String superiorName;
    @ApiModelProperty(value = "联系人", required = true)
    @NotNull(message = "不能为空")
    private String contact;
    @ApiModelProperty(value = "联系电话", required = true)
    @NotNull(message = "不能为空")
    private String contactPhone;
    @ApiModelProperty(value = "邮件")
    private String email;
    @ApiModelProperty(value = "单位网址")
    private String deptUrl;
    @ApiModelProperty(hidden = true)
    @JsonIgnore
    private Date time;
    private List<Department> children = new ArrayList<>();

    public Department() {
    }

    public Department(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Department that = (Department) o;

        return name != null ? name.equals(that.name) : that.name == null;
    }

    public List<Department> getChildren() {
        return children;
    }

    public void setChildren(List<Department> children) {
        this.children = children;
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

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    public Boolean getEnabled() {
        return enabled;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }

    public Long getCreationUid() {
        return creationUid;
    }

    public void setCreationUid(Long creationUid) {
        this.creationUid = creationUid;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getCreationTime() {
        return creationTime;
    }

    public void setCreationTime(String creationTime) {
        this.creationTime = creationTime;
    }

    public String getDeptSite() {
        return deptSite;
    }

    public void setDeptSite(String deptSite) {
        this.deptSite = deptSite;
    }

    public String getSuperiorName() {
        return superiorName;
    }

    public void setSuperiorName(String superiorName) {
        this.superiorName = superiorName;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getContactPhone() {
        return contactPhone;
    }

    public void setContactPhone(String contactPhone) {
        this.contactPhone = contactPhone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDeptUrl() {
        return deptUrl;
    }

    public void setDeptUrl(String deptUrl) {
        this.deptUrl = deptUrl;
    }
    @JsonIgnore
    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }
}
