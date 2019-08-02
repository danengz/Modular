package com.yu.router_api;

import android.util.Log;
import android.util.LruCache;

import java.util.Map;

public class RouterManager {

    private static final String TAG = "RouterManager";
    private static RouterManager instance;

    //Router$$Path$$xxx 类的缓存，防止每次调用Class.forName()、newInstance()
    //结构如：
    //  {
    //    app : Router$$Path$$app对象,
    //    order : Router$$Path$$order对象
    //    ...
    //  }

    private LruCache<String, RouterLoadPath> routerClassCache;

    //目标类缓存，目前为  Activity
    //结构如：
    //  {
    //    app/MainActivity : com.yu.modular.app.MainActivity.class,
    //    order/Order_MainActivity : com.yu.modular.order.Order_MainActivity.class
    //    ...
    //  }
    private LruCache<String, Class> targetClassCache;

    private RouterManager() {
        routerClassCache = new LruCache<>(66);
        targetClassCache = new LruCache<>(66);
    }

    /**
     * 单例
     *
     * @return
     */
    public static RouterManager getInstance() {
        if (instance == null) {
            synchronized (RouterManager.class) {
                if (instance == null) {
                    instance = new RouterManager();
                }
            }
        }
        return instance;
    }


    /**
     * 获取目标类
     *
     * @param groupName
     * @param pathName
     * @return
     */
    public Class get(String groupName, String pathName) {

        Class targetClass = targetClassCache.get(groupName + "/" + pathName);

        if (targetClass == null) {
            RouterLoadPath routerPathClass = getRouterPathClass(groupName);
            Map<String, Class> pathMap = routerPathClass.loadPath();
            targetClass = pathMap.get(pathName);
            Log.e(TAG, "新建目标类文件：" + pathName);
            targetClassCache.put(groupName + "/" + pathName, targetClass);
        }

        return targetClass;

    }


    /**
     * 获取APT生成的类
     *
     * @param groupName
     * @return
     */
    public RouterLoadPath getRouterPathClass(String groupName) {

        try {
            RouterLoadPath routerLoadPath = routerClassCache.get(groupName);
            if (routerLoadPath == null) {
                String groupClassName = "com.yu.modular.apt.Router$$Path$$" + groupName;
                routerLoadPath = (RouterLoadPath) Class.forName(groupClassName).newInstance();
                Log.e(TAG, "新建RouterPath文件：" + groupName);
                routerClassCache.put("groupName", routerLoadPath);
            }
            return routerLoadPath;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }
}
