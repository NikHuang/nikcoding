package com.coding.huang.exceptions;

import com.coding.huang.result.CodeMessage;

/**
 * Created by Administrator on 2019/10/23.
 */
public class CommonException extends RuntimeException {
    //异常类需要serialVersionUID
    private static final long serialVersionUID = 8356993429275402324L;


    private CodeMessage codeMessage;

    public CodeMessage getCodeMessage() {
        return codeMessage;
    }

    public CommonException(CodeMessage codeMessage) {
        super(codeMessage.toString());
        this.codeMessage = codeMessage;
    }
}
