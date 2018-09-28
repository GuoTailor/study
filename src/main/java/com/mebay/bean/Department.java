package com.mebay.bean;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by GYH on 2018/9/3.
 */
public class Department implements StreamTree {
    private Long id;
    private String name;
    @NotNull(message = "不能为空")
    private Long parentId;
    private Boolean enabled;
    private Boolean isParent;
    private Long creationUid;
    private String logoPath;

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

    public Department existId(Long id) {
        if (id == null)
            return null;
        if (this.id.equals(id))
            return this;
        if (!children.isEmpty()) {
            for (Department dep : children) {
                if (dep.existId(id) != null) {
                    return dep;
                }
            }
        }
        return null;
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

    public Boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public Boolean isParent() {
        return isParent;
    }

    public void setParent(boolean parent) {
        isParent = parent;
    }

    public Long getCreationUid() {
        return creationUid;
    }

    public void setCreationUid(Long creationUid) {
        this.creationUid = creationUid;
    }

    public String getLogoPath() {
        return logoPath;
    }

    public void setLogoPath(String logoPath) {
        this.logoPath = logoPath;
    }
}
