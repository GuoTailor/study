package com.mebay.mapper;

import com.mebay.bean.Department;
import com.mebay.bean.IdTree;
import org.apache.ibatis.annotations.Param;
import java.util.List;

/**
 * Created by gyh on 2018/1/7.
 */
public interface DepartmentMapper {
    /**
     * 增加一个单位
     * @param dep 单位信息
     * @return 受影响的行数 1：增加一行； 0：增加0行（增加失败）
     */
    int addDep(Department dep);

    /**
     * 删除一个单位
     * @param id 单位id
     * @return 受影响的行数 1：删除一行； 0：删除0行（删除失败）
     */
    int deleteDep(Long id);

    /**
     * 获取单位的名字通过单位id
     * @param id 单位的id
     * @return 单位的名字
     */
    String getDeptNameById(@Param("id")Long id);

    /**
     * 获取单位通过单位id
     * @param id 单位id
     * @return 单位
     */
    Department getDeptById(@Param("id")Long id);

    /**
     * 获取单位通过单位id
     * 注意该方法将返回单位的子单位
     * @param id 单位的id
     * @return 单位
     */
    Department getDeptsTreeById(@Param("id")Long id);

    /**
     * 获取单位通过单位id
     * 注意该方法将不会返回它上级单位的名字但回上级单的id依旧会返回
     * @param id 单位的id
     * @return 单位列表
     */
    List<Department> findDeptByPid(@Param("id")Long id);

    /**
     * 获取单位通过单位的id列表
     * @param id id列表
     * @param search 分页查询参数 见{@link com.mebay.bean.PageQuery}.buildSubSql();可为null
     * @return 单位列表
     */
    List<Department> getDeptByIds(@Param("ids") List<Long> id, @Param("search") String search);

    /**
     * 获取单位列表通过建造者的id,
     * 该方法会返回自己和下一级单位，但不会递归返回下下级
     * @param uid 建造者的id
     * @return 单位列表
     */
    List<IdTree> getDeptsByCreationId(@Param("uid") Long uid);

    /**
     * 获取所有的单位<p>
     * 不包括已禁用的单位
     * @return 单位列表
     */
    List<Department> getAllDeps();

    /**
     * 检查单位是否被启用
     * @param parentId 单位id
     * @return true：启用 false：没有被启用
     */
    boolean enable(@Param("id") Long parentId);

    /**
     * 更新单位信息
     * @param dep 新的单位信息
     * @param id 要更新的单位的id
     * @return 受影响的行数 1：修改一行； 0：修改0行（修改失败）
     */
    int updateDep(@Param("dep")Department dep, @Param("originalId") Long id);

    /**
     * 获取单位通过单位的id,
     * 注意该方法将返回自己和下一级单位，但不会递归返回下下级
     * @param id 单位的id
     * @return 单位
     */
    IdTree getDeptIdTreeByPid(@Param("id") Long id);

    /**
     * 获取单位id树通过单位id,
     * 该方法会返回自己和下一级单位，但不会递归返回下下级
     * @param id 单位的id
     * @return 单位id树 见{@link IdTree}
     */
    IdTree getDeptIdTreeById(@Param("id") Long id);
}
