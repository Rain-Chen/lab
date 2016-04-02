package com.qianyan.lab.util.cache.listener;

import com.qianyan.lab.util.cache.config.CacheManagerInfo;

/**
 * Created with Intellij IDEA.
 * Created by rain chen
 * User: rain chen
 * Date: 16-1-29
 * Time: 下午10:40.
 */
public interface CacheListener {

    /**
     * 初始化需要缓存监听cacheMap和清理的clearMap
     */
    void init(CacheManagerInfo.CacheListeners.CacheListener cacheListener);


    /**
     * 获取缓存对象
     *
     * @param listenerMap 监听缓存对象
     */
    boolean get(CacheListenerMap listenerMap);

    /**
     * 存放监听缓存信息到cacheMap
     *
     * @param listenerMap 缓存监听对象
     */
    void put(CacheListenerMap listenerMap);

    /**
     * 获取监听对象名称
     */
    String getName();

    /**
     * 停止监听，map为空
     */
    void stop();

    /**
     * 移除失效缓存操作
     *
     * @param listenerMap 缓存监听对象
     */
    boolean remove(CacheListenerMap listenerMap);
}
