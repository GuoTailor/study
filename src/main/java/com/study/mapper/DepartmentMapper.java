package com.study.mapper;

import com.study.bean.Department;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import java.util.List;

/**
 * Created by gyh on 2018/1/7.
 */
public interface DepartmentMapper {

    int addDep(Department dep);

    int deleteDep(Long id);

    Department getDepByPid(@Param("pid")Long pid);

    Department getDepById(@Param("id")Long pid);

    List<Department> getAllDeps();

    boolean existParentId(@Param("id") Long parentId);

    void setParentById(@Param("id") Long id, @Param("isParent") boolean isParent);

    int updateDep(@Param("dep")Department dep, @Param("originalId") Long id);
}
