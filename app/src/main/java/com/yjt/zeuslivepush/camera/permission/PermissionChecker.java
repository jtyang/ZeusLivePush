package com.yjt.zeuslivepush.camera.permission;

/**
 * 文件描述
 * AUTHOR: yangjiantong
 * DATE: 2016/12/17
 */
public abstract class PermissionChecker {

    public abstract Object startPrivilegedOperation(String var1);

    public abstract void stopPrivilegedOperation(Object var1);
}
