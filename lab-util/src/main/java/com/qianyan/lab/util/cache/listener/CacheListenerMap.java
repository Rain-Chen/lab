package com.qianyan.lab.util.cache.listener;

/**
 * Created with Intellij IDEA.
 * Created by rain chen
 * User: rain chen
 * Date: 16-1-29
 * Time: 下午10:46.
 */
public class CacheListenerMap {

    //缓存大类名称
    private String cacheName;

    //缓存对象唯一标示
    private Object key;

    //缓存过期时间
    private Long expirationTime;

    //重写equals和hash code,toString方法
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null) return false;
        if (getClass() != o.getClass()) return false;

        CacheListenerMap that = (CacheListenerMap) o;
        if (cacheName != null ? !cacheName.equals(that.cacheName) : that.cacheName != null) return false;
        if (cacheName == null) {
            if (that.cacheName != null) return false;
        } else if (cacheName.equals(that.cacheName)) {
            return false;
        }
        if (key == null) {
            if (that.key != null) {
                return false;
            }
        } else if (!key.equals(that.key)) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int result = cacheName != null ? cacheName.hashCode() : 0;
        result = 31 * result + (key != null ? key.hashCode() : 0);
        result = 31 * result + (expirationTime != null ? expirationTime.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "CacheListenerMap{" +
                "cacheName='" + cacheName + '\'' +
                ", key=" + key +
                ", expirationTime=" + expirationTime +
                '}';
    }

    public String getCacheName() {
        return cacheName;
    }

    public void setCacheName(String cacheName) {
        this.cacheName = cacheName;
    }

    public Object getKey() {
        return key;
    }

    public void setKey(Object key) {
        this.key = key;
    }

    public Long getExpirationTime() {
        return expirationTime;
    }

    public void setExpirationTime(Long expirationTime) {
        this.expirationTime = expirationTime;
    }
}
