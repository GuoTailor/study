package com.mebay.service;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.mebay.bean.*;
import com.mebay.common.FileUtil;
import com.mebay.common.UserUtils;
import com.mebay.common.Util;
import com.mebay.mapper.DepartmentMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Logger;

@Service
@Transactional(rollbackFor = {Exception.class})
public class DepartmentService {
    private static final Logger logger = Logger.getLogger(DepartmentService.class.getSimpleName());
    private final DepartmentMapper departmentMapper;
    @Autowired
    private UserService userService;

    @Autowired
    public DepartmentService(DepartmentMapper departmentMapper) {
        this.departmentMapper = departmentMapper;
    }

    /**
     * 创建一个单位
     *
     * @param department 单位信息
     */
    public int creationDep(Department department) {
        department.setEnabled(true);
        User user = UserUtils.getCurrentUser();
        department.setParentId(user.getDepId());
        //logger.info(department.getParentId() + " " + (deps == null));
        if (user.getRole().contains(new Role("ROLE_HIGH_GRADE_ADMIN"))) {
            department.setCreationUid(user.getId());
            logger.info("nmka>>>>>>>>>>>");
        }
        if (departmentMapper.enable(user.getDepId())) {
            return departmentMapper.addDep(department);
        }
        return -1;
    }

    public int updateDep(Department dep, Long did) {
        if (Util.isEmpty(dep))
            return -2;
        Long deptId = UserUtils.getCurrentUser().getDepId();
        if (!did.equals(deptId)) {  //如果修改的不是自己
            List<Department> depts = departmentMapper.findDeptByPid(deptId);
            if (!Util.hasAny((s, d) -> s.getId().equals(d), depts, did)) {  //也不是自己管理的单位下的
                return -1;
            }
        }
        String logoPath = getDepById(did).getLogo();
        int i = departmentMapper.updateDep(dep, did);
        logger.info(logoPath + " >> " + dep.getLogo());
        if (i == 1 && logoPath != null && dep.getLogo() != null && !logoPath.equals(dep.getLogo())) {
            FileUtil.deleteFile("." + logoPath);
            logger.info("删除》》》》》");
        }
        return i;
    }

    /**
     * 获取单位id树通过当前用户。
     * 单位id树：为了节省性能，该方法只返回单位的id
     */
    public List<IdTree> getDeptIdTreeByUser() {
        User user = UserUtils.getCurrentUser();
        if (Util.hasAny(Role::equalsRole, user.getRole(), "ROLE_HIGH_GRADE_ADMIN")) {
            return departmentMapper.getDeptsByCreationId(user.getId());
        }else if (Util.hasAny(Role::equalsRole, user.getRole(), "ROLE_SUPER_ADMIN")) {
            //TODO
        }
        return Collections.singletonList(departmentMapper.getDeptIdTreeById(user.getDepId()));
    }

    public List<IdTree> getDeptsByCreationId(Long id) {
        return departmentMapper.getDeptsByCreationId(id);
    }

    /**
     * 获取单位
     *
     * @return 单位
     */
    public PageView<Department> getDeptByUid(PageQuery pageQuery) {
        List<Long> ids = new LinkedList<>();
        getDeptIdTreeByUser().forEach(d -> d.getIDs(ids));
        Page<Department> page = PageHelper.startPage(pageQuery);
        departmentMapper.getDeptByIds(ids, pageQuery.buildSubSql());
        return PageView.build(page);
    }

    public Department getDepById(Long id) {
        return departmentMapper.getDeptById(id);
    }

    public int deleteDep(Long id) {
        List<Department> depts = departmentMapper.findDeptByPid(UserUtils.getCurrentUser().getDepId());
        if (Util.hasAny((dept, d) -> dept.getId().equals(d), depts, id)) {
            IdTree d = departmentMapper.getDeptIdTreeById(id);
            String logoPath = departmentMapper.getDeptById(id).getLogo();
            if (d.getChildren().isEmpty() && userService.getUserByDepId(d.getId()).isEmpty()) { //如果单位下有子单位或用户
                int i = departmentMapper.deleteDep(id);
                if (i == 1) {
                    FileUtil.deleteFile("." + logoPath);
                }
                return i;
            } else
                return -2;
        }
        return -1;
    }

    public List<Department> getDepByPid(Long pid) {
        return departmentMapper.findDeptByPid(pid);
    }

    public boolean enable(Long parentId) {
        return departmentMapper.enable(parentId);
    }

}
