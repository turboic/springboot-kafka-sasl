package com.example.cloud.entity;

/**
 * 请求返回类
 */
public class Result {
    private int code;
    private String msg;
    private Object data;

    public Result(int code, String msg, Object data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    public Result() {

    }
    /**
     * 返回失败结果
     * @param msg 失败信息
     * @param data 数据
     * @return FeedbackResult实体
     */
    public static Result build(int code, String msg, Object data) {
        Result fr = new Result();
        fr.setCode(code);
        fr.setMsg(msg);
        fr.setData(data);
        return fr;
    }

    /**
     * 返回失败结果
     * @param msg 失败信息
     * @param data 数据
     * @return FeedbackResult实体
     */
    public static Result failure(String msg, Object data) {
        Result fr = new Result();
        fr.setCode(500);
        fr.setMsg(msg);
        fr.setData(data);
        return fr;
    }
    public static Result failure(String msg) {
        Result fr = new Result();
        fr.setCode(500);
        fr.setMsg(msg);
        fr.setData(null);
        return fr;
    }

    /**
     * 返回成功结果
     * @param msg 信息
     * @param data 数据
     * @return FeedbackResult实体
     */
    public static Result ok(String msg, Object data) {
        Result fr = new Result();
        fr.setCode(200);
        fr.setMsg(msg);
        fr.setData(data);
        return fr;
    }

    public static Result ok(Object data) {
        Result fr = new Result();
        fr.setCode(200);
        fr.setMsg("操作成功");
        fr.setData(data);
        return fr;
    }


    public static Result ok() {
        Result fr = new Result();
        fr.setCode(200);
        fr.setMsg("操作成功");
        fr.setData(null);
        return fr;
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

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
