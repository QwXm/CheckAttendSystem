
/*管理员登录前台数据校验*/
$(document).ready(function () {

    /*前台数据校验*/
    $("#loginButton").bind("click", function () {
        if ($("#user_name").val() == '') {
            alert("账号不能为空！！！");
            return false;
        } else if ($("#password").val() == '') {
            alert("密码不能为空！！！");
            return false;
        }
        //异步提交url
        var url = "admin_login";
        //提交表单参数
        var params = $("#manageForm").serialize();
        $.post(url,params,function (data) {
            if(data==-1){

            }else if(data==0){

            }else if(data==1){

            }
        });
    });

});
/*教师添加前台数据校验*/
$(document).ready(function () {
    $("#name").bind("focusout",function () {
        $("#password").val($("#work_num").val());
    })

    $("#addButton").bind("click", function () {

        if($("#work_num").val()=='') {
            alert("教工号不能为空！！！");
            return false;
        }else if($("#name").val()=='') {
            alert("教师名称不能为空！！！");
            return false;
        }else if($("#password").val()==''){
            alert("密码不能为空！！！");
            return false;
        }

    });
});
$(document).ready(function () {
    //获取点击事件
    $("#addButton").bind("click",function () {
        /*访问路径*/
        var url = "add";
        /*访问参数*/
        var params = $("#addTeacherForm").serialize();
        $.post(url,params,function(data){
           if(data==1){
                alert("添加成功！！！");
           }else if(data==0) {
                alert("添加失败！！！");
           }
        });
    });
});


//------------------------
$("#start_week").click(function(){
    var htmlContent;
    for(var flag=1;flag<=20;flag++){
        htmlContent += "<option "+"value='" + flag +"'>第"+ flag +"周</option>";
    }
    $("#start_week").append(htmlContent);
    $("#end_week").empty();
    $("#end_week").append("<option>请选择结束上课周次</option>");
});

$("#end_week").click(function(){
    var flag = $("#start_week").val();
    var endFlag = 20;
    var htmlContent;
    while (flag <= endFlag){
        htmlContent += "<option "+"value='" + flag +"'>第"+ flag +"周</option>";
        flag++;
    }
    $("#end_week").append(htmlContent);
});