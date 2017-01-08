package com.yjt.zeuslivepush.utils;

import java.util.Map;

/**
 * 文件描述
 * AUTHOR: yangjiantong
 * DATE: 2016/12/17
 */
public class MapUtil {

    public static <T, K> T parseMapValue(Map<K, T> map, K key, T defValue) {
        return map != null && map.containsKey(key)?map.get(key):defValue;
    }
}
