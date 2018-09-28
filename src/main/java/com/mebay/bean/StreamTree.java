package com.mebay.bean;

import java.util.List;
import java.util.stream.Stream;

public interface StreamTree {
    List<? extends StreamTree> getChildren();

    default Stream<StreamTree> stream() {
        return this.getChildren().stream().map(StreamTree::stream).reduce(Stream.of(this), Stream::concat);
    }
}
