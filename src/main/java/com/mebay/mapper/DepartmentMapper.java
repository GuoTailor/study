package com.mebay.mapper;

import com.mebay.bean.Department;
import com.mebay.bean.DeptTreeId;
import org.apache.ibatis.annotations.Param;
import java.util.List;

/**
 * Created by gyh on 2018/1/7.
 */
public interface DepartmentMapper {

    int addDep(Department dep);

    int deleteDep(Long id);

    String getDeptNameById(@Param("id")Long id);

    Department getDeptById(@Param("id")Long id);

    Department getDeptsTreeById(@Param("id")Long id);

    List<Department> findDeptByPid(@Param("id")Long id);

    List<Department> getDeptByIds(@Param("ids") List<Long> id, @Param("search") String search);

    List<DeptTreeId> getDeptsByCreationId(@Param("uid") Long uid);

    List<Department> getAllDeps();

    boolean enable(@Param("id") Long parentId);

    int updateDep(@Param("dep")Department dep, @Param("originalId") Long id);

    DeptTreeId getDeptIdTreeByPid(@Param("id") Long id);

    DeptTreeId getDeptIdTreeById(@Param("id") Long id);
}
