package com.yu.common;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 全局路径记录器（根据子模块分组）
 */
public class PathManager {

    private static Map<String, List<PathBean>> groupMap = new HashMap<>();

    /**
     *
     * @param groupName 组名，如："order"
     * @param pathName  路劲名，如："Order_MainActivity"
     * @param clazz     类对象，如：Order_MainActivity.class
     */
    public static void set(String groupName, String pathName, Class<?> clazz) {
        List<PathBean> list = groupMap.get(groupName);
        if (list == null) {
            list = new ArrayList<>();
            list.add(new PathBean(pathName, clazz));
            groupMap.put(groupName, list);
        } else {
            groupMap.put(groupName, list);
        }
    }

    /**
     *
     * @param groupName 组名
     * @param pathName  路径名
     * @return 跳转目标的class类对象
     */
    public static Class<?> get(String groupName, String pathName) {
        List<PathBean> list = groupMap.get(groupName);
        if (list == null) return null;
        for (PathBean path : list) {
            if (pathName.equalsIgnoreCase(path.getPath())) {
                return path.getClazz();
            }
        }
        return null;
    }

    /**
     * 清理、回收
     */
    public static void recycleGroup() {
        groupMap.clear();
        groupMap = null;
        System.gc();
    }
}
