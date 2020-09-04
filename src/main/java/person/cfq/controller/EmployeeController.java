package person.cfq.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import person.cfq.bean.Employee;
import person.cfq.bean.Msg;
import person.cfq.service.EmployeeService;


/**
 * 处理员工CRUD请求 控制层
 *
 * @author 注解
 */
@Controller
public class EmployeeController {

    @Autowired
    EmployeeService employeeService;        // 自动装配

    /**
     * 可以单个删除、批量删除
     * 删除用户
     */
    @ResponseBody
    @RequestMapping(value = "/emp/{empId}", method = RequestMethod.DELETE)
    public Msg deleteEmpById(@PathVariable("empId") String ids) {
        if (ids.contains("-")){
           String [] str_ids =  ids.split("-");
           // 可以直接遍历单个删除---太笨，创建批量删除方法
            List<Integer> list = new ArrayList<Integer>();
            for (String str : str_ids){
                list.add(Integer.parseInt(str));
            }
            employeeService.deleteBatch(list);

        }else {
            Integer id = Integer.parseInt(ids);
            employeeService.deleteEmpById(id);
        }

        return Msg.success("删除用户");
    }

    /**
     * 更新呢员工信息
     * <p>
     * <p>
     * 如果直接发送ajax=PUT形式的请求
     * 封装的数据
     * Employee
     * [empId=1014, empName=null, gender=null, email=null, dId=null]
     * <p>
     * 问题：
     * 请求体中有数据；
     * 但是Employee对象封装不上；
     * update tbl_emp  where emp_id = 1014;
     * <p>
     * 原因：
     * Tomcat：
     * 1、将请求体中的数据，封装一个map。
     * 2、request.getParameter("empName")就会从这个map中取值。
     * 3、SpringMVC封装POJO对象的时候。
     * 会把POJO中每个属性的值，request.getParamter("email");
     * AJAX发送PUT请求引发的血案：
     * PUT请求，请求体中的数据，request.getParameter("empName")拿不到
     * Tomcat一看是PUT不会封装请求体中的数据为map，只有POST形式的请求才封装请求体为map
     * protected String parseBodyMethods = "POST";
     * * if( !getConnector().isParseBodyMethod(getMethod()) ) {
     * success = true;
     * return;
     * }
     * * 解决方案；
     * 我们要能支持直接发送PUT之类的请求还要封装请求体中的数据
     * 1、配置上HttpPutFormContentFilter；
     * 2、他的作用；将请求体中的数据解析包装成一个map。
     * 3、request被重新包装，request.getParameter()被重写，就会从自己封装的map中取数据
     */
    @ResponseBody
    @RequestMapping(value = "/upemp/{empId}", method = RequestMethod.PUT)
    public Msg upEmp(Employee employe) {
        System.out.println(employe);
        System.out.println(employe.getEmpId());
        System.out.println(employe.getEmpName());
        System.out.println(employe.getEmail());
        System.out.println(employe.getGender());
        System.out.println(employe.getdId());
        System.out.println(employe.getDepartment());

        employeeService.updateEmp(employe);
        return Msg.success("更新成功");
    }

    /**
     * 保存用户信息
     */
    @ResponseBody
    @RequestMapping("/saveEmp")
    public Msg saveEmp(@Valid Employee employee, BindingResult result) {
        // 原始手写效验
//        int xiao = checkUser(employee.getEmpName()).getCode();
//        if (xiao == 217) {
//            employeeService.saveEmp(employee);
//            return Msg.success("用户【" + employee.getEmpName() + "】保存成功！");
//        } else {
//            return Msg.fail("不去保存");
//        }
        //jsr303效验
        if (result.hasErrors()) {
            Map<String, Object> map = new HashMap<String, Object>();
            List<FieldError> errors = result.getFieldErrors();
            for (FieldError fieldError : errors) {
                map.put(fieldError.getField(), fieldError.getDefaultMessage());
            }
            return Msg.fail("效验失败，不保存！").add("errorFi", map);
        } else {
            employeeService.saveEmp(employee);
            return Msg.success("用户【" + employee.getEmpName() + "】保存成功！");
        }

    }


    /**
     * 检查用户名是否合法
     *
     * @param empName 要坚持的用户名
     * @return 是否可以新建用户
     */
    @ResponseBody
    @RequestMapping("/checkUser")
    public Msg checkUser(@RequestParam("empName") String empName) {
        // 前端效验了，后台也需要再次效验规则。
        String regx = "(^[a-zA-Z0-9-_]{5,16}$)|(^[\\u2E80-\\u9FFF]{2,5}$)";
        if (!empName.matches(regx)) {
            return Msg.fail("用户名不符合规则啊————server");
        }

        // 数据库效验
        boolean b = employeeService.checkUser(empName);
        if (b) {
            return Msg.success();
        } else {
            return Msg.fail("用户名已存在！————server");
        }

    }

    /**
     * 查询单个员工信息
     * 需要导入jackson包，用来包装json数据
     *
     * @return 用户列表的json类型数据，由请求方解析渲染，，
     */
    @ResponseBody
    @RequestMapping(value = "/emp/{id}", method = RequestMethod.GET)
    public Msg getEmpWithJson(@PathVariable("id") Integer id) {
        Employee emp = employeeService.getEmp(id);
        return Msg.success("获取员工数据").add("emp", emp);
    }


    /**
     * 查询所有员工信息（分页查询）
     * 需要导入jackson包，用来包装json数据
     *
     * @param pn 请求的页码
     * @return 用户列表的json类型数据，由请求方解析渲染，，
     */
    @ResponseBody
    @RequestMapping("/emps")
    public Msg getEmpsWithJson(
            @RequestParam(value = "pn", defaultValue = "1") Integer pn) {
        // 引入PageHelper分页插件
        PageHelper.startPage(pn, 10);

        // startPage后面紧跟的这个查询就是一个分页查询
        List<Employee> emps = employeeService.getAll();
        // 使用pageInfo包装查询后的结果，只需要将pageInfo交给页面就行了。
        // 封装了详细的分页信息,包括有我们查询出来的数据，传入连续显示的页数
        PageInfo page = new PageInfo(emps, 5);


        return Msg.success("获取员工数据").add("pageInfo", page);
    }

    /**
     * 查询员工数据（分页查询）
     *
     * @param pn 请求的页码
     * @return jsp的方式，数据处理由jsp渲染。。
     */
    @RequestMapping("/empsJsp")
    public String getEmps(
            @RequestParam(value = "pn", defaultValue = "1") Integer pn,
            Model model) {

        // 引入PageHelper分页插件
        PageHelper.startPage(pn, 10);

        // startPage后面紧跟的这个查询就是一个分页查询
        List<Employee> emps = employeeService.getAll();
        // 使用pageInfo包装查询后的结果，只需要将pageInfo交给页面就行了。
        // 封装了详细的分页信息,包括有我们查询出来的数据，传入连续显示的页数
        PageInfo page = new PageInfo(emps, 5);
        model.addAttribute("pageInfo", page);

        return "list";
    }

}
