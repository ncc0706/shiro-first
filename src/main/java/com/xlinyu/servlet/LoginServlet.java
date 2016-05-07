package com.xlinyu.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
       
    public LoginServlet() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.getRequestDispatcher("/WEB-INF/views/login.jsp").forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		System.out.println("username: " + username);
		System.out.println("password: " + password);
		Subject subject = SecurityUtils.getSubject();
		
		UsernamePasswordToken token = new UsernamePasswordToken(username, password);
		String message = null;
		try {
			subject.login(token);
		} catch (UnknownAccountException e) {
			message = "用户账号不存在";
		} catch (IncorrectCredentialsException e){
			message = "用户密码错误";
		} catch (AuthenticationException e){
			message = "其他异常" + e.getMessage();
		}
		
		if(message != null){
			request.setAttribute("message", message);
			request.getRequestDispatcher("/WEB-INF/views/login.jsp").forward(request, response);
		}else{
			response.sendRedirect(request.getContextPath()+"/");
		}
	}

}
