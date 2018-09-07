package com.study.mapper;

import com.study.bean.Department;
import org.apache.ibatis.annotations.Param;
import java.util.List;

/**
 * Created by sang on 2018/1/7.
 */
public interface DepartmentMapper {

    int addDep(Department dep);

    int deleteDep(Long id);

    List<Department> getDepByPid(@Param("pid")Long pid);

    List<Department> getAllDeps();

    boolean existParentId(@Param("id") Long parentId);

    void setParentById(@Param("id") Long id, @Param("isParent") boolean isParent);
}
