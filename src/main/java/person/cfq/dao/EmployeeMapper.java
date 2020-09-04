package person.cfq.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import person.cfq.bean.Employee;
import person.cfq.bean.EmployeeExample;

public interface EmployeeMapper {
    long countByExample(EmployeeExample example);

    int deleteByExample(EmployeeExample example);

    int deleteByPrimaryKey(Integer empId);

    int insert(Employee record);

    int insertSelective(Employee record);

    //--不带部门信息 按条件
    List<Employee> selectByExample(EmployeeExample example);

    //--不带部门信息 按住键
    Employee selectByPrimaryKey(Integer empId);

    //自定义方法--带部门信息  按条件
    List<Employee> selectByExampleWithDept(EmployeeExample example);

    //自定义方法--带部门信息  按主键
    Employee selectByPrimaryKeyWithDept(Integer empId);

    int updateByExampleSelective(@Param("record") Employee record, @Param("example") EmployeeExample example);

    int updateByExample(@Param("record") Employee record, @Param("example") EmployeeExample example);

    int updateByPrimaryKeySelective(Employee record);

    int updateByPrimaryKey(Employee record);
}