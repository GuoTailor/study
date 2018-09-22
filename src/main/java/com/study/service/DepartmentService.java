package com.study.service;

import com.study.bean.Department;
import com.study.bean.Role;
import com.study.bean.User;
import com.study.common.FileUtil;
import com.study.common.UserUtils;
import com.study.mapper.DepartmentMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.logging.Logger;

@Service
@Transactional
public class DepartmentService {
    private static final Logger logger = Logger.getLogger(DepartmentService.class.getSimpleName());
    @Autowired
    DepartmentMapper departmentMapper;

    public int creationDep(Department department) {
        department.setEnabled(true);
        User user = UserUtils.getCurrentUser();
        Department deps = getDepById(user.getDepId());
        logger.info(department.getParentId() + " " + (deps == null));
        if (!user.getRole().contains(new Role("ROLE_HIGH_GRADE_ADMIN")))
            department.setCreationUid(user.getId());
        if (deps != null && deps.existId(department.getParentId()) != null && departmentMapper.existParentId(department.getParentId())) {
            if (departmentMapper.addDep(department) == 1) {
                departmentMapper.setParentById(department.getParentId(), true);
                return 1;
            }
            return 0;
        }
        return -1;
    }

    public int updateDep(Department dep, Long did) {
        Department deps = getDepByUid();
        Department d = deps.existId(did);
        if (d == null) {
            return -1;
        }
        if (d.getLogoPath() != null) {
            FileUtil.deleteFile(d.getLogoPath());
        }
        return departmentMapper.updateDep(dep, did);
    }

    public Department getDepByUid() {
        return getDepById(UserUtils.getCurrentUser().getDepId());
    }

    public Department getDepById(Long id) {
        return departmentMapper.getDepById(id);
    }

    public int deleteDep(Long id) {
        return departmentMapper.deleteDep(id);
    }

    public Department getDepByPid(Long pid) {
        return departmentMapper.getDepByPid(pid);
    }

    public boolean existParentId(Long parentId) {
        return departmentMapper.existParentId(parentId);
    }

    public void setParentById(Long id, boolean isParent) {
        departmentMapper.setParentById(id, isParent);
    }

}
