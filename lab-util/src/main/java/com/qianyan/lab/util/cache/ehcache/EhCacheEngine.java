package com.qianyan.lab.util.cache.ehcache;

import com.qianyan.lab.util.cache.ICache;
import com.qianyan.lab.util.cache.config.CacheManagerInfo;
import com.qianyan.lab.util.cache.config.Param;
import com.qianyan.lab.util.common.StringUtil;
import net.sf.ehcache.Cache;
import net.sf.ehcache.Element;
import net.sf.ehcache.config.CacheConfiguration;
import net.sf.ehcache.config.Configuration;
import net.sf.ehcache.config.ConfigurationFactory;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created with Intellij IDEA.
 * Created by rain chen
 * User: rain chen
 * Date: 16-1-30
 * Time: 下午5:01.
 */
public class EhCacheEngine implements ICache {

    private static final Log log = LogFactory.getLog(EhCacheEngine.class);

    /**
     * ehcache缓存配置文件
     */
    private static final String DEFAULT_CACHE_CONFIG_FILE = "/ehcache.xml";

    /**
     * 配置文件名称
     */
    private static final String PARAM_CONFIG_FILE_NAME = "configFile";

    /**
     * 缓存对象使用的模板
     */
    private static final String PARAM_CACHE_ITEM_TEMPLATE_NAME = "cacheTemplateName";

    /**
     * 本地非堆栈内存大小
     */
    private static final String PARAM_MAX_BYTES_LOCAL_OFFER_HEAP = "maxBytesLocalOffHeap";

    /**
     * 本地堆栈内存大小
     */
    private static final String PARAM_MAX_BYTES_LOCAL_HEAP = "maxBytesLocalHeap";

    /**
     * 本地堆可以存储的最大缓存数量
     */
    private static final String PARAM_MAX_ENTRIES_LOCAL_HEAP = "maxEntriesLocalHeap";

    /**
     * EhCache引擎
     */
    private net.sf.ehcache.CacheManager manager = null;

    /**
     * 缓存名称
     */
    private String name = null;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public void init(CacheManagerInfo cacheManagerInfo, CacheManagerInfo.CacheEngines.CacheEngine cacheEngine) {
        String configFileName = name;
        List<Param> cacheEngineParams = null;
        if (cacheEngine.getParams() != null) {
            cacheEngineParams = cacheEngine.getParams().getParam();
        } else {
            cacheEngineParams = new ArrayList<Param>();
        }
        for (Param param : cacheEngineParams) {
            if (PARAM_CONFIG_FILE_NAME.equalsIgnoreCase(param.getName())) {
                configFileName = param.getValue();
                break;
            }
        }
        if (StringUtil.isEmptyOrNull(configFileName)) {
            configFileName = DEFAULT_CACHE_CONFIG_FILE;
        }

        URL configurationFileURL = this.getClass().getResource(configFileName);
        Configuration configuration = ConfigurationFactory.parseConfiguration(configurationFileURL);
        String path = configuration.getDiskStoreConfiguration().getPath();
        path = StringUtil.formatFilePath(path);
        configuration.getDiskStoreConfiguration().setPath(path);
        if (log.isDebugEnabled()) {
            log.debug("EhCache缓存文件目录配置为[" + path + "]");
        }
        manager = net.sf.ehcache.CacheManager.create(configuration);
        if (log.isDebugEnabled()) {
            log.debug("EhCache已经启动!启动配置文件为[" + configurationFileURL + "]");
        }

        for (CacheManagerInfo.CacheItems.CacheItem cacheItem : cacheManagerInfo.getCacheItems().getCacheItem()) {
            String cacheName = cacheItem.getCacheName();
            String cacheType = cacheItem.getEngineName();
            if (cacheEngine.getEngineName().equals(cacheType)) {
                List<Param> cacheItemParams = null;
                if (cacheItem.getParams() != null) {
                    cacheItemParams = cacheItem.getParams().getParam();
                } else {
                    cacheItemParams = new ArrayList<Param>();
                }
                String cacheTemplateName = null;
                String maxBytesLocalOffHeap = null;
                String maxBytesLocalHeap = null;
                Long maxEntriesLocalHeap = null;
                // 获取缓存项目使用缓存模板参数
                for (Param param : cacheItemParams) {
                    if (PARAM_CACHE_ITEM_TEMPLATE_NAME.equalsIgnoreCase(param.getName())) {
                        cacheTemplateName = param.getValue();
                    } else if (PARAM_MAX_BYTES_LOCAL_HEAP.equalsIgnoreCase(param.getName())) {
                        maxBytesLocalHeap = param.getValue();
                    } else if (PARAM_MAX_BYTES_LOCAL_OFFER_HEAP.equalsIgnoreCase(param.getName())) {
                        maxBytesLocalOffHeap = param.getValue();
                    } else if (PARAM_MAX_ENTRIES_LOCAL_HEAP.equals(param.getName())) {
                        maxEntriesLocalHeap = Long.valueOf(param.getValue());
                    }
                }

                // 模板不存在创建模板
                if (!manager.cacheExists(cacheName)) {
                    CacheConfiguration config = null;
                    if (StringUtil.isEmptyOrNull(cacheTemplateName)) {
                        config = manager.getConfiguration().getDefaultCacheConfiguration();
                    } else {
                        Cache customCacheTemplate = manager.getCache(cacheTemplateName);
                        config = customCacheTemplate.getCacheConfiguration();
                    }
                    // 设置使用的最大堆内存
                    if (!StringUtil.isEmptyOrNull(maxBytesLocalHeap)) {
                        config.setMaxBytesLocalHeap(maxBytesLocalHeap);
                    }
                    // 设置使用的最大非堆内存
                    if (!StringUtil.isEmptyOrNull(maxBytesLocalOffHeap)) {
                        config.setMaxBytesLocalOffHeap(maxBytesLocalOffHeap);
                    }
                    // 设置最大的本地缓存对象梳理
                    if (maxEntriesLocalHeap != null) {
                        config.setMaxEntriesLocalHeap(maxEntriesLocalHeap);
                    }
                    // 设置配置信息
                    config.setName(cacheName);
                    Cache customCache = new Cache(config);
                    manager.addCache(customCache);
                }
            }
        }
    }

    @Override
    public void stop() {
        manager.shutdown();
        if (log.isDebugEnabled()) {
            log.info("EhCache已经关闭!");
        }
    }

    public Cache getCache(String cacheName) {
        Cache cache = manager.getCache(cacheName);
        if (cache != null) {
            return cache;
        } else {
            synchronized (manager) {
                cache = manager.getCache(cacheName);
                if (cache == null) {
                    manager.addCache(cacheName);
                    return manager.getCache(cacheName);
                } else {
                    return cache;
                }
            }
        }
    }

    /**
     * 从缓存获取值
     *
     * @param cacheName 缓存大类名称
     * @param key       缓存对象key
     * @param <T>       返回对象实例
     */
    @SuppressWarnings("unchecked")
    @Override
    public <T> T get(String cacheName, Object key) {
        if (key == null) {
            return null;
        }
        Element element = this.getCache(cacheName).get(key);
        return (T) (element == null ? null : element.getObjectValue());
    }

    private List getKeys(String cacheName) {
        return this.getCache(cacheName).getKeys();
    }

    /**
     * 根据缓存大类获取所有的key-value集合
     *
     * @param cacheName 缓存大类
     */
    @Override
    public Map<Object, Object> getAll(String cacheName) {
        if (cacheName == null) {
            return null;
        }
        List list = getKeys(cacheName);
        if (list == null || list.size() == 0) {
            return null;
        }
        Map map = this.getCache(cacheName).getAll(list);
        Map<Object, Object> resultMap = new HashMap<Object, Object>(map.size());
        for (Object obj : map.entrySet()) {
            Map.Entry entry = (Map.Entry) obj;
            Element element = (Element) entry.getValue();
            resultMap.put(entry.getKey(), element.getObjectValue());
        }
        return resultMap;
    }

    /**
     * @param cacheName 缓存大类名称
     * @param key       缓存存储关键字
     * @param value     缓存值信息
     */
    public boolean put(String cacheName, Object key, Object value) {
        return this.put(cacheName, key, value, 0L);
    }

    /**
     * @param cacheName      缓存大类名称
     * @param key            缓存存储关键字
     * @param value          缓存值信息
     * @param expirationTime 失效时间，0:永久有效，x:到x秒后缓存失效
     * @return boolean
     */
    @Override
    public boolean put(String cacheName, Object key, Object value, long expirationTime) {
        if (key == null) {
            return false;
        }
        Cache cache = this.getCache(cacheName);
        Element element = new Element(key, value);
        if (expirationTime > 0) {
            element.setTimeToLive((int) expirationTime);
        }
        cache.put(element);
        return true;
    }

    /**
     * @param cacheName      缓存大类名称
     * @param key            缓存存储关键字
     * @param expirationTime 失效时间，0:永久有效，x:到x秒后缓存失效
     */
    @Override
    public boolean remove(String cacheName, Object key, long expirationTime) {
        if (key == null) {
            return false;
        }
        Cache cache = this.getCache(cacheName);
        cache.remove(key);
        return true;
    }

    /**
     * 移除所有缓存
     *
     * @param cacheName 缓存大类名称
     */
    @Override
    public boolean remove(String cacheName) {
        manager.getCache(cacheName).removeAll();
        return true;
    }

}
