package com.study.mapper;

import com.study.bean.Chapter;
import org.apache.ibatis.annotations.Param;

public interface ChapterMapper {
    Chapter getChapterById(@Param("id") Long id);

    void addChapter(Chapter chapter);
}
