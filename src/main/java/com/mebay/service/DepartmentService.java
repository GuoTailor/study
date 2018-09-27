package com.mebay.service;

import com.mebay.bean.Department;
import com.mebay.bean.Role;
import com.mebay.bean.User;
import com.mebay.common.FileUtil;
import com.mebay.common.UserUtils;
import com.mebay.mapper.DepartmentMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import java.util.logging.Logger;

@Service
@Transactional(rollbackFor={Exception.class})
public class DepartmentService {
    private static final Logger logger = Logger.getLogger(DepartmentService.class.getSimpleName());
    private final DepartmentMapper departmentMapper;

    @Autowired
    public DepartmentService(DepartmentMapper departmentMapper) {
        this.departmentMapper = departmentMapper;
    }

    public int creationDep(Department department) {
        department.setEnabled(true);
        department.setParent(false);
        User user = UserUtils.getCurrentUser();
        Department deps = getDepById(user.getDepId());
        logger.info(department.getParentId() + " " + (deps == null));
        if (!user.getRole().contains(new Role("ROLE_HIGH_GRADE_ADMIN")))
            department.setCreationUid(user.getId());
        if (deps != null && deps.existId(department.getParentId()) != null && departmentMapper.enable(department.getParentId())) {
            if (departmentMapper.addDep(department) == 1) {
                if (!departmentMapper.setParentById(department.getParentId(), true))
                    TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();  //手动回滚事务
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
        if (departmentMapper.isPaerent(id))
            return -1;
        return departmentMapper.deleteDep(id);
    }

    public Department getDepByPid(Long pid) {
        return departmentMapper.getDepByPid(pid);
    }

    public boolean enable(Long parentId) {
        return departmentMapper.enable(parentId);
    }

    public void setParentById(Long id, boolean isParent) {
        departmentMapper.setParentById(id, isParent);
    }

}
