package com.mebay.bean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by gyh on 2018/10/22.
 */
public class DeptTreeId implements StreamTree<DeptTreeId> {
    private Long id;
    private List<DeptTreeId> children = new ArrayList<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<DeptTreeId> getChildren() {
        return children;
    }

    public void setChildren(List<DeptTreeId> children) {
        this.children = children;
    }

    public void getIDs(List<Long> deptIsds) {
        deptIsds.add(id);
        for (DeptTreeId deptTreeId : children) {
            deptTreeId.getIDs(deptIsds);
        }
    }
}
