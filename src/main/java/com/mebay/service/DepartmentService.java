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
@Transactional(rollbackFor={Exception.class})
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
     * @param department 单位信息
     */
    public int creationDep(Department department) {
        department.setEnabled(true);
        User user = UserUtils.getCurrentUser();
        department.setParentId(UserUtils.getCurrentUser().getDepId());
        //logger.info(department.getParentId() + " " + (deps == null));
        if (user.getRole().contains(new Role("ROLE_HIGH_GRADE_ADMIN"))) {
            if (department.getParentId().equals(1L)) {
                department.setCreationUid(user.getId());
                logger.info("nmka>>>>>>>>>>>");
            }
        }
        if (departmentMapper.enable(department.getParentId())) {
            return departmentMapper.addDep(department);
        }
        return -1;
    }

    public int updateDep(Department dep, Long did) {
        if (Util.isEmpty(dep))
            return -2;
        List<DeptTreeId> deptTreeIds = getDeptIdTreeByUser();
        for (DeptTreeId deptId : deptTreeIds) {
            DeptTreeId d = deptId.findSubById(did);
            if (d != null) {
                String logoPath = getDepById(d.getId()).getLogo();
                int i = departmentMapper.updateDep(dep, did);
                if (i == 1 && logoPath != null) {
                    FileUtil.deleteFile("." + logoPath);
                }
                return i;
            }
        }
        return -1;
    }

    /**
     * 获取单位id树通过当前用户。
     * 单位id树：为了节省性能，该方法只返回单位的id
     */
    public List<DeptTreeId> getDeptIdTreeByUser() {
        User user = UserUtils.getCurrentUser();
        if (Util.hasAny(Role::equalsRole, user.getRole(), "ROLE_HIGH_GRADE_ADMIN")) {
            return departmentMapper.getDeptsByCreationId(user.getId());
        }
        return Collections.singletonList(departmentMapper.getDeptIdTreeById(user.getDepId()));
    }

    /**
     * 获取单位
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
        List<DeptTreeId> deptTreeIds = getDeptIdTreeByUser();
        for (DeptTreeId deptId : deptTreeIds) {
            DeptTreeId d = deptId.findSubById(id);
            if (d != null) {
                String logoPath = getDepById(d.getId()).getLogo();
                if(d.getChildren().isEmpty() && userService.getUserByDepId(d.getId()).isEmpty()) {
                    int i = departmentMapper.deleteDep(id);
                    if(i == 1) {
                        FileUtil.deleteFile("." + logoPath);
                    }
                    return i;
                }else
                    return -2;
            }
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
