package com.qianyan.lab.util.cache.listener.impl;

import com.qianyan.lab.util.cache.CacheManager;
import com.qianyan.lab.util.cache.config.CacheManagerInfo;
import com.qianyan.lab.util.cache.config.Param;
import com.qianyan.lab.util.cache.listener.CacheListener;
import com.qianyan.lab.util.cache.listener.CacheListenerMap;
import com.qianyan.lab.util.common.DateUtil;
import com.qianyan.lab.util.common.NumberUtil;
import com.qianyan.lab.util.common.StringUtil;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created with Intellij IDEA.
 * Created by rain chen
 * User: rain chen
 * Date: 16-1-29
 * Time: 下午11:08.
 */
public class ExpirationCacheListenerImpl implements CacheListener, Runnable {


    //失效时间单位(秒)
    private static final Long EXPIRATION_UNIT = 1000L;

    //任务执行间隔时间，单位毫秒，默认1s/次
    private Long interval = 1 * EXPIRATION_UNIT;

    private static final Log log = LogFactory.getLog(ExpirationCacheListenerImpl.class);
    //初始化Map，存储缓存大类小类，访问时间，有效时间

    private static ConcurrentHashMap<Object, Object> cacheMap;
    //任务调度器

    private ScheduledExecutorService executor;

    private CacheManagerInfo.CacheListeners.CacheListener cacheListener;

    public ExpirationCacheListenerImpl() {
        schemeTimer();
    }

    @Override
    public void init(CacheManagerInfo.CacheListeners.CacheListener cacheListener) {
        if (log.isDebugEnabled()) {
            log.debug("监听初始化开始...");
        }
        cacheMap = new ConcurrentHashMap<Object, Object>();
        Long cacheTimerTime;
        if (cacheListener.getParams() != null) {
            for (Param param : cacheListener.getParams().getParam()) {
                if ("schemeTimerTime".equals(param.getName())) {
                    String schemeTimerTime = param.getValue();
                    if (StringUtil.isNotEmptyOrNull(schemeTimerTime)) {
                        cacheTimerTime = NumberUtil.getLongFromObj(schemeTimerTime);
                        this.interval = cacheTimerTime * EXPIRATION_UNIT;
                        if (log.isDebugEnabled()) {
                            log.debug("任务调度成功，任务调度周期为[" + interval + "] 毫秒/每次");
                        }
                    }
                }
            }
        }
        this.cacheListener = cacheListener;
    }

    /**
     * 记录最新一次时间
     *
     * @param listenerMap 监听缓存对象
     * @return 记录成功:true 记录失败:false
     */
    @Override
    public boolean get(CacheListenerMap listenerMap) {
        if (cacheMap != null) {
            Set entryMap = cacheMap.entrySet();
            long nowTime = DateUtil.getSysDate().getTime();
            for (Object anEntryMap : entryMap) {
                Map.Entry entry = (Map.Entry) anEntryMap;
                CacheListenerMap cacheListenerMap = (CacheListenerMap) entry.getKey();
                if (listenerMap.equals(cacheListenerMap)) {
                    cacheMap.replace(cacheListenerMap, nowTime);
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public void put(CacheListenerMap listenerMap) {
        if (cacheMap != null && listenerMap != null) {
            long nowTime = DateUtil.getSysDate().getTime();
            if (0L != listenerMap.getExpirationTime()) {
                if (log.isDebugEnabled()) {
                    log.debug("CacheMap添加入新对象：[" + listenerMap.toString() + "]，时间：" + nowTime);
                }
                cacheMap.put(listenerMap, nowTime);
            }
        }
    }

    @Override
    public String getName() {
        if (this.cacheListener != null) {
            return this.cacheListener.getListenerName();
        }
        return "";
    }

    @Override
    public void stop() {
        if (executor != null) {
            executor.shutdown();
        }
        if (cacheMap != null) {
            this.remove();
        }
    }

    @Override
    public boolean remove(CacheListenerMap listenerMap) {
        boolean result = false;
        if (cacheMap.containsKey(listenerMap)) {
            cacheMap.remove(listenerMap);
            result = true;
        } else {
            for (Object o : cacheMap.keySet()) {
                CacheListenerMap cacheListenerMapVO = (CacheListenerMap) o;
                if (listenerMap.getCacheName().equals(cacheListenerMapVO.getCacheName())) {
                    cacheMap.remove(cacheListenerMapVO);
                    result = true;
                }
            }
        }
        return result;
    }

    @Override
    public void run() {
        this.remove();
    }

    /**
     * 设置定时任务
     */
    private void schemeTimer() {
        executor = Executors.newScheduledThreadPool(1, new ThreadFactory() {

            //线程计数器
            private final AtomicInteger threadCount = new AtomicInteger(1);

            @Override
            public Thread newThread(Runnable r) {
                String name = getName() + this.threadCount.getAndIncrement();
                Thread thread = new Thread(r, name);
                //设置为后台守护线程
                thread.setDaemon(true);
                return thread;
            }
        });
        executor.scheduleWithFixedDelay(this, 0, this.interval, TimeUnit.MILLISECONDS);
    }

    /**
     * 移除相关缓存记录信息
     */
    @SuppressWarnings("unchecked")
    private void remove() {
        if (cacheMap != null) {
            Set<Map.Entry<Object, Object>> entryCacheMap = cacheMap.entrySet();
            long nowTime = DateUtil.getSysDate().getTime();
            for (Map.Entry<Object, Object> eCacheMap : entryCacheMap) {
                long nearTimeOfMap = Long.parseLong(eCacheMap.getValue().toString());
                CacheListenerMap cacheListenerMap = (CacheListenerMap) eCacheMap.getKey();
                Object clearKey = eCacheMap.getKey();
                //有效时长
                long expiryTimeOfMap = cacheListenerMap.getExpirationTime();
                if (expiryTimeOfMap <= (nowTime - nearTimeOfMap)) {
                    Object removeMapResult = cacheMap.remove(clearKey);
                    if (removeMapResult != null) {
                        if (log.isInfoEnabled()) {
                            log.info("CacheMap删除成功：key为[" + clearKey + "]  value：" + removeMapResult);
                        }
                    } else {
                        if (log.isInfoEnabled()) {
                            log.info("CacheMap删除失败：key为[" + clearKey + "]  value：为空");
                        }
                    }
                    //移除具体缓存
                    CacheManager.getInstance().remove(cacheListenerMap.getCacheName(), cacheListenerMap.getKey());
                }
            }
        }
    }
}