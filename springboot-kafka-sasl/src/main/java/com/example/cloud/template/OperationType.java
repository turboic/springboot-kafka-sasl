package com.example.cloud.template;

import java.util.HashSet;
import java.util.Set;

public enum OperationType {
    INSERT("insert", "插入操作"),
    DELETE("delete", "删除操作"),
    UPDATE("update", "更新操作"),
    QUERY("query", "查询操作"),
    IMPORT("import", "导入操作"),
    EXPORT("export", "导出操作"),
    DOWNLOAD("download", "下载操作"),
    UPLOAD("upload", "上传操作"),
    LOGIN("login", "登录操作"),
    LOGOUT("logout", "登出操作"),
    OTHER("other", "其他操作");

    private String operationCode;
    private String operationName;
    private static Set<String> TYPE_SET = new HashSet();

    private OperationType(String operationCode, String operationName) {
        this.operationCode = operationCode;
        this.operationName = operationName;
    }

    public String getOperationCode() {
        return this.operationCode;
    }

    public void setOperationCode(String operationCode) {
        this.operationCode = operationCode;
    }

    public String getOperationName() {
        return this.operationName;
    }

    public void setOperationName(String operationName) {
        this.operationName = operationName;
    }

    public static OperationType convertoEnum(String operationCode) {
        if (!isValid(operationCode)) {
            return null;
        } else {
            OperationType[] var1 = values();
            int var2 = var1.length;

            for(int var3 = 0; var3 < var2; ++var3) {
                OperationType operationType = var1[var3];
                if (operationCode.equals(operationType.getOperationCode())) {
                    return operationType;
                }
            }

            return null;
        }
    }

    public static boolean isValid(String operationCode) {
        return TYPE_SET.contains(operationCode);
    }

    public boolean isEqual(String operationCode) {
        return operationCode != null && this.operationCode.equals(operationCode);
    }

    static {
        OperationType[] types = values();
        OperationType[] var1 = types;
        int var2 = types.length;

        for(int var3 = 0; var3 < var2; ++var3) {
            OperationType type = var1[var3];
            TYPE_SET.add(type.getOperationCode());
        }

    }
}
