package person.cfq.bean;

import jdk.nashorn.internal.objects.annotations.Property;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Pattern;

public class Employee {
    private Integer empId;

    // @Length 长度效验
    @Pattern(regexp = "(^[a-zA-Z0-9-_]{5,16}$)|(^[\\u2E80-\\u9FFF]{2,5}$)",
    message = "用户名不符合规则啊————jsr303监测")
    private String empName;

    private String gender;
    // @Email
    @Pattern(regexp = "[\\w!#$%&'*+/=?^_`{|}~-]+(?:\\.[\\w!#$%&'*+/=?^_`{|}~-]+)*@(?:[\\w](?:[\\w-]*[\\w])?\\.)+[\\w](?:[\\w-]*[\\w])?",
    message = "邮箱不符合规则  xx@xx.xxx ——jsr303监测")
    private String email;

    private Integer dId;
    // 部门信息
    private Department department;

    public Department getDepartment() {
        return department;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }


    public Integer getEmpId() {
        return empId;
    }

    public void setEmpId(Integer empId) {
        this.empId = empId;
    }

    public String getEmpName() {
        return empName;
    }

    public void setEmpName(String empName) {
        this.empName = empName == null ? null : empName.trim();
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender == null ? null : gender.trim();
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email == null ? null : email.trim();
    }

    public Integer getdId() {
        return dId;
    }

    public void setdId(Integer dId) {
        this.dId = dId;
    }
}