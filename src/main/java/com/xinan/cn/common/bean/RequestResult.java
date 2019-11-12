package com.xinan.cn.common.bean;

import com.alibaba.fastjson.JSON;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Serializable;
import java.io.UnsupportedEncodingException;


/**
 * 封装请求结果
 */
public class RequestResult implements Serializable {
    private static final Logger logger = LoggerFactory.getLogger(RequestResult.class);
    public static int CODE_OK = StatusCode.CODE_OK;
    public static int CODE_ERROR = StatusCode.CODE_ERROR;

    private int code;//响应结果，0为正常，其它值为异常

    private String message = "";//响应消息

    private String data = "";//返回的数据

    private String action = "";//终端收到的响应类型

    public RequestResult() {
    }

    public RequestResult(int code) {
        this.code = code;
    }

    public RequestResult(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public RequestResult(int code, String message, String data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    public RequestResult(int code, String message, String data, String action) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    public static RequestResult success(Object data) {
        RequestResult requestResult = new RequestResult();
        try {
            requestResult.setCode(CODE_OK);
            String jsonString = JSON.toJSONString(data);
            if (data == null) {
                jsonString = "";
            }
            requestResult.setData(jsonString);
        } catch (Exception e) {
            logger.error("error in success with data:{}.", data, e);
            requestResult.setCode(CODE_ERROR);
            requestResult.setMessage(e.getMessage());
        }
        return requestResult;
    }

    public RequestResult withMsg(String msg) {
        this.message = msg;
        return this;
    }

    public RequestResult withMsg(Object msgObj) {
        this.message = JSON.toJSONString(msgObj);
        return this;
    }

    public static RequestResult error(WeiyanException e) {
        RequestResult requestResult = new RequestResult();
        requestResult.setCode(e.getStatusCodeEnum().getCode());
        requestResult.setMessage(e.getStatusCodeEnum().getMsg());
        return requestResult;
    }

    public static RequestResult error(WeiyanException e, String msg) {
        RequestResult requestResult = new RequestResult();
        requestResult.setCode(e.getStatusCodeEnum().getCode());
        requestResult.setMessage(msg);
        return requestResult;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;

        RequestResult that = (RequestResult) o;

        if (code != that.code)
            return false;
        if (!message.equals(that.message))
            return false;
        if (!data.equals(that.data))
            return false;
        return action.equals(that.action);
    }

    @Override
    public int hashCode() {
        int result = code;
        result = 31 * result + message.hashCode();
        result = 31 * result + data.hashCode();
        result = 31 * result + action.hashCode();
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("{\"code\":").append(code);
        sb.append(",\"action\":").append('"').append(action).append('"');
        sb.append(",\"message\":").append('"').append(message).append('"');
        sb.append(",\"data\":");

        if (data.length() > 0) {
            sb.append(data);
        } else {
            sb.append("\"\"");
        }
        sb.append("}");
        return sb.toString();
    }

    public byte[] toUtf8Bytes(String callback) {
        try {
            if (StringUtils.isNotBlank(callback)) {
                String txt = String.format("%s(%s);", callback, this.toString());
                return txt.getBytes("UTF-8");
            } else {
                return this.toString().getBytes("UTF-8");
            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return null;
    }

    public byte[] toUtf8Bytes() {
        try {
            return this.toString().getBytes("UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return null;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }


}
