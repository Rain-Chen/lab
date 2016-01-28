
package com.qianyan.lab.util.cache.config;

import javax.xml.bind.annotation.*;
import java.util.ArrayList;
import java.util.List;


/**
 * <p>Java class for anonymous complex type.
 *
 * <p>The following schema fragment specifies the expected content contained within this class.
 *
 * <pre>
 * &lt;complexType>
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="CacheEngines">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element name="CacheEngine" maxOccurs="unbounded">
 *                     &lt;complexType>
 *                       &lt;complexContent>
 *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                           &lt;sequence>
 *                             &lt;element name="engineName" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                             &lt;element name="implClass" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                             &lt;element name="engineDesc" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                             &lt;element name="params" minOccurs="0">
 *                               &lt;complexType>
 *                                 &lt;complexContent>
 *                                   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                                     &lt;sequence>
 *                                       &lt;element name="param" type="{}Param" maxOccurs="unbounded" minOccurs="0"/>
 *                                     &lt;/sequence>
 *                                   &lt;/restriction>
 *                                 &lt;/complexContent>
 *                               &lt;/complexType>
 *                             &lt;/element>
 *                           &lt;/sequence>
 *                         &lt;/restriction>
 *                       &lt;/complexContent>
 *                     &lt;/complexType>
 *                   &lt;/element>
 *                 &lt;/sequence>
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *         &lt;element name="CacheListeners">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element name="CacheListener" maxOccurs="unbounded" minOccurs="0">
 *                     &lt;complexType>
 *                       &lt;complexContent>
 *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                           &lt;sequence>
 *                             &lt;element name="listenerName" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                             &lt;element name="implClass" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                             &lt;element name="listenerDesc" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                             &lt;element name="params" minOccurs="0">
 *                               &lt;complexType>
 *                                 &lt;complexContent>
 *                                   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                                     &lt;sequence>
 *                                       &lt;element name="param" type="{}Param" maxOccurs="unbounded" minOccurs="0"/>
 *                                     &lt;/sequence>
 *                                   &lt;/restriction>
 *                                 &lt;/complexContent>
 *                               &lt;/complexType>
 *                             &lt;/element>
 *                           &lt;/sequence>
 *                         &lt;/restriction>
 *                       &lt;/complexContent>
 *                     &lt;/complexType>
 *                   &lt;/element>
 *                 &lt;/sequence>
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *         &lt;element name="CacheItems">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element name="CacheItem" maxOccurs="unbounded" minOccurs="0">
 *                     &lt;complexType>
 *                       &lt;complexContent>
 *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                           &lt;sequence>
 *                             &lt;element name="cacheName" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                             &lt;element name="engineName" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                             &lt;element name="cacheDesc" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                             &lt;element name="defaultExpireTime" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                             &lt;element name="params" minOccurs="0">
 *                               &lt;complexType>
 *                                 &lt;complexContent>
 *                                   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                                     &lt;sequence>
 *                                       &lt;element name="param" type="{}Param" maxOccurs="unbounded" minOccurs="0"/>
 *                                     &lt;/sequence>
 *                                   &lt;/restriction>
 *                                 &lt;/complexContent>
 *                               &lt;/complexType>
 *                             &lt;/element>
 *                           &lt;/sequence>
 *                         &lt;/restriction>
 *                       &lt;/complexContent>
 *                     &lt;/complexType>
 *                   &lt;/element>
 *                 &lt;/sequence>
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 *
 *
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
        "cacheEngines",
        "cacheListeners",
        "cacheItems"
})
@XmlRootElement(name = "CacheManagerInfo")
public class CacheManagerInfo {

    @XmlElement(name = "CacheEngines", required = true)
    protected CacheManagerInfo.CacheEngines cacheEngines;
    @XmlElement(name = "CacheListeners", required = true)
    protected CacheManagerInfo.CacheListeners cacheListeners;
    @XmlElement(name = "CacheItems", required = true)
    protected CacheManagerInfo.CacheItems cacheItems;

    /**
     * Gets the value of the cacheEngines property.
     *
     * @return
     *     possible object is
     *     {@link CacheManagerInfo.CacheEngines }
     *
     */
    public CacheManagerInfo.CacheEngines getCacheEngines() {
        return cacheEngines;
    }

    /**
     * Sets the value of the cacheEngines property.
     *
     * @param value
     *     allowed object is
     *     {@link CacheManagerInfo.CacheEngines }
     *
     */
    public void setCacheEngines(CacheManagerInfo.CacheEngines value) {
        this.cacheEngines = value;
    }

    /**
     * Gets the value of the cacheListeners property.
     *
     * @return
     *     possible object is
     *     {@link CacheManagerInfo.CacheListeners }
     *
     */
    public CacheManagerInfo.CacheListeners getCacheListeners() {
        return cacheListeners;
    }

    /**
     * Sets the value of the cacheListeners property.
     *
     * @param value
     *     allowed object is
     *     {@link CacheManagerInfo.CacheListeners }
     *
     */
    public void setCacheListeners(CacheManagerInfo.CacheListeners value) {
        this.cacheListeners = value;
    }

    /**
     * Gets the value of the cacheItems property.
     *
     * @return
     *     possible object is
     *     {@link CacheManagerInfo.CacheItems }
     *
     */
    public CacheManagerInfo.CacheItems getCacheItems() {
        return cacheItems;
    }

    /**
     * Sets the value of the cacheItems property.
     *
     * @param value
     *     allowed object is
     *     {@link CacheManagerInfo.CacheItems }
     *
     */
    public void setCacheItems(CacheManagerInfo.CacheItems value) {
        this.cacheItems = value;
    }


    /**
     * <p>Java class for anonymous complex type.
     *
     * <p>The following schema fragment specifies the expected content contained within this class.
     *
     * <pre>
     * &lt;complexType>
     *   &lt;complexContent>
     *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *       &lt;sequence>
     *         &lt;element name="CacheEngine" maxOccurs="unbounded">
     *           &lt;complexType>
     *             &lt;complexContent>
     *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *                 &lt;sequence>
     *                   &lt;element name="engineName" type="{http://www.w3.org/2001/XMLSchema}string"/>
     *                   &lt;element name="implClass" type="{http://www.w3.org/2001/XMLSchema}string"/>
     *                   &lt;element name="engineDesc" type="{http://www.w3.org/2001/XMLSchema}string"/>
     *                   &lt;element name="params" minOccurs="0">
     *                     &lt;complexType>
     *                       &lt;complexContent>
     *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *                           &lt;sequence>
     *                             &lt;element name="param" type="{}Param" maxOccurs="unbounded" minOccurs="0"/>
     *                           &lt;/sequence>
     *                         &lt;/restriction>
     *                       &lt;/complexContent>
     *                     &lt;/complexType>
     *                   &lt;/element>
     *                 &lt;/sequence>
     *               &lt;/restriction>
     *             &lt;/complexContent>
     *           &lt;/complexType>
     *         &lt;/element>
     *       &lt;/sequence>
     *     &lt;/restriction>
     *   &lt;/complexContent>
     * &lt;/complexType>
     * </pre>
     *
     *
     */
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = {
            "cacheEngine"
    })
    public static class CacheEngines {

        @XmlElement(name = "CacheEngine", required = true)
        protected List<CacheEngine> cacheEngine;

        /**
         * Gets the value of the cacheEngine property.
         *
         * <p>
         * This accessor method returns a reference to the live list,
         * not a snapshot. Therefore any modification you make to the
         * returned list will be present inside the JAXB object.
         * This is why there is not a <CODE>set</CODE> method for the cacheEngine property.
         *
         * <p>
         * For example, to add a new item, do as follows:
         * <pre>
         *    getCacheEngine().add(newItem);
         * </pre>
         *
         *
         * <p>
         * Objects of the following type(s) are allowed in the list
         * {@link CacheManagerInfo.CacheEngines.CacheEngine }
         *
         *
         */
        public List<CacheEngine> getCacheEngine() {
            if (cacheEngine == null) {
                cacheEngine = new ArrayList<CacheEngine>();
            }
            return this.cacheEngine;
        }


        /**
         * <p>Java class for anonymous complex type.
         *
         * <p>The following schema fragment specifies the expected content contained within this class.
         *
         * <pre>
         * &lt;complexType>
         *   &lt;complexContent>
         *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
         *       &lt;sequence>
         *         &lt;element name="engineName" type="{http://www.w3.org/2001/XMLSchema}string"/>
         *         &lt;element name="implClass" type="{http://www.w3.org/2001/XMLSchema}string"/>
         *         &lt;element name="engineDesc" type="{http://www.w3.org/2001/XMLSchema}string"/>
         *         &lt;element name="params" minOccurs="0">
         *           &lt;complexType>
         *             &lt;complexContent>
         *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
         *                 &lt;sequence>
         *                   &lt;element name="param" type="{}Param" maxOccurs="unbounded" minOccurs="0"/>
         *                 &lt;/sequence>
         *               &lt;/restriction>
         *             &lt;/complexContent>
         *           &lt;/complexType>
         *         &lt;/element>
         *       &lt;/sequence>
         *     &lt;/restriction>
         *   &lt;/complexContent>
         * &lt;/complexType>
         * </pre>
         *
         *
         */
        @XmlAccessorType(XmlAccessType.FIELD)
        @XmlType(name = "", propOrder = {
                "engineName",
                "implClass",
                "engineDesc",
                "params"
        })
        public static class CacheEngine {

            @XmlElement(required = true)
            protected String engineName;
            @XmlElement(required = true)
            protected String implClass;
            @XmlElement(required = true)
            protected String engineDesc;
            protected CacheManagerInfo.CacheEngines.CacheEngine.Params params;

            /**
             * Gets the value of the engineName property.
             *
             * @return
             *     possible object is
             *     {@link String }
             *
             */
            public String getEngineName() {
                return engineName;
            }

            /**
             * Sets the value of the engineName property.
             *
             * @param value
             *     allowed object is
             *     {@link String }
             *
             */
            public void setEngineName(String value) {
                this.engineName = value;
            }

            /**
             * Gets the value of the implClass property.
             *
             * @return
             *     possible object is
             *     {@link String }
             *
             */
            public String getImplClass() {
                return implClass;
            }

            /**
             * Sets the value of the implClass property.
             *
             * @param value
             *     allowed object is
             *     {@link String }
             *
             */
            public void setImplClass(String value) {
                this.implClass = value;
            }

            /**
             * Gets the value of the engineDesc property.
             *
             * @return
             *     possible object is
             *     {@link String }
             *
             */
            public String getEngineDesc() {
                return engineDesc;
            }

            /**
             * Sets the value of the engineDesc property.
             *
             * @param value
             *     allowed object is
             *     {@link String }
             *
             */
            public void setEngineDesc(String value) {
                this.engineDesc = value;
            }

            /**
             * Gets the value of the params property.
             *
             * @return
             *     possible object is
             *     {@link CacheManagerInfo.CacheEngines.CacheEngine.Params }
             *
             */
            public CacheManagerInfo.CacheEngines.CacheEngine.Params getParams() {
                return params;
            }

            /**
             * Sets the value of the params property.
             *
             * @param value
             *     allowed object is
             *     {@link CacheManagerInfo.CacheEngines.CacheEngine.Params }
             *
             */
            public void setParams(CacheManagerInfo.CacheEngines.CacheEngine.Params value) {
                this.params = value;
            }


            /**
             * <p>Java class for anonymous complex type.
             *
             * <p>The following schema fragment specifies the expected content contained within this class.
             *
             * <pre>
             * &lt;complexType>
             *   &lt;complexContent>
             *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
             *       &lt;sequence>
             *         &lt;element name="param" type="{}Param" maxOccurs="unbounded" minOccurs="0"/>
             *       &lt;/sequence>
             *     &lt;/restriction>
             *   &lt;/complexContent>
             * &lt;/complexType>
             * </pre>
             *
             *
             */
            @XmlAccessorType(XmlAccessType.FIELD)
            @XmlType(name = "", propOrder = {
                    "param"
            })
            public static class Params {

                protected List<Param> param;

                /**
                 * Gets the value of the param property.
                 *
                 * <p>
                 * This accessor method returns a reference to the live list,
                 * not a snapshot. Therefore any modification you make to the
                 * returned list will be present inside the JAXB object.
                 * This is why there is not a <CODE>set</CODE> method for the param property.
                 *
                 * <p>
                 * For example, to add a new item, do as follows:
                 * <pre>
                 *    getParam().add(newItem);
                 * </pre>
                 *
                 *
                 * <p>
                 * Objects of the following type(s) are allowed in the list
                 * {@link Param }
                 *
                 *
                 */
                public List<Param> getParam() {
                    if (param == null) {
                        param = new ArrayList<Param>();
                    }
                    return this.param;
                }

            }

        }

    }


    /**
     * <p>Java class for anonymous complex type.
     *
     * <p>The following schema fragment specifies the expected content contained within this class.
     *
     * <pre>
     * &lt;complexType>
     *   &lt;complexContent>
     *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *       &lt;sequence>
     *         &lt;element name="CacheItem" maxOccurs="unbounded" minOccurs="0">
     *           &lt;complexType>
     *             &lt;complexContent>
     *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *                 &lt;sequence>
     *                   &lt;element name="cacheName" type="{http://www.w3.org/2001/XMLSchema}string"/>
     *                   &lt;element name="engineName" type="{http://www.w3.org/2001/XMLSchema}string"/>
     *                   &lt;element name="cacheDesc" type="{http://www.w3.org/2001/XMLSchema}string"/>
     *                   &lt;element name="defaultExpireTime" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
     *                   &lt;element name="params" minOccurs="0">
     *                     &lt;complexType>
     *                       &lt;complexContent>
     *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *                           &lt;sequence>
     *                             &lt;element name="param" type="{}Param" maxOccurs="unbounded" minOccurs="0"/>
     *                           &lt;/sequence>
     *                         &lt;/restriction>
     *                       &lt;/complexContent>
     *                     &lt;/complexType>
     *                   &lt;/element>
     *                 &lt;/sequence>
     *               &lt;/restriction>
     *             &lt;/complexContent>
     *           &lt;/complexType>
     *         &lt;/element>
     *       &lt;/sequence>
     *     &lt;/restriction>
     *   &lt;/complexContent>
     * &lt;/complexType>
     * </pre>
     *
     *
     */
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = {
            "cacheItem"
    })
    public static class CacheItems {

        @XmlElement(name = "CacheItem")
        protected List<CacheItem> cacheItem;

        /**
         * Gets the value of the cacheItem property.
         *
         * <p>
         * This accessor method returns a reference to the live list,
         * not a snapshot. Therefore any modification you make to the
         * returned list will be present inside the JAXB object.
         * This is why there is not a <CODE>set</CODE> method for the cacheItem property.
         *
         * <p>
         * For example, to add a new item, do as follows:
         * <pre>
         *    getCacheItem().add(newItem);
         * </pre>
         *
         *
         * <p>
         * Objects of the following type(s) are allowed in the list
         * {@link CacheManagerInfo.CacheItems.CacheItem }
         *
         *
         */
        public List<CacheItem> getCacheItem() {
            if (cacheItem == null) {
                cacheItem = new ArrayList<CacheItem>();
            }
            return this.cacheItem;
        }


        /**
         * <p>Java class for anonymous complex type.
         *
         * <p>The following schema fragment specifies the expected content contained within this class.
         *
         * <pre>
         * &lt;complexType>
         *   &lt;complexContent>
         *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
         *       &lt;sequence>
         *         &lt;element name="cacheName" type="{http://www.w3.org/2001/XMLSchema}string"/>
         *         &lt;element name="engineName" type="{http://www.w3.org/2001/XMLSchema}string"/>
         *         &lt;element name="cacheDesc" type="{http://www.w3.org/2001/XMLSchema}string"/>
         *         &lt;element name="defaultExpireTime" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
         *         &lt;element name="params" minOccurs="0">
         *           &lt;complexType>
         *             &lt;complexContent>
         *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
         *                 &lt;sequence>
         *                   &lt;element name="param" type="{}Param" maxOccurs="unbounded" minOccurs="0"/>
         *                 &lt;/sequence>
         *               &lt;/restriction>
         *             &lt;/complexContent>
         *           &lt;/complexType>
         *         &lt;/element>
         *       &lt;/sequence>
         *     &lt;/restriction>
         *   &lt;/complexContent>
         * &lt;/complexType>
         * </pre>
         *
         *
         */
        @XmlAccessorType(XmlAccessType.FIELD)
        @XmlType(name = "", propOrder = {
                "cacheName",
                "engineName",
                "cacheDesc",
                "defaultExpireTime",
                "params"
        })
        public static class CacheItem {

            @XmlElement(required = true)
            protected String cacheName;
            @XmlElement(required = true)
            protected String engineName;
            @XmlElement(required = true)
            protected String cacheDesc;
            protected String defaultExpireTime;
            protected CacheManagerInfo.CacheItems.CacheItem.Params params;

            /**
             * Gets the value of the cacheName property.
             *
             * @return
             *     possible object is
             *     {@link String }
             *
             */
            public String getCacheName() {
                return cacheName;
            }

            /**
             * Sets the value of the cacheName property.
             *
             * @param value
             *     allowed object is
             *     {@link String }
             *
             */
            public void setCacheName(String value) {
                this.cacheName = value;
            }

            /**
             * Gets the value of the engineName property.
             *
             * @return
             *     possible object is
             *     {@link String }
             *
             */
            public String getEngineName() {
                return engineName;
            }

            /**
             * Sets the value of the engineName property.
             *
             * @param value
             *     allowed object is
             *     {@link String }
             *
             */
            public void setEngineName(String value) {
                this.engineName = value;
            }

            /**
             * Gets the value of the cacheDesc property.
             *
             * @return
             *     possible object is
             *     {@link String }
             *
             */
            public String getCacheDesc() {
                return cacheDesc;
            }

            /**
             * Sets the value of the cacheDesc property.
             *
             * @param value
             *     allowed object is
             *     {@link String }
             *
             */
            public void setCacheDesc(String value) {
                this.cacheDesc = value;
            }

            /**
             * Gets the value of the defaultExpireTime property.
             *
             * @return
             *     possible object is
             *     {@link String }
             *
             */
            public String getDefaultExpireTime() {
                return defaultExpireTime;
            }

            /**
             * Sets the value of the defaultExpireTime property.
             *
             * @param value
             *     allowed object is
             *     {@link String }
             *
             */
            public void setDefaultExpireTime(String value) {
                this.defaultExpireTime = value;
            }

            /**
             * Gets the value of the params property.
             *
             * @return
             *     possible object is
             *     {@link CacheManagerInfo.CacheItems.CacheItem.Params }
             *
             */
            public CacheManagerInfo.CacheItems.CacheItem.Params getParams() {
                return params;
            }

            /**
             * Sets the value of the params property.
             *
             * @param value
             *     allowed object is
             *     {@link CacheManagerInfo.CacheItems.CacheItem.Params }
             *
             */
            public void setParams(CacheManagerInfo.CacheItems.CacheItem.Params value) {
                this.params = value;
            }


            /**
             * <p>Java class for anonymous complex type.
             *
             * <p>The following schema fragment specifies the expected content contained within this class.
             *
             * <pre>
             * &lt;complexType>
             *   &lt;complexContent>
             *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
             *       &lt;sequence>
             *         &lt;element name="param" type="{}Param" maxOccurs="unbounded" minOccurs="0"/>
             *       &lt;/sequence>
             *     &lt;/restriction>
             *   &lt;/complexContent>
             * &lt;/complexType>
             * </pre>
             *
             *
             */
            @XmlAccessorType(XmlAccessType.FIELD)
            @XmlType(name = "", propOrder = {
                    "param"
            })
            public static class Params {

                protected List<Param> param;

                /**
                 * Gets the value of the param property.
                 *
                 * <p>
                 * This accessor method returns a reference to the live list,
                 * not a snapshot. Therefore any modification you make to the
                 * returned list will be present inside the JAXB object.
                 * This is why there is not a <CODE>set</CODE> method for the param property.
                 *
                 * <p>
                 * For example, to add a new item, do as follows:
                 * <pre>
                 *    getParam().add(newItem);
                 * </pre>
                 *
                 *
                 * <p>
                 * Objects of the following type(s) are allowed in the list
                 * {@link Param }
                 *
                 *
                 */
                public List<Param> getParam() {
                    if (param == null) {
                        param = new ArrayList<Param>();
                    }
                    return this.param;
                }

            }

        }

    }


    /**
     * <p>Java class for anonymous complex type.
     *
     * <p>The following schema fragment specifies the expected content contained within this class.
     *
     * <pre>
     * &lt;complexType>
     *   &lt;complexContent>
     *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *       &lt;sequence>
     *         &lt;element name="CacheListener" maxOccurs="unbounded" minOccurs="0">
     *           &lt;complexType>
     *             &lt;complexContent>
     *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *                 &lt;sequence>
     *                   &lt;element name="listenerName" type="{http://www.w3.org/2001/XMLSchema}string"/>
     *                   &lt;element name="implClass" type="{http://www.w3.org/2001/XMLSchema}string"/>
     *                   &lt;element name="listenerDesc" type="{http://www.w3.org/2001/XMLSchema}string"/>
     *                   &lt;element name="params" minOccurs="0">
     *                     &lt;complexType>
     *                       &lt;complexContent>
     *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *                           &lt;sequence>
     *                             &lt;element name="param" type="{}Param" maxOccurs="unbounded" minOccurs="0"/>
     *                           &lt;/sequence>
     *                         &lt;/restriction>
     *                       &lt;/complexContent>
     *                     &lt;/complexType>
     *                   &lt;/element>
     *                 &lt;/sequence>
     *               &lt;/restriction>
     *             &lt;/complexContent>
     *           &lt;/complexType>
     *         &lt;/element>
     *       &lt;/sequence>
     *     &lt;/restriction>
     *   &lt;/complexContent>
     * &lt;/complexType>
     * </pre>
     *
     *
     */
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = {
            "cacheListener"
    })
    public static class CacheListeners {

        @XmlElement(name = "CacheListener")
        protected List<CacheListener> cacheListener;

        /**
         * Gets the value of the cacheListener property.
         *
         * <p>
         * This accessor method returns a reference to the live list,
         * not a snapshot. Therefore any modification you make to the
         * returned list will be present inside the JAXB object.
         * This is why there is not a <CODE>set</CODE> method for the cacheListener property.
         *
         * <p>
         * For example, to add a new item, do as follows:
         * <pre>
         *    getCacheListener().add(newItem);
         * </pre>
         *
         *
         * <p>
         * Objects of the following type(s) are allowed in the list
         * {@link CacheManagerInfo.CacheListeners.CacheListener }
         *
         *
         */
        public List<CacheListener> getCacheListener() {
            if (cacheListener == null) {
                cacheListener = new ArrayList<CacheListener>();
            }
            return this.cacheListener;
        }


        /**
         * <p>Java class for anonymous complex type.
         *
         * <p>The following schema fragment specifies the expected content contained within this class.
         *
         * <pre>
         * &lt;complexType>
         *   &lt;complexContent>
         *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
         *       &lt;sequence>
         *         &lt;element name="listenerName" type="{http://www.w3.org/2001/XMLSchema}string"/>
         *         &lt;element name="implClass" type="{http://www.w3.org/2001/XMLSchema}string"/>
         *         &lt;element name="listenerDesc" type="{http://www.w3.org/2001/XMLSchema}string"/>
         *         &lt;element name="params" minOccurs="0">
         *           &lt;complexType>
         *             &lt;complexContent>
         *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
         *                 &lt;sequence>
         *                   &lt;element name="param" type="{}Param" maxOccurs="unbounded" minOccurs="0"/>
         *                 &lt;/sequence>
         *               &lt;/restriction>
         *             &lt;/complexContent>
         *           &lt;/complexType>
         *         &lt;/element>
         *       &lt;/sequence>
         *     &lt;/restriction>
         *   &lt;/complexContent>
         * &lt;/complexType>
         * </pre>
         *
         *
         */
        @XmlAccessorType(XmlAccessType.FIELD)
        @XmlType(name = "", propOrder = {
                "listenerName",
                "implClass",
                "listenerDesc",
                "params"
        })
        public static class CacheListener {

            @XmlElement(required = true)
            protected String listenerName;
            @XmlElement(required = true)
            protected String implClass;
            @XmlElement(required = true)
            protected String listenerDesc;
            protected CacheManagerInfo.CacheListeners.CacheListener.Params params;

            /**
             * Gets the value of the listenerName property.
             *
             * @return
             *     possible object is
             *     {@link String }
             *
             */
            public String getListenerName() {
                return listenerName;
            }

            /**
             * Sets the value of the listenerName property.
             *
             * @param value
             *     allowed object is
             *     {@link String }
             *
             */
            public void setListenerName(String value) {
                this.listenerName = value;
            }

            /**
             * Gets the value of the implClass property.
             *
             * @return
             *     possible object is
             *     {@link String }
             *
             */
            public String getImplClass() {
                return implClass;
            }

            /**
             * Sets the value of the implClass property.
             *
             * @param value
             *     allowed object is
             *     {@link String }
             *
             */
            public void setImplClass(String value) {
                this.implClass = value;
            }

            /**
             * Gets the value of the listenerDesc property.
             *
             * @return
             *     possible object is
             *     {@link String }
             *
             */
            public String getListenerDesc() {
                return listenerDesc;
            }

            /**
             * Sets the value of the listenerDesc property.
             *
             * @param value
             *     allowed object is
             *     {@link String }
             *
             */
            public void setListenerDesc(String value) {
                this.listenerDesc = value;
            }

            /**
             * Gets the value of the params property.
             *
             * @return
             *     possible object is
             *     {@link CacheManagerInfo.CacheListeners.CacheListener.Params }
             *
             */
            public CacheManagerInfo.CacheListeners.CacheListener.Params getParams() {
                return params;
            }

            /**
             * Sets the value of the params property.
             *
             * @param value
             *     allowed object is
             *     {@link CacheManagerInfo.CacheListeners.CacheListener.Params }
             *
             */
            public void setParams(CacheManagerInfo.CacheListeners.CacheListener.Params value) {
                this.params = value;
            }


            /**
             * <p>Java class for anonymous complex type.
             *
             * <p>The following schema fragment specifies the expected content contained within this class.
             *
             * <pre>
             * &lt;complexType>
             *   &lt;complexContent>
             *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
             *       &lt;sequence>
             *         &lt;element name="param" type="{}Param" maxOccurs="unbounded" minOccurs="0"/>
             *       &lt;/sequence>
             *     &lt;/restriction>
             *   &lt;/complexContent>
             * &lt;/complexType>
             * </pre>
             *
             *
             */
            @XmlAccessorType(XmlAccessType.FIELD)
            @XmlType(name = "", propOrder = {
                    "param"
            })
            public static class Params {

                protected List<Param> param;

                /**
                 * Gets the value of the param property.
                 *
                 * <p>
                 * This accessor method returns a reference to the live list,
                 * not a snapshot. Therefore any modification you make to the
                 * returned list will be present inside the JAXB object.
                 * This is why there is not a <CODE>set</CODE> method for the param property.
                 *
                 * <p>
                 * For example, to add a new item, do as follows:
                 * <pre>
                 *    getParam().add(newItem);
                 * </pre>
                 *
                 *
                 * <p>
                 * Objects of the following type(s) are allowed in the list
                 * {@link Param }
                 *
                 *
                 */
                public List<Param> getParam() {
                    if (param == null) {
                        param = new ArrayList<Param>();
                    }
                    return this.param;
                }

            }

        }

    }

}
