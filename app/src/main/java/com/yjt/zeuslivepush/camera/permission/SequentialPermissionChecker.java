package com.yjt.zeuslivepush.camera.permission;


import com.yjt.zeuslivepush.utils.ThreadUtil;

import java.util.ArrayDeque;

/**
 * 文件描述
 * AUTHOR: yangjiantong
 * DATE: 2016/12/17
 */
public class SequentialPermissionChecker extends PermissionChecker {
    private final ArrayDeque<String> _PermissionRequests = new ArrayDeque();

    public synchronized Object startPrivilegedOperation(String permission) {
        while (!this._PermissionRequests.isEmpty()) {
            ThreadUtil.wait(this);
        }

        this._PermissionRequests.push(permission);
        return permission;
    }

    public synchronized void stopPrivilegedOperation(Object token) {
        if (token != this._PermissionRequests.peek()) {
            throw new IllegalArgumentException("invalid token: " + token);
        } else {
            this._PermissionRequests.pop();
            this.notifyAll();
        }
    }
}
