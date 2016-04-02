package com.qianyan.lab.util.cache;

import com.qianyan.lab.util.cache.config.CacheManagerInfo;
import com.qianyan.lab.util.cache.config.Param;
import com.qianyan.lab.util.cache.listener.CacheListener;
import com.qianyan.lab.util.cache.listener.CacheListenerMap;
import com.qianyan.lab.util.common.JaxbUtil;
import com.qianyan.lab.util.common.NumberUtil;
import com.qianyan.lab.util.common.StringUtil;
import com.qianyan.lab.util.constant.LabUtilErrorMessageConst;
import com.qianyan.lab.util.exception.SystemException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created with Intellij IDEA
 * by rain chen on 2016/1/28.
 */
public final class CacheManager {

    /**
     * 配置文件
     */
    private static final String CONFIG_FILE = "classpath:cache-cfg.xml";
    /**
     * 多级缓存位置
     */
    private static final String PARAM_LEVEL_CACHE_ENGINE = "cacheEngine";
    /**
     * 缓存管理器实例
     */
    private static CacheManager cacheManager = null;
    /**
     * 日志
     */
    private final Log log = LogFactory.getLog(CacheManager.class);
    /**
     * 缓存引擎
     */
    private Map<String, ICache> cacheEngines = new HashMap<String, ICache>();
    /**
     * 缓存内容与引擎对应关系
     */
    private Map<String, CacheManagerInfo.CacheItems.CacheItem> cacheItems = new HashMap<String, CacheManagerInfo.CacheItems.CacheItem>();
    /**
     * 监听信息集合
     */
    private List<CacheManagerInfo.CacheListeners.CacheListener> cacheListenerList = new ArrayList<CacheManagerInfo.CacheListeners.CacheListener>();
    /**
     * 监听的具体实现
     */
    private List<CacheListener> cacheListeners = new ArrayList<CacheListener>();

    private CacheManager(CacheManagerInfo cacheManagerInfo) {
        if (log.isDebugEnabled()) {
            log.debug("初始化缓存管理器，配置信息" + cacheManagerInfo);
        }
        /* 初始化缓存引擎 */
        for (CacheManagerInfo.CacheEngines.CacheEngine cacheEngine :
                cacheManagerInfo.getCacheEngines().getCacheEngine()) {
            String engineName = cacheEngine.getEngineName();
            String implName = cacheEngine.getImplClass();
            try {
                if (log.isDebugEnabled()) {
                    log.debug("初始化缓存引擎开始.引擎名称：[" + engineName + "]，实现名称为：[" + implName + "]");
                }
                ICache engineImpl = (ICache) Class.forName(implName).newInstance();
                engineImpl.setName(engineName);
                engineImpl.init(cacheManagerInfo, cacheEngine);
                cacheEngines.put(engineName, engineImpl);
                if (log.isDebugEnabled()) {
                    log.debug("启动缓存引擎[" + engineName + "]...启动成功!");
                }
            } catch (InstantiationException e) {
                log.error("启动缓存引擎[" + engineName + "]...启动失败!", e);
                throw new SystemException(LabUtilErrorMessageConst.MODULE_NAME, e, false, LabUtilErrorMessageConst.ERROR_MESSAGE_200100, engineName, implName);
            } catch (IllegalAccessException e) {
                log.error("启动缓存引擎[" + engineName + "]...启动失败!", e);
                throw new SystemException(LabUtilErrorMessageConst.MODULE_NAME, e, false, LabUtilErrorMessageConst.ERROR_MESSAGE_200100, engineName, implName);
            } catch (ClassNotFoundException e) {
                log.error("缓存引擎[engineName]=" + engineName + "启动失败", e);
                throw new SystemException(LabUtilErrorMessageConst.MODULE_NAME, e, false, LabUtilErrorMessageConst.ERROR_MESSAGE_200101, engineName, implName);
            }
        }
        /* 初始化缓存内容 */
        for (CacheManagerInfo.CacheItems.CacheItem cacheItem :
                cacheManagerInfo.getCacheItems().getCacheItem()) {
            String engineName = cacheItem.getEngineName();
            String cacheName = cacheItem.getCacheName();
            if (!cacheEngines.containsKey(engineName)) {
                throw new SystemException(LabUtilErrorMessageConst.MODULE_NAME, null, false, LabUtilErrorMessageConst.ERROR_MESSAGE_200102, cacheName, engineName);
            } else {
                cacheItems.put(cacheName, cacheItem);
            }
        }
        /* 初始化监听信息*/
        if (log.isDebugEnabled()) {
            log.debug("开始执行初始化缓存监听管理器,配置信息为[" + cacheManagerInfo + "]");
        }
        CacheManagerInfo.CacheListeners cacheListeners = cacheManagerInfo.getCacheListeners();
        if (cacheListeners != null) {
            this.cacheListenerList = cacheListeners.getCacheListener();
            if (cacheListenerList != null && cacheListenerList.size() > 0) {
                for (CacheManagerInfo.CacheListeners.CacheListener cacheListener : cacheListenerList) {
                    //得到每个监听对象
                    String listenerName = cacheListener.getListenerName();//监听器名字
                    String implClass = cacheListener.getImplClass();//监听器的全路径
                    try {
                        if (log.isDebugEnabled()) {
                            log.debug("开始初始化监听引擎,监听器名称为：[" + listenerName + "],实现类名称：[" + implClass + "]");
                        }
                        //获得具体的监听实例
                        CacheListener iListenerImpl = (CacheListener) Class.forName(implClass).newInstance();
                        //初始化实例对象，Map缓存监听和清理信息的Map
                        iListenerImpl.init(cacheListener);
                        //将具体实例存到List集合
                        this.cacheListeners.add(iListenerImpl);
                        if (log.isDebugEnabled()) {
                            log.debug("启动监听引擎，名称为：[" + listenerName + "]...启动成功!");
                        }
                    } catch (InstantiationException e) {
                        log.error("启动监听引擎，名称为：[" + listenerName + "]...启动失败!", e);
                        throw new SystemException(LabUtilErrorMessageConst.MODULE_NAME, e, false, LabUtilErrorMessageConst.ERROR_MESSAGE_200100, listenerName, implClass);
                    } catch (IllegalAccessException e) {
                        log.error("监听引擎[listenerName]=" + implClass + "启动失败", e);
                        throw new SystemException(LabUtilErrorMessageConst.MODULE_NAME, e, false, LabUtilErrorMessageConst.ERROR_MESSAGE_200100, listenerName, implClass);
                    } catch (ClassNotFoundException e) {
                        log.error("监听引擎[listenerName]=" + implClass + "启动失败", e);
                        throw new SystemException(LabUtilErrorMessageConst.MODULE_NAME, e, false, LabUtilErrorMessageConst.ERROR_MESSAGE_200101, listenerName, implClass);
                    }
                }
            }
        } else {
            log.error("监听清理缓存配置文件不存在，缓存清理监听管理器初始化失败!");
        }
    }

    /**
     * 返回CacheManager实例对象 为防止多线程下并发获取CacheManager返回不是同一个对象，加入同步
     *
     * @param cacheManagerInfo 配置信息为null时返回默认实例对象
     */
    public static synchronized CacheManager getInstance(CacheManagerInfo cacheManagerInfo) {
        if (cacheManagerInfo == null) {
            return getInstance();
        } else {
            return new CacheManager(cacheManagerInfo);
        }
    }

    /**
     * @return 返回缓存管理单利对象
     */
    public static CacheManager getInstance() {
        if (cacheManager == null) {
            cacheManager = getInstance(getCacheManagerInfo(null));
        }
        return cacheManager;
    }

    /**
     * 获取缓存配置信息
     *
     * @param configFile 配置文件，为空时获取默认配置文件
     * @return 配置信息
     */
    public static CacheManagerInfo getCacheManagerInfo(String configFile) {

        String cfgFile = CONFIG_FILE;
        if (StringUtil.isNotEmptyOrNull(configFile)) {
            cfgFile = configFile;
        }
        String realFilePath = StringUtil.formatFilePath(cfgFile);
        CacheManagerInfo cacheManagerInfo;

        try {
            cacheManagerInfo = JaxbUtil.unmarshal(CacheManagerInfo.class, new FileInputStream(realFilePath));
        } catch (FileNotFoundException e) {
            throw new SystemException(LabUtilErrorMessageConst.MODULE_NAME, e, false, LabUtilErrorMessageConst.ERROR_MESSAGE_200011, realFilePath);
        }
        return cacheManagerInfo;

    }

    /**
     * 根据缓存大类加载缓存
     *
     * @param cacheName 缓存大类
     * @return ICache缓存实现
     */
    private List<ICache> loadCache(final String cacheName) {
        List<ICache> cacheEngineList = new ArrayList<ICache>(2);
        CacheManagerInfo.CacheItems.CacheItem cacheItem = cacheItems.get(cacheName);
        if (cacheItem == null) {
            throw new SystemException(LabUtilErrorMessageConst.MODULE_NAME, null, false, LabUtilErrorMessageConst.ERROR_MESSAGE_200103, cacheName);
        }

        String cacheType = cacheItem.getEngineName();
        ICache iCache = cacheEngines.get(cacheType);
        if (cacheType == null) {
            throw new SystemException(LabUtilErrorMessageConst.MODULE_NAME, null, false, LabUtilErrorMessageConst.ERROR_MESSAGE_200102, cacheName, null);
        }

        cacheEngineList.add(iCache);
        if (cacheItem.getParams() != null) {
            for (Param param : cacheItem.getParams().getParam()) {
                if (param.getName().startsWith(PARAM_LEVEL_CACHE_ENGINE)) {
                    String levelCacheEngineName = param.getValue();
                    iCache = cacheEngines.get(levelCacheEngineName);
                    cacheEngineList.add(iCache);
                }
            }
        }
        return cacheEngineList;
    }

    /**
     * 从缓存中读取指定类型的元素
     *
     * @param cacheName 缓存大类名称
     * @param key       缓存对象存储的关键字
     * @param <T>       缓存对象类型
     * @return 对应类型的缓存对象
     */
    @SuppressWarnings("unchecked")
    public <T> T get(final String cacheName, final Object key) {
        if (log.isTraceEnabled()) {
            log.trace("方法【get】调用开始，参数[" + cacheName + "]，缓存的key为[" + key + "]");
        }

        Object result = null;
        List<ICache> cacheList = this.loadCache(cacheName);
        for (int i = 0; i < cacheList.size(); i++) {
            ICache cache = cacheList.get(i);
            try {
                result = cache.get(cacheName, key);
                if (result != null) {
                    if (i > 0) {
                        cacheList.get(0).put(cacheName, key, result, this.getExpirationTime(cacheName));
                    }
                    break;
                }
            } catch (Exception e) {
                log.warn("读取缓存异常", new SystemException(LabUtilErrorMessageConst.MODULE_NAME, e, false, LabUtilErrorMessageConst.ERROR_MESSAGE_200104, cache.getName(), cacheName));
            }
        }
        for (int j = 0; cacheListeners != null && j < cacheListeners.size(); j++) {
            CacheListener cacheListenerImpl = cacheListeners.get(j);
            CacheListenerMap listenerMap = new CacheListenerMap();
            listenerMap.setCacheName(cacheName);
            listenerMap.setKey(key);
            cacheListenerImpl.get(listenerMap);
        }

        if (log.isTraceEnabled()) {
            log.trace("方法【get】调用结束，出参为[" + result + "]");
        }
        return (T) result;
    }

    /**
     * 根据缓存大类返回 key-value的集合
     *
     * @param cacheName 缓存大类
     * @return Map集合
     */
    public Map getAll(final String cacheName) {
        ICache cache = this.loadCache(cacheName).get(0);
        try {
            return cache.getAll(cacheName);
        } catch (Exception e) {
            log.warn("获取缓存错误", new SystemException(LabUtilErrorMessageConst.MODULE_NAME, e, false, LabUtilErrorMessageConst.ERROR_MESSAGE_200104, cache.getName(), cacheName));
            return null;
        }
    }

    /**
     * 保存缓存信息
     *
     * @param cacheName      缓存大类名称
     * @param key            缓存存储关键字
     * @param value          缓存值
     * @param expirationTime 缓存过期时间，0:永久有效 x:缓存x秒后失效
     * @return true:保存成功 false:保存失败
     */
    public boolean put(final String cacheName, final Object key, final Object value, final long expirationTime) {
        boolean result = false;
        for (ICache cache : this.loadCache(cacheName)) {
            try {
                result = cache.put(cacheName, key, value, expirationTime);
            } catch (Exception e) {
                log.warn("保存缓存异常", new SystemException(LabUtilErrorMessageConst.MODULE_NAME, e, false, LabUtilErrorMessageConst.ERROR_MESSAGE_200104, cache.getName(), cacheName));
                result = false;
            }
        }

        if (cacheListeners != null) {
            for (CacheListener cacheListenerImpl : cacheListeners) {
                CacheListenerMap listenerMap = new CacheListenerMap();
                listenerMap.setCacheName(cacheName);
                listenerMap.setKey(key);
                listenerMap.setExpirationTime(expirationTime);
                cacheListenerImpl.put(listenerMap);
            }
        }
        return result;
    }

    /**
     * 永久保存缓存信息
     *
     * @param cacheName 缓存大类名称
     * @param key       缓存存储关键字
     * @param value     缓存值
     * @return true:保存成功 false:保存失败
     */
    public boolean put(final String cacheName, final Object key, final Object value) {
        boolean result = false;
        for (ICache cache : this.loadCache(cacheName)) {
            try {
                Long expirationTime = getExpirationTime(cacheName);
                result = this.put(cacheName, key, value, expirationTime);
            } catch (Exception e) {
                log.warn("保存缓存异常", new SystemException(LabUtilErrorMessageConst.MODULE_NAME, e, false, LabUtilErrorMessageConst.ERROR_MESSAGE_200104, cache.getName(), cacheName));
                result = false;
            }
        }
        return result;
    }

    /**
     * 删除缓存信息
     *
     * @param cacheName      缓存大类名称
     * @param key            缓存关键字
     * @param expirationTime 缓存过期时间，0:永久有效 x:缓存x秒后失效
     * @return true:删除成功 false:删除失败
     */
    public boolean remove(final String cacheName, final Object key, final long expirationTime) {
        boolean result = false;
        for (ICache cache : this.loadCache(cacheName)) {
            result = cache.remove(cacheName, key, expirationTime);
            this.removeListener(cacheName, key, expirationTime);
        }
        return result;
    }

    /**
     * 删除缓存信息
     *
     * @param cacheName 缓存大类名称
     * @param key       缓存关键字
     * @return true:删除成功 false:删除失败
     */
    public boolean remove(final String cacheName, final Object key) {
        return this.remove(cacheName, key, 0L);
    }

    /**
     * 删除缓存大类信息
     *
     * @param cacheName 缓存大类名称
     * @return true:删除成功 false:删除失败
     */
    public boolean remove(final String cacheName) {
        boolean result = false;
        for (ICache cache : this.loadCache(cacheName)) {
            result = cache.remove(cacheName);
            this.removeListener(cacheName, null, null);
        }
        return result;
    }

    /**
     * 获取指定缓存引擎
     *
     * @param cacheEngineName 缓存引擎配置名称
     * @return 缓存引擎
     */
    public ICache getCacheEngines(String cacheEngineName) {
        return this.cacheEngines.get(cacheEngineName);
    }


    private Long getExpirationTime(final String cacheName) {
        Long defaultExpirationTime = 0L;
        CacheManagerInfo.CacheItems.CacheItem cacheItem = cacheItems.get(cacheName);

        String cDefaultExpireTime = cacheItem.getDefaultExpireTime();
        if (StringUtil.isNotEmptyOrNull(cDefaultExpireTime)) {
            defaultExpirationTime = NumberUtil.getLongFromObj(cDefaultExpireTime) * 1000L;
        }
        return defaultExpirationTime;
    }

    /**
     * 移除监听信息
     *
     * @param cacheName      缓存大类名称
     * @param key            缓存存储关键字
     * @param expirationTime 过期时间
     * @return true:删除成功 false:删除失败
     */
    private boolean removeListener(final String cacheName, final Object key, final Long expirationTime) {
        boolean result = false;
        if (cacheListeners != null) {
            for (CacheListener cacheListenerImpl : cacheListeners) {
                CacheListenerMap listenerMap = new CacheListenerMap();
                listenerMap.setCacheName(cacheName);
                listenerMap.setKey(key);
                listenerMap.setExpirationTime(expirationTime);
                result = cacheListenerImpl.remove(listenerMap);
            }
        } else {
            log.warn("监听清理缓存配置文件不存在，缓存清理监听管理器初始化失败!)");
        }
        return result;
    }
}
