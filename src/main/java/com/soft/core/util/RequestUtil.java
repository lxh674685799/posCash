package com.soft.core.util;

import java.util.Enumeration;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RequestUtil {
	private static ThreadLocal<HttpServletRequest> reqLocal = new ThreadLocal();

	private static ThreadLocal<HttpServletResponse> responseLocal = new ThreadLocal();

	private static Logger logger = LoggerFactory.getLogger("");

	public static void setHttpServletRequest(HttpServletRequest request) {
		reqLocal.set(request);
	}

	public static void clearHttpReqResponse() {
		reqLocal.remove();
		responseLocal.remove();
	}

	public static void setHttpServletResponse(HttpServletResponse response) {
		responseLocal.set(response);
	}

	public static HttpServletRequest getHttpServletRequest() {
		return (HttpServletRequest) reqLocal.get();
	}

	public static HttpServletResponse getHttpServletResponse() {
		return (HttpServletResponse) responseLocal.get();
	}

	public static String getString(HttpServletRequest request, String key,
			String defaultValue) {
		String value = request.getParameter(key);
		if (StringUtil.isEmpty(value))
			return defaultValue;
		return value.replace("'", "''").trim();
	}

	public static String getString(HttpServletRequest request, String key) {
		return getString(request, key, "");
	}

	public static String getSecureString(HttpServletRequest request,
			String key, String defaultValue) {
		String value = request.getParameter(key);
		if (StringUtil.isEmpty(value))
			return defaultValue;
		return filterInject(value);
	}

	public static String getSecureString(HttpServletRequest request, String key) {
		return getSecureString(request, key, "");
	}

	public static String filterInject(String str) {
		String injectstr = "\\||;|exec|insert|select|delete|update|count|chr|truncate|char";
		Pattern regex = Pattern.compile(injectstr, 226);

		Matcher matcher = regex.matcher(str);
		str = matcher.replaceAll("");
		str = str.replace("'", "''");
		str = str.replace("-", "—");
		str = str.replace("(", "（");
		str = str.replace(")", "）");
		str = str.replace("%", "％");

		return str;
	}

	public static String getLowercaseString(HttpServletRequest request,
			String key) {
		return getString(request, key).toLowerCase();
	}

	public static int getInt(HttpServletRequest request, String key) {
		return getInt(request, key, 0);
	}

	public static int getInt(HttpServletRequest request, String key,
			int defaultValue) {
		String str = request.getParameter(key);
		if (StringUtil.isEmpty(str))
			return defaultValue;
		return Integer.parseInt(str);
	}

	public static long getLong(HttpServletRequest request, String key) {
		return getLong(request, key, 0L);
	}


	public static Long[] getLongAryByStr(HttpServletRequest request, String key) {
		String sysUserId = request.getParameter(key);
		String[] aryId = sysUserId.split(",");
		Long[] lAryId = new Long[aryId.length];
		for (int i = 0; i < aryId.length; ++i) {
			lAryId[i] = Long.valueOf(Long.parseLong(aryId[i]));
		}
		return lAryId;
	}

	public static String[] getStringAryByStr(HttpServletRequest request,
			String key) {
		String sysUserId = request.getParameter(key);
		String[] aryId = sysUserId.split(",");
		String[] lAryId = new String[aryId.length];
		for (int i = 0; i < aryId.length; ++i) {
			lAryId[i] = aryId[i];
		}
		return lAryId;
	}

	public static Integer[] getIntAry(HttpServletRequest request, String key) {
		String[] aryKey = request.getParameterValues(key);
		Integer[] aryInt = new Integer[aryKey.length];
		for (int i = 0; i < aryKey.length; ++i) {
			aryInt[i] = Integer.valueOf(Integer.parseInt(aryKey[i]));
		}
		return aryInt;
	}

	public static Float[] getFloatAry(HttpServletRequest request, String key) {
		String[] aryKey = request.getParameterValues(key);
		Float[] fAryId = new Float[aryKey.length];
		for (int i = 0; i < aryKey.length; ++i) {
			fAryId[i] = Float.valueOf(Float.parseFloat(aryKey[i]));
		}
		return fAryId;
	}

	public static long getLong(HttpServletRequest request, String key,
			long defaultValue) {
		String str = request.getParameter(key);
		if (StringUtil.isEmpty(str))
			return defaultValue;
		return Long.parseLong(str);
	}

	public static float getFloat(HttpServletRequest request, String key) {
		return getFloat(request, key, 0.0F);
	}

	public static float getFloat(HttpServletRequest request, String key,
			float defaultValue) {
		String str = request.getParameter(key);
		if (StringUtil.isEmpty(str))
			return defaultValue;
		return Float.parseFloat(request.getParameter(key));
	}

	public static boolean getBoolean(HttpServletRequest request, String key) {
		return getBoolean(request, key, false);
	}

	public static boolean getBoolean(HttpServletRequest request, String key,
			boolean defaultValue) {
		String str = request.getParameter(key);
		if (StringUtil.isEmpty(str))
			return defaultValue;
		if (StringUtils.isNumeric(str))
			return Integer.parseInt(str) == 1;
		return Boolean.parseBoolean(str);
	}

	public static Short getShort(HttpServletRequest request, String key) {
		return getShort(request, key, Short.valueOf((short) 0));
	}

	public static Short getShort(HttpServletRequest request, String key,
			Short defaultValue) {
		String str = request.getParameter(key);
		if (StringUtil.isEmpty(str))
			return defaultValue;
		return Short.valueOf(Short.parseShort(str));
	}

	public static String getUrl(HttpServletRequest request) {
		StringBuffer urlThisPage = new StringBuffer();
		urlThisPage.append(request.getRequestURI());
		Enumeration e = request.getParameterNames();
		String para = "";
		String values = "";
		urlThisPage.append("?");
		while (e.hasMoreElements()) {
			para = (String) e.nextElement();
			values = request.getParameter(para);
			urlThisPage.append(para);
			urlThisPage.append("=");
			urlThisPage.append(values);
			urlThisPage.append("&");
		}
		return urlThisPage.substring(0, urlThisPage.length() - 1);
	}

	public static String getPrePage(HttpServletRequest request) {
		return request.getHeader("Referer");
	}

	private static String getByAry(String[] aryTmp, boolean isSecure) {
		String rtn = "";
		for (int i = 0; i < aryTmp.length; ++i) {
			String str = aryTmp[i].trim();
			if (!str.equals("")) {
				if (isSecure) {
					str = filterInject(str);
					if (!str.equals(""))
						rtn = new StringBuilder().append(rtn).append(str)
								.append(",").toString();
				} else {
					rtn = new StringBuilder().append(rtn).append(str)
							.append(",").toString();
				}
			}
		}
		if (rtn.length() > 0)
			rtn = rtn.substring(0, rtn.length() - 1);
		return rtn;
	}

	public static Locale getLocal(HttpServletRequest request) {
		Locale local = request.getLocale();
		if (local == null)
			local = Locale.CHINA;
		return local;
	}

	public static final String getErrorUrl(HttpServletRequest request) {
		String errorUrl = (String) request
				.getAttribute("javax.servlet.error.request_uri");

		if (errorUrl == null) {
			errorUrl = (String) request
					.getAttribute("javax.servlet.forward.request_uri");
		}

		if (errorUrl == null) {
			errorUrl = (String) request
					.getAttribute("javax.servlet.include.request_uri");
		}

		if (errorUrl == null) {
			errorUrl = request.getRequestURL().toString();
		}
		return errorUrl;
	}

	public static final StringBuilder getErrorInfoFromRequest(
			HttpServletRequest request, boolean isInfoEnabled) {
		StringBuilder sb = new StringBuilder();
		String errorUrl = getErrorUrl(request);
		sb.append(StringUtil.formatMsg(
				"Error processing url: %1, Referrer: %2, Error message: %3.\n",
				new Object[] { errorUrl, request.getHeader("REFERER"),
						request.getAttribute("javax.servlet.error.message") }));

		Throwable ex = (Throwable) request.getAttribute("exception");
		if (ex == null) {
			ex = (Throwable) request
					.getAttribute("javax.servlet.error.exception");
		}

		if (ex != null) {
			sb.append(StringUtil.formatMsg("Exception stack trace: \n",
					new Object[] { ex }));
		}
		return sb;
	}

	public static final StringBuilder getRequestInfo(HttpServletRequest request) {
		StringBuilder sb = new StringBuilder();
		sb.append("--------------Debuging request.getParameterNames()---------\n");
		Enumeration enumeration = request.getParameterNames();
		while (enumeration.hasMoreElements()) {
			String paramName = (String) enumeration.nextElement();
			sb.append(StringUtil
					.formatMsg("Request Parameter - %1 = %2.\n", new Object[] {
							paramName, request.getParameter(paramName) }));
		}

		sb.append("-----------Debuging request.getAttributeNames()---------\n");
		enumeration = request.getAttributeNames();
		while (enumeration.hasMoreElements()) {
			String attrName = (String) enumeration.nextElement();
			if (!attrName.endsWith("exception")) {
				sb.append(StringUtil.formatMsg(
						"Request Attribute - %1 = %2.\n", new Object[] {
								attrName, request.getAttribute(attrName) }));
			}

		}

		sb.append("-----------Debuging request.getHeaderNames()---------------\n");
		enumeration = request.getHeaderNames();
		while (enumeration.hasMoreElements()) {
			String headerName = (String) enumeration.nextElement();
			sb.append(StringUtil.formatMsg("Request Header - %1 = %2.\n",
					new Object[] { headerName, request.getHeader(headerName) }));
		}

		return sb;
	}
}
