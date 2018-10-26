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
}
