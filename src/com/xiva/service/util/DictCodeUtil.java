package com.xiva.service.util;

import java.util.List;

import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Element;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.xiva.common.CommonConstant;
import com.xiva.common.util.ServerLocator;
import com.xiva.dao.dictcode.IvDictCodeDao;
import com.xiva.domain.IvDictCode;

public class DictCodeUtil
{

    private static final Log log = LogFactory.getFactory().getInstance(DictCodeUtil.class);

    private static CacheManager manager = new CacheManager();

    @SuppressWarnings("unchecked")
    public static List<IvDictCode> getUserStatusDict(String key)
    {
        List<IvDictCode> codeList = null;
        Cache dictCache = manager.getCache("DictCodeCache");
        Element element = dictCache.get(key);
        if (element == null)
        {
            try
            {
                IvDictCodeDao codeDao = (IvDictCodeDao) ServerLocator.getService("ivDictCodeDao");
                codeList = codeDao.findDictCodeByKey(key);
                element = new Element(key, codeList);
                dictCache.put(element);
            }
            catch (Exception e)
            {
                log.error(e);
            }
        }
        else
        {
            codeList = (List<IvDictCode>) element.getValue();
        }

        return codeList;
    }

    public static String getDictDes(String key)
    {
        List<IvDictCode> codeList = getUserStatusDict(CommonConstant.CODE_KEY_USER_STATUS);
        String codeDes = "";

        for (IvDictCode dictCode : codeList)
        {
            if (dictCode.getStatuskey().equals(key))
            {
                codeDes = dictCode.getStatusdes();
                break;
            }
        }
        return codeDes;
    }

}
