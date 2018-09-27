package com.mebay.mapper;

import com.mebay.bean.Department;
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

    boolean enable(@Param("id") Long parentId);

    boolean isPaerent(@Param("id") Long id);

    boolean setParentById(@Param("id") Long id, @Param("isParent") boolean isParent);

    int updateDep(@Param("dep")Department dep, @Param("originalId") Long id);
}
