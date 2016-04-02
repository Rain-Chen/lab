package com.qianyan.lab.util.cache;

import com.qianyan.lab.util.cache.config.CacheManagerInfo;

import java.util.Map;

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

    /**
     * 根据缓存大类返回所有的key-value集合
     *
     * @param cacheName 缓存大类
     * @return key-value集合
     */
    Map<Object, Object> getAll(final String cacheName);

    /**
     * 保存缓存信息
     *
     * @param cacheName      缓存大类名称
     * @param key            缓存存储关键字
     * @param value          缓存值信息
     * @param expirationTime 失效时间，0:永久有效，x:到x秒后缓存失效
     * @return 缓存保存结果，true:缓存成功保存 false:缓存保存失败
     */
    boolean put(final String cacheName, final Object key, final Object value, final long expirationTime);

    @Deprecated
    boolean remove(final String cacheName, final Object key, final long expirationTime);

    /**
     * 删除缓存信息
     *
     * @param cacheName 缓存大类名称
     * @return true:删除成功 false:删除失败
     */
    boolean remove(final String cacheName);
}
