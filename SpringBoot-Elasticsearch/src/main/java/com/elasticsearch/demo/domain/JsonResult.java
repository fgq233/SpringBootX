package com.elasticsearch.demo.domain;

import java.io.Serializable;

/**
 * JSON返回结果
 *
 * @author ly
 * @date 2020/8/7
 */
public class JsonResult implements Serializable {
    private static final long serialVersionUID = 1001L;

    /***
     * 请求成功否
     */
    private boolean success = true;
    /***
     * 状态码
     */
    private int code;
    /***
     * 消息内容
     */
    private String msg;
    /***
     * 返回结果数据
     */
    private Object data;

    /**
     * 默认成功，无返回数据
     */
    public JsonResult() {
    }

    /**
     * 默认成功，有返回数据
     */
    public JsonResult(Object data) {
        this.code = Status.OK.code();
        this.msg = Status.OK.label();
        this.data = data;
    }

    /**
     * 默认成功，有返回数据、及附加提示信息
     */
    public JsonResult(Object data, String additionalMsg) {
        this.code = Status.OK.code();
        this.msg = Status.OK.label();
        this.data = data;
    }

    /***
     * 非成功，指定状态
     * @param status
     */
    public JsonResult(Status status) {
        this.code = status.code();
        this.msg = status.label();
        this.data = null;
        if (status != Status.OK) {
            this.success = false;
        }
    }

    /***
     * 非成功，指定状态及附加提示信息
     * @param status
     * @param additionalMsg
     */
    public JsonResult(Status status, String additionalMsg) {
        this.code = status.code();
        this.msg = status.label();
        this.data = null;
        if (status != Status.OK) {
            this.success = false;
        }
    }

    /**
     * 非成功，指定状态、返回数据
     *
     * @param status
     * @param data
     */
    public JsonResult(Status status, Object data) {
        this.code = status.code();
        this.msg = status.label();
        this.data = data;
        if (status != Status.OK) {
            this.success = false;
        }
    }

    /**
     * 非成功，指定状态、返回数据、及附加提示信息
     */
    public JsonResult(Status status, Object data, String additionalMsg) {
        this.code = status.code();
        this.msg = status.label();
        this.data = data;
        if (status != Status.OK) {
            this.success = false;
        }
    }

    /***
     * 自定义JsonResult
     * @param code
     * @param msg
     * @param data
     */
    public JsonResult(int code, String msg, Object data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
        this.success = true;
    }

    /***
     * 自定义JsonResult
     * @param code
     * @param msg
     */
    public JsonResult(int code, String msg) {
        this.code = code;
        this.msg = msg;
        this.success = true;
    }

    /***
     * 自定义JsonResult
     * @param code
     * @param label
     * @param data
     */
    public JsonResult(int code, String label, Object data, boolean success) {
        this(code, label, data);
        this.success = success;
    }

    /**
     * 设置status，如果msg为空则msg设置为status.label
     *
     * @param status
     * @return
     */
    public JsonResult status(Status status) {
        this.code = status.code();
        if (this.msg == null) {
            this.msg = status.label();
        }
        if (status != Status.OK) {
            this.success = false;
        }
        return this;
    }

    /**
     * 设置返回数据
     *
     * @param data
     * @return
     */
    public JsonResult data(Object data) {
        this.data = data;
        return this;
    }

    /**
     * 设置msg
     *
     * @param additionalMsg
     * @return
     */
    public JsonResult msg(String additionalMsg) {
        return this;
    }

    public JsonResult success(boolean success) {
        this.success = success;
        return this;
    }

    public int getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }

    public Object getData() {
        return data;
    }

    public boolean getSuccess() {
        return success;
    }



    /***
     * 请求处理成功
     */
    public static JsonResult ok() {
        return new JsonResult(Status.OK);
    }

    /***
     * 请求处理成功
     */
    public static JsonResult ok(Object data) {
        return new JsonResult(Status.OK, data);
    }

    /***
     * 部分成功（一般用于批量处理场景，只处理筛选后的合法数据）
     */
    public static JsonResult warnPartialSuccess(String msg) {
        return new JsonResult(Status.WARN_PARTIAL_SUCCESS).msg(msg);
    }

    /***
     * 有潜在的性能问题
     */
    public static JsonResult warnPerformanceIssue(String msg) {
        return new JsonResult(Status.WARN_PERFORMANCE_ISSUE).msg(msg);
    }

    /***
     * 传入参数不对
     */
    public static JsonResult failInvalidParam(String msg) {
        return new JsonResult(Status.FAIL_INVALID_PARAM).msg(msg);
    }

    /***
     * Token无效或已过期
     */
    public static JsonResult failInvalidToken(String msg) {
        return new JsonResult(Status.FAIL_INVALID_TOKEN).msg(msg);
    }

    /***
     * 没有权限执行该操作
     */
    public static JsonResult failNoPermission(String msg) {
        return new JsonResult(Status.FAIL_NO_PERMISSION).msg(msg);
    }

    /***
     * 请求资源不存在
     */
    public static JsonResult failNotFound(String msg) {
        return new JsonResult(Status.FAIL_NOT_FOUND).msg(msg);
    }

    /***
     * 数据校验不通过
     */
    public static JsonResult failValidation(String msg) {
        return new JsonResult(Status.FAIL_VALIDATION).msg(msg);
    }

    /***
     * 操作执行失败
     */
    public static JsonResult failOperation(String msg) {
        return new JsonResult(Status.FAIL_OPERATION).msg(msg);
    }

    /***
     * 系统异常
     */
    public static JsonResult failException(String msg) {
        return new JsonResult(Status.FAIL_EXCEPTION).msg(msg);
    }

    /***
     * 缓存清空
     */
    public static JsonResult memoryEmptyLost(String msg) {
        return new JsonResult(Status.MEMORY_EMPTY_LOST).msg(msg);
    }

    public JsonResult setSuccess(boolean success) {
        this.success = success;
        return this;
    }

    public JsonResult setMsg(String msg) {
        this.msg = msg;
        return this;
    }

    public JsonResult setData(Object data) {
        this.data = data;
        return this;
    }
}
