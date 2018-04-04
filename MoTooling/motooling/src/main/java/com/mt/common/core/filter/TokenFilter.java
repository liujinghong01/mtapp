package com.mt.common.core.filter;

import com.mt.common.core.interceptor.TokenHttpServletRequestWrapper;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * @Author: Alnwick
 * @Date: create in 2018-01-25   15:14
 * @Description:
 * @Modified By:
 */
public class TokenFilter implements Filter {

    private static ServletRequest requestWrapper = null;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        // 重新包装request
        requestWrapper = new TokenHttpServletRequestWrapper((HttpServletRequest) request);
        if (null == requestWrapper) {
            chain.doFilter(request, response);
        } else {
            // 放行 包装后requestWrapper
            chain.doFilter(requestWrapper, response);
        }
    }

    @Override
    public void destroy() {

    }
}
