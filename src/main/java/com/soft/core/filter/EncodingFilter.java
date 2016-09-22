package com.soft.core.filter;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;

public class EncodingFilter implements Filter {
	private String encoding;
	private String contentType;

	public EncodingFilter() {
		this.encoding = "UTF-8";
		this.contentType = "text/html;charset=UTF-8";
	}

	public void destroy() {
	}

	public void doFilter(ServletRequest request, ServletResponse httpresponse,
			FilterChain chain) throws IOException, ServletException {
		HttpServletResponse response = (HttpServletResponse) httpresponse;
		request.setCharacterEncoding(this.encoding);
		response.setCharacterEncoding(this.encoding);
		response.setContentType(this.contentType);

		response.setHeader("Cache-Control", "no-cache");
		response.setHeader("Pragma", "no-cache");
		response.setDateHeader("Expires", -1L);

		chain.doFilter(request, response);
	}

	public void init(FilterConfig config) throws ServletException {
		String _encoding = config.getInitParameter("encoding");
		String _contentType = config.getInitParameter("contentType");

		if (_encoding != null)
			this.encoding = _encoding;
		if (_contentType != null)
			this.contentType = _contentType;
	}
}
