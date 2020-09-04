package person.cfq.test;

import com.github.pagehelper.PageInfo;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import person.cfq.bean.Employee;


import java.util.List;

/**
 * 文件名：MvcTest.java
 *  使用spring测试模块提供的测试请求功能
 *  ======================================spring4测试的时候，需要spring3的支持
 *
 * @author : cfq086
 * @date : 2020/9/1 12:08
 */

@RunWith(SpringJUnit4ClassRunner.class) // 下面的test是(SpringJUnit4ClassRunner.class提供
@WebAppConfiguration                // 这样就可以自动装配了2
@ContextConfiguration({"classpath:applicationContext.xml","file:src/main/webapp/WEB-INF/dispatcherServlet-servlet.xml"})
public class MvcTest {
    // 传入springMCVC的ioc————自动装配 只能装配autowired里面的，自己无法自动装配1
    @Autowired
    WebApplicationContext context;
    // 虚拟的mvc请求，获取到处理结果
    MockMvc mockMvc;

    @Before                             // 每次执行前 都执行下方法，实例化虚拟请求对象。把springMVC的ioc传给他
    public void initMokcMvc(){
        mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
    }
    @Test
  public void testPage(){
       try {
           // 模拟发送请求，并发送参数，并获取返回值
          MvcResult mvcResult =  mockMvc.perform(MockMvcRequestBuilders.get("/emps").param("pn", "4")).andReturn();

          // 请求成功以后，请求与中会有 pageInfo ;提取PageIfo进行验证
           MockHttpServletRequest request =  mvcResult.getRequest();
           PageInfo pi = (PageInfo) request.getAttribute("pageInfo");
           System.out.println("当前页码："+pi.getPageNum());
           System.out.println("总页码："+pi.getPages());
           System.out.println("总记录条数："+pi.getTotal());
           System.out.println("当前连续显示几条数据："+pi.getList().size());
           int[] nums = pi.getNavigatepageNums();
           System.out.println("导航页码");
           for (int i:nums){
               System.out.print(""+i);
           }
           // 获取员工数
           List<Employee> list = pi.getList();

           for (Employee employee : list){
               System.out.printf("id:%d ，姓名：%s ,性别： %s , 邮箱： %s \n ",employee.getdId(),employee.getEmpName(),employee.getGender(),employee.getdId());
           }
       } catch (Exception e) {
           e.printStackTrace();
       }

   }
}
