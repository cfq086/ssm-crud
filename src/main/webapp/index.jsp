<%--
  Created by IntelliJ IDEA.
  User: cfq
  Date: 2020/9/1
  Time: 11:42
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<% pageContext.setAttribute("APP_PATH", request.getContextPath());
%>

<html>
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <!-- 上述3个meta标签*必须*放在最前面，任何其他内容都*必须*跟随其后！ -->
    <title>员工列表</title>
    <!-- Bootstrap -->
    <link href="${APP_PATH}/static/bootstrap-3.3.7-dist/css/bootstrap.min.css" rel="stylesheet">
    <!-- jQuery (Bootstrap 的所有 JavaScript 插件都依赖 jQuery，所以必须放在前边) -->
    <script src="${APP_PATH}/static/js/jquery.min.js"></script>
    <!-- 加载 Bootstrap 的所有 JavaScript 插件。你也可以根据需要只加载单个插件。 -->
    <script src="${APP_PATH}/static/bootstrap-3.3.7-dist/js/bootstrap.min.js"></script>
    <script src="${APP_PATH}/static/js/urlTool.js"></script>
    <style>
        .table tbody tr th {
            vertical-align: middle;
        }

        .table tbody tr td {
            vertical-align: middle;
        }


        .bt_2 button {
            margin-left: 3rem;
        }

        .juyou {
            text-align: right;
            padding-right: 3rem;
        }

        .juzuo {
            padding-left: 3rem;
        }
    </style>
</head>
<body>
<div class="container">
    <%--第一行、标题--%>
    <div class="row">
        <div class="col-md-12"><h2>SSM-CRUD</h2></div>
    </div>
    <%--第二行、按钮--%>
    <div class="row"></div>
    <div class="col-md-4 col-md-offset-8 bt_2">
        <button class="btn btn-success" id="add_Btn">
            <span class="glyphicon glyphicon-plus" aria-hidden="true"></span> 添加
        </button>
        <button class="btn btn-danger">
            <span class="glyphicon glyphicon-trash" aria-hidden="true"></span> 删除
        </button>
    </div>
    <br> <br> <br>

    <%--第三行、表格--%>
    <div class="row"></div>
    <div class="col-md-12">
        <table class="table table-bordered table-striped table-hover" id="emps_table">
            <thead>
            <tr class="info">
                <th>id</th>
                <th>姓名</th>
                <th>性别</th>
                <th>邮箱</th>
                <th>部门</th>
                <th class="col-md-3">操作</th>

            </tr>
            </thead>
            <tbody>
            <tr>
                <td class="col-md-12" colspan="5">正在加载中....</td>
            </tr>
            </tbody>
        </table>
    </div>
    <%--第四行、数据条数 页码--%>
    <div class="row">
        <div class="col-md-6 juzuo" id="page_info_area">

        </div>
        <div class="col-md-6 juyou" id="page_nav_area">

        </div>
    </div>

</div>

<!-- Modal ___员工添加 -->
<div class="modal fade" id="empAddModal" tabindex="-1" role="dialog" data-backdrop="false"
     aria-labelledby="myModalLabel">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span>
                </button>
                <h4 class="modal-title" id="myModalLabel">添加用户哈</h4>
            </div>
            <div class="modal-body">
                <form class="form-horizontal" id="emps_form" name="emps_fm">

                    <div class="form-group">
                        <label for="emp_add_user" class="col-sm-2 control-label">姓 名</label>
                        <div class="col-sm-10">
                            <input type="text" class="form-control" name="empName" id="emp_add_user"
                                   onchange="check_user('#emp_add_user')"
                                   placeholder="在此键入姓名">
                            <span id="helpName" class="help-block"></span>
                        </div>
                    </div>
                    <div class="form-group">
                        <label for="emp_sex" class="col-sm-2 control-label">性 别</label>
                        <div class="col-sm-10">
                            <label class="radio-inline" id="emp_sex">
                                <input type="radio" name="gender" id="sex_n" value="男" checked="checked"> 男生
                            </label>
                            <label class="radio-inline">
                                <input type="radio" name="gender" id="sex_v" value="女"> 女孩
                            </label>
                        </div>
                    </div>
                    <div class="form-group">
                        <label for="emp_email" class="col-sm-2 control-label">邮 箱</label>
                        <div class="col-sm-10">
                            <input type="email" class="form-control" name="email" id="emp_email"
                                   onchange="check_email('#emp_email')"
                                   placeholder="在此键入邮箱">
                            <span id="helpEmail" class="help-block"></span>
                        </div>
                    </div>
                    <div class="form-group">
                        <label for="dept_name" class="col-sm-2 control-label">部 门</label>
                        <div class="col-sm-4">
                            <select class="form-control" name="dId" id="dept_name">
                                <option></option>
                            </select>
                        </div>
                    </div>

                </form>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">
                    关闭窗口
                </button>
                <button type="button" id="emp_save_Btn" class="btn btn-primary">保存</button>
            </div>
        </div>
    </div>
</div>
<%--modal   ———— 员工修改--%>
<div class="modal fade" id="empUpdateModal" tabindex="-1" role="dialog" data-backdrop="false"
     aria-labelledby="updateModalLabel">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span>
                </button>
                <h4 class="modal-title" id="updateModalLabel">编辑用户</h4>
            </div>
            <div class="modal-body">
                <form class="form-horizontal" id="emps_update_form" name="emps_update_fm">

                    <div class="form-group">
                        <label for="empName_update_static" class="col-sm-2 control-label">姓 名</label>
                        <div class="col-sm-10">
                            <input type="text" class="form-control" name="empName" id="empName_update_static"
                                   onchange="check_user()" disabled >

                        </div>
                    </div>
                    <div class="form-group">
                        <label for="emp_update_sex" class="col-sm-2 control-label">性 别</label>
                        <div class="col-sm-10">
                            <label class="radio-inline" id="emp_update_sex">
                                <input type="radio" name="gender" value="男" checked="checked"> 男生
                            </label>
                            <label class="radio-inline">
                                <input type="radio" name="gender" value="女"> 女孩
                            </label>
                        </div>
                    </div>
                    <div class="form-group">
                        <label for="emp_update_email" class="col-sm-2 control-label">邮 箱</label>
                        <div class="col-sm-10">
                            <input type="email" class="form-control" name="email" id="emp_update_email"
                                   onchange="check_email('#emp_update_email')"
                                   placeholder="在此键入邮箱">
                            <span id="helpupdateEmail" class="help-block"></span>
                        </div>
                    </div>
                    <div class="form-group">
                        <label for="dept_update_name" class="col-sm-2 control-label">部 门</label>
                        <div class="col-sm-4">
                            <select class="form-control" name="dId" id="dept_update_name">
                                <option></option>
                            </select>
                        </div>
                    </div>

                </form>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">
                    关闭窗口
                </button>
                <button type="button" id="emp_update_Btn" class="btn btn-primary">更新</button>
            </div>
        </div>
    </div>
</div>

<script type="text/javascript">
    // 全局变量 总记录数
    var totalRecord;
    // 编辑按钮点击事件
    $(document).on("click", ".edit_btn", function () {
        // 1、填冲部门信息
        getDepts("#dept_update_name")
        // 2、获取单个员工数据
        getEmp($(this).attr("edit_id"))
        // 3、把员工的id传递给模态框的更新
        $("#emp_update_Btn").attr("edit-id",$(this).attr("edit_id"));
        //打开页面
        $("#empUpdateModal").modal();
    })

    // 编辑modal的更新按钮
    $("#emp_update_Btn").click(function () {
        // 验证邮箱是否合法
        // 1、效验邮箱信息
        if (check_email("#emp_update_email")){
            alert("可以注册");
        }
        else {
            alert("邮箱错误！")
        }
        //2、更新保存员工数据

        alert($("#empUpdateModal form").serialize());
        $.ajax({
            url:"${APP_PATH}/upemp/" + $(this).attr("edit-id"),
            type:"POST",
            data:$("#empUpdateModal form").serialize()+"&_method=PUT",
            success:function (result) {
                alert(result.msg);
            }
        })

    });

    function getEmp(id) {
        $.ajax({
            url: "${APP_PATH}/emp/" + id,
            type: "GET",
            success: function (result) {
                console.log(result);

                var emp = result.extend.emp;
                // document.getElementById("empName_update_static").value="result.extend.emp.empName";
                $("#empName_update_static").val(emp.empName);
                $("#emp_update_email").val(emp.email);
                $("#empUpdateModal input[name=gender]").val([emp.gender]);
                $("#dept_update_name").val([emp.dId]);

            }
        })
    }

    /*1、页面加载完成后，直接去发送一个ajax请求，要到分页数据*/
    $(function () {
        var pn = request_par("pn");
        if (pn != null && pn == undefined && pn == "" && pn == "undefined") {
            pn = "1";
        }
        // 去首页
        to_page(pn);
    });

    function to_page(pn) {
        $.ajax({
            url: "${APP_PATH}/emps",
            data: "pn=" + pn,
            type: "get",
            success: function (result) {
                console.log(result);
                /*拿到数据了*/
                // 1、解析并显示员工数据
                build_emps_table(result);
                // 2、解析并显示分页信息
                build_page_info(result);
                // 3、显示分页条
                build_page_nav(result);
            }
        });
    }

    /*生成表格*/
    function build_emps_table(result) {
        $("#emps_table tbody").empty();          // 先清空元素
        var emps = result.extend.pageInfo.list;
        /*jquery 提供的遍历*/
        $.each(emps, function (index, item) {
            // alert(item.empName);

            var empIdTd = $("<td></td>").append(item.empId);
            var empNameTd = $("<td></td>").append(item.empName);
            var genderTd = $("<td></td>").append(item.gender);
            var emailTd = $("<td></td>").append(item.email);
            var deptNameTd = $("<td></td>").append(item.department.deptName);
            var editBtn = $("<button></button>").addClass("btn btn-info btn-sm edit_btn")
                .append("<span></span>").addClass("glyphicon glyphicon-pencil")
                .append(" 编辑");
            // 添加自定义属性，为了获取编辑按钮编辑那个用户
            editBtn.attr("edit_id", item.empId)
            var delBtn = $("<button></button>").addClass("btn btn-danger btn-sm delete_btn")
                .append("<span></span>").addClass("glyphicon glyphicon-trash")
                .append(" 删除");
            var caoTd = $("<td></td>").addClass("bt_2").append(editBtn, delBtn);
            // 返回tr 表格行
            $("<tr></tr>").append(empIdTd, empNameTd, genderTd, emailTd, deptNameTd, caoTd).appendTo("#emps_table tbody");
        })
    }

    /*生成分页信息*/
    function build_page_info(result) {
        $("#page_info_area").empty();    // 先清空元素
        var pageInfo = result.extend.pageInfo;
        $("#page_info_area").append("<p></p>").append("现在 <kbd>" + pageInfo.pageNum + "</kbd> 页 | 共 <kbd>" + pageInfo.pages + "</kbd> 页 | 共 <kbd>" + pageInfo.total + "</kbd> 条数据")
        totalRecord = pageInfo.total;
    }


    /*生成分页条、点击访问*/
    function build_page_nav(result) {
        $("#page_nav_area").empty();        // 先清空元素
        var pageInfo = result.extend.pageInfo;
        var ul = $("<ul></ul>").addClass("pagination");
        // 先添加前两个按钮
        var firstLi = $("<li></li>").append($("<a></a>").append("首页").attr("href", "#"));
        var preLi = $("<li></li>").append($("<a></a>").append("<span aria-hidden='true'>&laquo;</span>").attr("href", "#"));


        if (pageInfo.isFirstPage) {
            firstLi.addClass("disabled")
            preLi = null;
        } else {
            firstLi.click(function () {
                to_page(1);

            })
            preLi.click(function () {
                to_page(pageInfo.pageNum - 1)
            })
        }

        ul.append(firstLi, preLi);

        // 页码按钮
        // jquery遍历
        $.each(pageInfo.navigatepageNums, function (index, item) {
            var pageLi = $("<li></li>").append($("<a></a>").append(item).attr("href", "#"));
            if (item == pageInfo.pageNum) {
                // 当前页
                pageLi.addClass("active");
            }
            pageLi.click(function () {
                to_page(item);
            })
            ul.append(pageLi);
        });
        // // 原生js遍历
        // for (var i = 0; i < pageInfo.navigatepageNums.length; i++) {
        //     var pageLi = $("<li></li>").append($("<a></a>").append(pageInfo.navigatepageNums[i]).attr("href", "#"));
        //     if (pageInfo.navigatepageNums[i] == pageInfo.pageNum) {
        //         // 当前页
        //           pageLi.addClass("active");
        //     }
        //     ul.append(pageLi);
        // }

        // 后两个按钮
        var nextLi = $("<li></li>").append($("<a></a>").attr("href", "#").append("<span aria-hidden='true'>&raquo;</span>"));
        var lastLi = $("<li></li>").append($("<a></a>").append("末页").attr("href", "#"));

        if (pageInfo.isLastPage) {
            lastLi.addClass("disabled")
            nextLi = null;
        } else {
            nextLi.click(function () {
                to_page(pageInfo.pageNum + 1);
            })
            lastLi.click(function () {
                to_page(pageInfo.pages);
            })
        }

        ul.append(nextLi, lastLi);
        $("#page_nav_area").attr("aria-label", "Page navigation").append(ul);
    }

    // 点击部门modal
    $("#add_Btn").click(function () {
        //清除数据

        // 下面这个是jquery里的reset方法，能重置表单输入，，但是重置不了加入的提示
        $("#empAddModal form")[0].reset();
        // 自写方法 消除数据
        clearInp("emps_form");

        // 请求数据
        getDepts("#dept_name");

        //打开页面
        $("#empAddModal").modal();
    });


    // 请求部门数据
    function getDepts(m) {
        $.ajax({
            url: "${APP_PATH}/depts",
            type: "GET",
            success: function (result) {
                console.log(result)
                // 解析部门数据并把部门数据填充到modal中
                build_dept_select(result, m);
            }
        })

    }

    // 填充数据到选择器
    function build_dept_select(result, m) {
        $(m).empty();        // 先清空，避免重复出现
        var depts = result.extend.depts;
        // <option>3</option>
        $.each(depts, function (index, item) {
            $(m).append($("<option></option>").attr("value", item.deptId).append(item.deptName));
        })


    }

    // 检查用户名是否可用于注册--重复 false
    function is_nameRepeat(ele) {
        var empName = $(ele).val();
        var zt = false;
        $.ajax({
            url: "${APP_PATH}/checkUser",
            data: "empName=" + empName,
            type: "POST",
            async: false,
            success: function (result) {
                console.log(result);
                if (result.code == 217) {
                    show_validate_msg(ele, "success", "可以注册！。。");
                    zt = true;
                } else {
                    show_validate_msg(ele, "error", result.msg);
                }
            }
        })
        return zt;
    }

    // 检查用户名是否合法
    function check_user() {
        var empName = $("#emp_add_user").val();
        var regName = /(^[a-zA-Z0-9-_]{5,16}$)|(^[\u2E80-\u9FFF]{2,5}$)/;
        if (empName == "" || empName == null) {
            show_validate_msg("#emp_add_user", "error", "姓名不能为空");
            return false;
        } else {
            show_validate_msg("#emp_add_user", "success", "");
        }

        if (!regName.test(empName)) {
            show_validate_msg("#emp_add_user", "error", "输入的姓名不合法！5-10位大小写英文数字 或 2-5位汉字");
            return false;
        } else {
            show_validate_msg("#emp_add_user", "success", "");
        }
        // 发送给后端进行效验
        return is_nameRepeat("#emp_add_user");
    }

    // 检查邮箱是否合法
    function check_email(email) {
        var empEmail = $(email).val();
        var regEmail = /[\w!#$%&'*+/=?^_`{|}~-]+(?:\.[\w!#$%&'*+/=?^_`{|}~-]+)*@(?:[\w](?:[\w-]*[\w])?\.)+[\w](?:[\w-]*[\w])?/;
        if (empEmail == "" || empEmail == null) {
            show_validate_msg(email, "error", "邮箱不能为空");
            return false;
        } else {
            show_validate_msg(email, "success", "");
        }
        if (!regEmail.test(empEmail)) {
            show_validate_msg(email, "error", "邮箱不合法！      xx@xx.xxx");
            return false;
        } else {
            show_validate_msg(email, "success", "");
        }
        return true;
    }

    //消除产生的提示信息
    function clearInp(frm) {
        var x = document.getElementById(frm);
        for (var i = 0; i < x.length; i++) {
            var tmp = x.elements[i].type;
            if (tmp == "text" || tmp == "password" || tmp == "email") {
                x.elements[i].value = "";
                // 清除筱燕的问题和状态
                clear_validate("#" + x.elements[i].id);
            }
        }

    }

    // 清除筱燕的问题和状态
    function clear_validate(ele) {
        $(ele).parent().removeClass("has-success has-error");
        $(ele).next("span").empty();
    }

    // 显示提示信息
    function show_validate_msg(ele, status, msg) {
        // 清除筱燕的问题和状态
        clear_validate(ele);
        if (status == "success") {
            $(ele).parent().addClass("has-success");
            $(ele).next("span").text(msg)
        } else {
            $(ele).parent().addClass("has-error");
            $(ele).next("span").text(msg)
        }
    }

    // 点击保存按钮
    $("#emp_save_Btn").click(function () {
        // 晓燕用户输入的信息是否合法        ———— 服务器端也要进行筱燕。

        if (!check_user("emp_add_user") || !check_email("#emp_email")) {
            alert("请输入正确的值！！")
            return false;
        }
        // 提交给服务器
        $.ajax({
            url: "${APP_PATH}/saveEmp",
            type: "POST",
            data: $("#emps_form").serialize(),
            success: function (result) {
                console.log(result);
                if (result.code == 217) {
                    // 隐藏关闭模态框
                    $("#empAddModal").modal('hide');
                    // 跳转到最后页
                    to_page(totalRecord);

                } else {
                    var list = result.extend.errorFi;
                    if (list.empName != undefined || list.empName != "undefined") {
                        show_validate_msg("#emp_add_user", "error", list.empName);
                    }
                    if (list.email != undefined || list.email != "undefined") {
                        show_validate_msg("#emp_email", "error", list.email);
                    }

                }

            }
        })
    })
</script>
</body>
</html>
