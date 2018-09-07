package com.study.mapper;

import com.study.bean.Chapter;
import com.study.bean.Curriculum;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface CurriculumMapper {
    Curriculum getCurriculumById(@Param("id") Long id);

    Curriculum getCurriculumByTitle(@Param("title") String title);

    List<Chapter> getChaptersById(@Param("chapterid") String chapterid);

    void addCurriculum(Curriculum curriculum);

    List<Curriculum> getAll();
}
