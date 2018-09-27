package com.mebay.bean;

import java.util.List;
import java.util.stream.Stream;

public interface StreamTerr {
    List<? extends StreamTerr> getChildren();

    default Stream<StreamTerr> stream() {
        return this.getChildren().stream().map(StreamTerr::stream).reduce(Stream.of(this), Stream::concat);
    }
}
