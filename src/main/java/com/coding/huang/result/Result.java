package com.coding.huang.result;

/**
 * Created by Administrator on 2019/10/23.
 */
public class Result<T> {
    private int code;
    private String msg;
    private T data;

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

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    //操作成功构造
    public Result(T data) {
        this(CodeMessage.SUCCESS);
        this.data = data;
    }
    //操作失败构造,无数据
    public Result(CodeMessage codeMessage) {
        this.code = codeMessage.getCode();
        this.msg = codeMessage.getMsg();
    }

   //操作成功时调用
   public static <T> Result<T> success(T data){
       return new Result(data);
   }
    //操作失败时调用 无数据
    public static <T> Result<T> error(CodeMessage codeMessage){
        return new Result(codeMessage);
    }
}
