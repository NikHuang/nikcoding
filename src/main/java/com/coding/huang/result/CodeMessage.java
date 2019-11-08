package com.coding.huang.result;

/**
 * Created by Administrator on 2019/10/23.
 */
public class CodeMessage {
    private int code;
    private String msg;
    public CodeMessage(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    //模块划分
    //操作成功模块
    public static CodeMessage SUCCESS = new CodeMessage(100,"操作成功");
    //登录模块
    public static CodeMessage NOACCOOUNT = new CodeMessage(201,"登录失败，用户 %s 不存在");
    public static CodeMessage WRONG_PASSWORD = new CodeMessage(202,"登录失败，密码输入错误");
    public static CodeMessage NEED_LOGIN = new CodeMessage(203,"请先登录");
    public static CodeMessage PARAM_ERROR = new CodeMessage(204,"参数校验异常， %s ");
    public static CodeMessage ACCOUNT_ALREADY_EXISTS = new CodeMessage(205,"用户 %s 已存在");

    //业务模块
    public static CodeMessage DATA_NOT_FOUND = new CodeMessage(301,"数据不存在");
    public static CodeMessage ADMIN_ONLY = new CodeMessage(302,"该功能仅管理员可用");
    public static CodeMessage ACCOUNT_FROZEN = new CodeMessage(303,"该账号已冻结");

    //其他
    public static CodeMessage SERVER_ERROR = new CodeMessage(9001,"系统异常");
    public static CodeMessage NO_SUCH_API = new CodeMessage(404,"请求的接口不存在，跳转错误页面");

    //文本填充方法 一定不要写static 避免错误调用
    public CodeMessage fillArgs(Object... args){
        int code = this.getCode();
        String msg = String.format(this.getMsg(),args);
        return new CodeMessage(code,msg);
    }
}
