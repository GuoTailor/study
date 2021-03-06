package com.mebay.bean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by gyh on 2018/10/22.
 */
public class IdTree implements StreamTree<IdTree> {
    private Long id;
    private List<IdTree> children = new ArrayList<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<IdTree> getChildren() {
        return children;
    }

    public void setChildren(List<IdTree> children) {
        this.children = children;
    }

    /**
     * 拆解整颗树，把树的所有节点添加到{@code deptIsds}中
     * @param deptIsds 节点集合
     */
    public void getIDs(List<Long> deptIsds) {
        deptIsds.add(id);
        for (IdTree idTree : children) {
            idTree.getIDs(deptIsds);
        }
    }
}
