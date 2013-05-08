package com.xiva.common;

import javax.servlet.http.HttpServletRequest;

import com.opensymphony.oscache.base.Cache;
import com.opensymphony.oscache.web.ServletCacheAdministrator;
import com.opensymphony.oscache.web.filter.ICacheKeyProvider;

public class IvCacheKeyProvider implements ICacheKeyProvider
{

    @Override
    public String createCacheKey(HttpServletRequest httpRequest, ServletCacheAdministrator scAdmin, Cache cache)
    {
        // buffer for the cache key
        StringBuffer buffer = new StringBuffer(100);

        buffer.append(httpRequest.getRequestURI());

        buffer.append('_');

        buffer.append(httpRequest.getParameter("start"));

        buffer.append('_');

        buffer.append(httpRequest.getParameter("limit"));

        buffer.append('_');

        buffer.append(httpRequest.getParameter("orgId"));

        return buffer.toString();
    }

}
