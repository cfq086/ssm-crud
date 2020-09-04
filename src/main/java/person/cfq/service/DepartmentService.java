package person.cfq.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import person.cfq.bean.Department;
import person.cfq.bean.Msg;
import person.cfq.dao.DepartmentMapper;

import java.util.List;

/**
 * 文件名：DepartmentService.java
 *
 * @author : cfq086
 * @date : 2020/9/2 16:00
 */

@Service
public class DepartmentService {

    @Autowired
    private DepartmentMapper departmentMapper;

    public List<Department> getDepts() {
        // 查出的所有部门信息
        List<Department> list = departmentMapper.selectByExample(null);

        return list;
    }
}
