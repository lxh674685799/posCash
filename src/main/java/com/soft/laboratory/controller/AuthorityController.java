package com.soft.laboratory.controller;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Random;

import javax.annotation.Resource;
import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.soft.core.controller.GenericController;
import com.soft.core.syscontext.SystemContext;
import com.soft.laboratory.model.user.SysUser;
import com.soft.laboratory.service.user.SysUserService;

@Controller
@RequestMapping({ "/system/authority" })
public class AuthorityController extends GenericController{
	
	@Resource
	private SysUserService userService;
	
   
    // 用户登录
	@RequestMapping(value = "login")
    public ModelAndView login(HttpServletRequest request,SysUser user) {
    	 ModelAndView view = new ModelAndView();
    	 view.setViewName("/login.jsp");
    	// String passWord = Base64Demo.getBASE64(user.getPassWord());
 		// user.setPassWord(passWord);
    	 SysUser loginUser = userService.login(user);
         String securityCode = request.getParameter("securityCode");
         //session保存的校验码
         String securityCodeT = request.getSession().getAttribute("securityCode").toString();
         if (securityCodeT.equals(securityCode)) {
             securityCodeT = request.getSession().getAttribute("securityCode").toString();
         } else {	 
        	 view.addObject("error", "验证码错误,请重试!");
             return view;
         }
         if(loginUser !=null){
        	 SystemContext.setServletContext(request.getSession().getServletContext());
        	 SystemContext.setCurrentUser(request,loginUser);
             view.setViewName("redirect:/resource/resource/menu.do");
    		return view;      
         }
         view.addObject("error", "用户名，密码错误!");
         return view;
    }

    // 退出
    @RequestMapping(value = "logout")
    public String logout(HttpSession session) {  
    	SystemContext.clearCurrentUser(session);
        return "/login.jsp";
    }

    // 修改密码
    @RequestMapping(value = "changePwd")
    public String changePwd(HttpServletRequest request) {
		return null;
       
    }

    private Color getRandColor(int fc, int bc) {//给定范围获得随机颜色
        Random random = new Random();
        if (fc > 255) fc = 255;
        if (bc > 255) bc = 255;
        int r = fc + random.nextInt(bc - fc);
        int g = fc + random.nextInt(bc - fc);
        int b = fc + random.nextInt(bc - fc);
        return new Color(r, g, b);
    }

    /**
     * 生成校验码图片
     *
     * @param request
     * @param response
     * @throws IOException
     */
    @RequestMapping("/securityCode")
    public void securityCode(HttpServletRequest request, HttpServletResponse response) throws IOException {
        //设置页面不缓存
        response.setHeader("Pragma", "No-cache");
        response.setHeader("Cache-Control", "no-cache");
        response.setDateHeader("Expires", 0);

        // 在内存中创建图象
        int width = 60, height = 20;
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

        // 获取图形上下文
        Graphics g = image.getGraphics();

        //生成随机类
        Random random = new Random();

        // 设定背景色
        g.setColor(getRandColor(200, 250));
        g.fillRect(0, 0, width, height);

        //设定字体
        g.setFont(new Font("Times New Roman", Font.PLAIN, 18));

        // 随机产生155条干扰线，使图象中的认证码不易被其它程序探测到
        g.setColor(getRandColor(160, 200));
        for (int i = 0; i < 155; i++) {
            int x = random.nextInt(width);
            int y = random.nextInt(height);
            int xl = random.nextInt(12);
            int yl = random.nextInt(12);
            g.drawLine(x, y, x + xl, y + yl);
        }

        // 取随机产生的认证码(4位数字)
        String sRand = "";
        for (int i = 0; i < 4; i++) {
            String rand = String.valueOf(random.nextInt(10));
            sRand += rand;
            // 将认证码显示到图象中
            g.setColor(new Color(20 + random.nextInt(110), 20 + random.nextInt(110), 20 + random.nextInt(110)));
            //调用函数出来的颜色相同，可能是因为种子太接近，所以只能直接生成
            g.drawString(rand, 13 * i + 6, 16);
        }

        HttpSession session = request.getSession();
        // 将认证码存入SESSION
        session.setAttribute("securityCode", sRand);

        // 图象生效
        g.dispose();

        // 输出图象到页面
        ImageIO.write(image, "JPEG", response.getOutputStream());
    }
}
