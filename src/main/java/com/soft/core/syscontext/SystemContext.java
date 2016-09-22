package com.soft.core.syscontext;

import java.net.MalformedURLException;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.soft.laboratory.model.user.SysUser;

public class SystemContext {
	
	private static ServletContext servletContext;
	
	/** 
	 * 通过　HttpServletRequest对象得到当前用户信息
	 * @param request
	 * @return　用户对象
	 */
	public static SysUser getCurrentUser(HttpServletRequest request) {
		return (SysUser) request.getSession().getAttribute(
				Const.USER_SESSION_ID);
	}
	/**
	 * 通过HttpSession对象得到当前用户信息
	 * @param session
	 * @return　用户对象
	 */
	public static SysUser getCurrentUser(HttpSession session) {
		return (SysUser) session.getAttribute(
				Const.USER_SESSION_ID);
	}
	/**
	 * 清除用户登录信息
	 * @param session
	 */
	public static void clearCurrentUser(HttpSession session) {
		session.removeAttribute(Const.USER_SESSION_ID);
	}
	/**
	 * 通过HttpServletRequest对象设置当前用户信息
	 * @param request
	 * @param user
	 */
	public static void setCurrentUser(HttpServletRequest request,SysUser user) {
		request.getSession().setAttribute(Const.USER_SESSION_ID, user);
	}
	public static void setCurrentUser(HttpSession session,SysUser user) {
		session.setAttribute(Const.USER_SESSION_ID, user);
	}

	public static ServletContext getServletContext() {
		return servletContext;
	}
	public static void setServletContext(ServletContext servletContext) {
		SystemContext.servletContext = servletContext;
	}
	
	 /**
     * 用于取代ServletContext.getRealPath()，当项目运行在weblogic服务器上时此方法返回null。
     * 原因：weblogic是以war包的形式发布的，并没有realPath
     * @return
     */
    public static String getRealPath(){
        String path;
        try {
            if((path=servletContext.getRealPath("/"))==null){
                path=servletContext.getResource("/").getPath().substring(1);  //去掉第一个字符-"/"
            }
            return path;
        } catch (MalformedURLException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
        return "";
    }

}
