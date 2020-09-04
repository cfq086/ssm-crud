package person.cfq.service;

import java.util.List;

import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ResponseBody;
import person.cfq.bean.Employee;
import person.cfq.bean.EmployeeExample;
import person.cfq.dao.EmployeeMapper;

/**
 * employee 员工信息 service层
 * 都需要注解
 */

@Service
public class EmployeeService {

    @Autowired
    EmployeeMapper employeeMapper;        // 自动装配  EmployeeMapper 是dao层


    /**
     * 查询所有员工(分页查询)
     *
     * @return 用户信息的集合
     */
    public List<Employee> getAll() {
        // TODO Auto-generated method stub
        EmployeeExample employeeExample = new EmployeeExample();
        employeeExample.setOrderByClause(" e.emp_id asc");
        return employeeMapper.selectByExampleWithDept(employeeExample);
    }


    /**
     * 保存用户信息————使用按条件保存，可以不提供某些参数，
     *
     * @param employee 要保存的用户信息
     */
    public void saveEmp(Employee employee) {
        employeeMapper.insertSelective(employee);
    }

    /**
     * @param empName 要检查的姓名字符串
     * @return 如果查找记录返回false，查不到返回true----转换的结果，非实际查询返回值
     */
    public boolean checkUser(String empName) {
        EmployeeExample example = new EmployeeExample();
        EmployeeExample.Criteria criteria = example.createCriteria();
        criteria.andEmpNameEqualTo(empName);
        long count = employeeMapper.countByExample(example);
        return count == 0;

    }


    public Employee getEmp(Integer id) {

        Employee employee = employeeMapper.selectByPrimaryKey(id);
        return employee;
    }

    public void updateEmp(Employee employe) {
        employeeMapper.updateByPrimaryKeySelective(employe);
    }

    public void deleteEmpById(Integer id) {
        employeeMapper.deleteByPrimaryKey(id);
    }

    /**
     * 批量删除
     */
    public void deleteBatch(List<Integer> str_ids) {
        EmployeeExample employeeExample = new EmployeeExample();
        EmployeeExample.Criteria criteria = employeeExample.createCriteria();
        // delete from xxx where emp_id in(1,2,3,...)
        criteria.andEmpIdIn(str_ids);
        employeeMapper.deleteByExample(employeeExample);
    }
}
