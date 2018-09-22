package com.study.bean;

public class Role {
    private Long id;
    private String name;
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
