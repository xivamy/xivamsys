package com.xiva.test;

import java.io.Serializable;

import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Element;

public class CacheDemo
{

    static CacheManager manager = new CacheManager();

    public static void main(String[] args) throws InterruptedException
    {

        String[] cacheNames = manager.getCacheNames();
        System.out.println("读取的缓存列表为：");
        for (int i = 0; i < cacheNames.length; i++)
        {
            System.out.println("-- " + (i + 1) + " " + cacheNames[i]);
        }

        Cache cache = manager.getCache("cache1");
        Element element = new Element("key1", "value1");
        cache.put(element);

        element = cache.get("key1");
        Serializable value = element.getValue();
        System.out.println("序列化后的值为：" + value.toString());

        element = cache.get("key1");
        Object value1 = element.getObjectValue();
        System.out.println("未序列化的值为：" + value1.toString());

        int elementsInMemory = cache.getSize();
        System.out.println("得到缓存的对象数量：" + elementsInMemory);

        long elementsInMemory1 = cache.getMemoryStoreSize();
        System.out.println("得到缓存对象占用内存的数量：" + elementsInMemory1);

        long elementsInMemory2 = cache.getDiskStoreSize();
        System.out.println("得到缓存对对象占用磁盘的数量：" + elementsInMemory2);

//        int hits = cache.getHitCount();
//        System.out.println("得到缓存读取的命中次数：" + hits);
//
//        int hits1 = cache.getMemoryStoreHitCount();
//        System.out.println("得到内存中缓存读取的命中次数：" + hits1);
//
//        int hits2 = cache.getDiskStoreHitCount();
//        System.out.println("得到磁盘中缓存读取的命中次数：" + hits2);
//
//        int hits3 = cache.getMissCountNotFound();
//        System.out.println("得到缓存读取的丢失次数：" + hits3);
//
//        int hits4 = cache.getMissCountExpired();
//        System.out.println("得到缓存读取的已经被销毁的对象丢失次数：" + hits4);
    }

}
