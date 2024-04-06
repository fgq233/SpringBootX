package com.elasticsearch.demo.domain;

/**
 * 状态码定义
 *
 * @author ly
 * @date 2020/8/7
 */
public enum Status {
    /***
     * 请求处理成功
     */
    OK(0, "操作成功"),

    /***
     * 部分成功（一般用于批量处理场景，只处理筛选后的合法数据）
     */
    WARN_PARTIAL_SUCCESS(1001, "部分成功"),

    /***
     * 有潜在的性能问题
     */
    WARN_PERFORMANCE_ISSUE(1002, "潜在的性能问题"),

    /***
     * 传入参数不对
     */
    FAIL_INVALID_PARAM(4000, "请求参数不匹配"),

    /***
     * Token无效或已过期
     */
    FAIL_INVALID_TOKEN(4001, "Token无效或已过期"),

    /***
     * 没有权限执行该操作
     */
    FAIL_NO_PERMISSION(4003, "无权执行该操作"),

    /***
     * 请求资源不存在
     */
    FAIL_NOT_FOUND(4004, "请求资源不存在"),

    /***
     * 数据校验不通过
     */
    FAIL_VALIDATION(4005, "数据校验不通过"),

    /***
     * 操作执行失败
     */
    FAIL_OPERATION(4006, "操作执行失败"),

    /***
     * 系统异常
     */
    FAIL_EXCEPTION(5000, "系统异常"),

    /***
     * 缓存清空
     */
    MEMORY_EMPTY_LOST(9999, "缓存清空"),

    /**
     * 旧密码不正确
     */
    OLD_AUTH_SECRET_ERROR(4007, "旧密码不正确"),

    /**
     * 上传文件为空
     */
    FILE_NULL(600, "上传文件为空"),

    /**
     * 无效的文件路径
     */
    FILE_PATH_ERROR(600, "无效的文件路径"),

    /**
     * 文件未找到错误
     */
    FILE_NOT_FOUND(600, "文件未找到"),

    /**
     * 文件下载失败
     */
    FILE_DOWNLOAD_ERROR(600, "文件下载失败"),

    /**
     * upload上传失败
     */
    UPLOAD_ERROR(600, "上传失败");

    private int code;
    private String label;

    Status(int code, String label) {
        this.code = code;
        this.label = label;
    }

    public int code() {
        return this.code;
    }

    public String label() {
        return this.label;
    }

    public static int getCode(String value) {
        for (Status eu : Status.values()) {
            if (eu.name().equals(value)) {
                return eu.code();
            }
        }
        return 0;
    }

    public static String getLabel(String value) {
        for (Status eu : Status.values()) {
            if (eu.name().equals(value)) {
                return eu.label();
            }
        }
        return null;
    }

}
