package com.qianyan.lab.util.cache;

import com.qianyan.lab.util.cache.config.CacheManagerInfo;

/**
 * Created with Intellij IDEA
 * by rain chen on 2016/1/28.
 */
public interface ICache {

    /**
     * 获取缓存引擎名称
     *
     * @return 缓存引擎名称
     */
    String getName();

    /**
     * 设置缓存引擎名称
     *
     * @param cacheEngineName 缓存引擎名称
     */
    void setName(String cacheEngineName);

    /**
     * 初始化缓存引擎
     *
     * @param cacheManagerInfo 缓存配置信息
     * @param cacheEngine      引擎配置信息
     */
    void init(CacheManagerInfo cacheManagerInfo, CacheManagerInfo.CacheEngines.CacheEngine cacheEngine);

    /**
     * 停止缓存管理器
     */
    void stop();

    /**
     * 从缓存中读取指定类型的元素
     *
     * @param cacheName 缓存大类名称
     * @param key       缓存对象key
     * @param <T>       缓存对象类型
     * @return 对应缓存对象
     */
    <T> T get(final String cacheName, final Object key);

}
