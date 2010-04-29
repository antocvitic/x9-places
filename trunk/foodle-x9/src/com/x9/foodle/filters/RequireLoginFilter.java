package com.x9.foodle.filters;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.x9.foodle.user.UserModel;
import com.x9.foodle.user.UserUtils;
import com.x9.foodle.util.QuickURLEncoder;

public class RequireLoginFilter implements Filter {

	@Override
	public void destroy() {
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse resp = (HttpServletResponse) response;
		UserModel user = UserUtils.getCurrentUser(req, resp);

		if (user == null) {
			resp.sendRedirect(req.getContextPath() + "/login.jsp?redirect="
					+ QuickURLEncoder.encode(req.getRequestURL()+"?"+req.getQueryString()));
			return;
		}

		chain.doFilter(request, response);
	}

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
	}

}
