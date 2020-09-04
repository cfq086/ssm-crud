package person.cfq.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import person.cfq.bean.Department;
import person.cfq.bean.Msg;
import person.cfq.service.DepartmentService;

import java.util.List;

/**
 * 文件名：DepartmentController.java
 *
 * @author : cfq086
 * @date : 2020/9/2 15:59
 */
@Controller
public class DepartmentController {

    @Autowired
    private DepartmentService departmentService;        // 注解引入

    @RequestMapping("/depts")
    @ResponseBody
    public Msg getDepts() {
        List<Department> list = departmentService.getDepts();
        return Msg.success().add("depts", list);
    }
}
