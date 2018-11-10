package com.mebay.bean;

import java.util.List;
import java.util.stream.Stream;

public interface StreamTree<T extends StreamTree> {
    List<T> getChildren();
    Long getId();

    default Stream<T> stream() {
        return this.getChildren().stream().map(StreamTree<T>::stream).reduce(Stream.of((T)(this)), Stream::concat);
    }

    default T findSubById(Long id) {
        if (id == null)
            return null;
        if (getId().equals(id))
            return (T)this;
        if (!getChildren().isEmpty()) {
            for (T dep : getChildren()) {
                if (dep.findSubById(id) != null) {
                    return dep;
                }
            }
        }
        return null;
    }

    /**
     * 查找一个id的深度
     * @param o1 id
     * @return 深度 -1：不存在； >0正整数：深度
     */
    default int getTier(Long o1) {
        if (getId().equals(o1)) {
            return 0;
        }
        int tier = -1;
        for (StreamTree st : getChildren()) {
            int temp = st.getTier(o1);
            if (temp != -1 && temp + 1 > tier)
                tier = temp + 1;
        }
        return tier;
    }
}
