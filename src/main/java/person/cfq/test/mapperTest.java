package person.cfq.test;

import org.apache.ibatis.session.SqlSession;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import person.cfq.bean.Employee;
import person.cfq.bean.EmployeeExample;
import person.cfq.dao.DepartmentMapper;
import person.cfq.dao.EmployeeMapper;

import java.util.List;
import java.util.UUID;

/**
 * 文件名：mapperTest.java
 * 测试DAO层的
 * 使用spring项目的spring单元测试，
 * 1、导入springTest模块
 * 2、@ContextConfiguration 指定spring配置文件的位置
 * 3、@RunWith()junit中的注解   指定spring提供的测试模块。
 *
 * @author : cfq086
 * @date : 2020/8/31 21:11
 */

@RunWith(SpringJUnit4ClassRunner.class) // 下面的test是(SpringJUnit4ClassRunner.class提供
@ContextConfiguration({"classpath:applicationContext.xml"})
public class mapperTest {

    @Autowired
    DepartmentMapper departmentMapper;      // 注解自动引入 部门dao成

    @Autowired
    EmployeeMapper employeeMapper;          // 注解 引入 员工dao

    @Autowired
    SqlSession sqlSession;                  // 能执行批量的session

    /**
     * 测试DepartmentMapper
     * 部门信息mapper.
     */
    @Test
    public void testCRUD() {
//        // 1、创建springIOC容器
//        ApplicationContext ioc = new ClassPathXmlApplicationContext("applicationContext.xml");
//        // 2、从容器中获取mapper
//        DepartmentMapper bean = ioc.getBean(DepartmentMapper.class);
        System.out.println("执行了");
        System.out.println(employeeMapper);

//        Employee employees = employeeMapper.selectByPrimaryKey(1);
//        System.out.println(employees.getEmpName());
//        Employee employee = employeeMapper.selectByPrimaryKeyWithDept(1);
//        System.out.println(employee.getEmpName());
        EmployeeExample employeeExample = new EmployeeExample();
        employeeExample.setOrderByClause(" e.emp_id asc");
        List<Employee> list = employeeMapper.selectByExampleWithDept(employeeExample);
        for (int i=0;list!=null&&i<list.size();i++){
            Employee employee = list.get(i);
        }
        // 1、插入几个部门
//
//        departmentMapper.insertSelective(new Department(null,"后勤部"));
//        departmentMapper.insertSelective(new Department(null,"傻子部"));

//        employeeMapper.insertSelective(new Employee(null,"刘备","男","libei@emai.com",5));
//        employeeMapper.insertSelective(new Employee(null,"关于","男","guanyu@emai.com",2));
//        employeeMapper.insertSelective(new Employee(null,"张飞","男","zhangfei@emai.com",2));
//        employeeMapper.insertSelective(new Employee(null,"张三","男","1@emai.com",1));
        /*
         * for(){
         * }        这样的不是批量，还是一条一条的插入，，需要挺长时间
         * */
        //配置可以批量导入的
//        EmployeeMapper mapper = sqlSession.getMapper(EmployeeMapper.class);
//        for (int i =0;i<1000;i++){
//            String uid = UUID.randomUUID().toString().substring(0,5)+i;
//            mapper.insertSelective(new Employee(null, uid, "男", uid+"@email.com", 1));
//
//        }

    }

}
