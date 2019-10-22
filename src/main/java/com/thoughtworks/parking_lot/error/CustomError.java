package com.thoughtworks.parking_lot.error;

public class CustomError {
    private String errMessage;
    private Integer errCode;

    public CustomError() {
    }

    public String getErrMessage() {
        return errMessage;
    }

    public void setErrMessage(String errMessage) {
        this.errMessage = errMessage;
    }

    public Integer getErrCode() {
        return errCode;
    }

    public void setErrCode(Integer errCode) {
        this.errCode = errCode;
    }
}
