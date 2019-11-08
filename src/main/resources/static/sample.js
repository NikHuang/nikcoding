/**
 * Created by Administrator on 2019/7/10.
 */
var usersalt = "hq1hb2qk3";
function login(){
    $("#loginForm").validate({
        submitHandler: function(form) {
            doLogin();
        }
    });
}
function doLogin(){
     showLayer();
    var inputPassword = $("#pwdInput").val();
    var formPassword = usersalt.charAt(1)+usersalt.charAt(5)+inputPassword+usersalt.charAt(2)+usersalt.charAt(4);
    var realpassword = hex_md5(formPassword)
        $.ajax({
        url:"http://192.168.2.6:8211/login/doLogin",
        type:"POST",
        data:{
            loginAccount:$("#mobileInput").val(),
            loginPwd:realpassword
        },
        xhrFields:{
            withCredentials:true
        },
        success: function (resp) {
            layer.closeAll();
            if (resp.code == 0){
                layer.msg("登录成功");
                window.location.href = "/goods/to_list";
            }else {
                layer.msg(resp.msg);
            }
        },
        error:function(){

        }
    })
}
$(document).ready(function(){

})