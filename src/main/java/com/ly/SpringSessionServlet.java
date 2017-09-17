package com.ly;

import java.io.IOException;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.session.data.redis.config.annotation.web.http.RedisHttpSessionConfiguration;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;

@WebServlet(urlPatterns = "/hello")
public class SpringSessionServlet extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
	
	private List<String> asList;

	@Override
	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		RedisHttpSessionConfiguration s;
		WebApplicationContext currentWebApplicationContext = ContextLoader.getCurrentWebApplicationContext();
		String[] beanDefinitionNames = currentWebApplicationContext.getBeanDefinitionNames();
		for(String name:beanDefinitionNames) {
			resp.getWriter().println(name+"--->"+currentWebApplicationContext.getBean(name).getClass().getName());
		}
		
		Cookie[] cookies = req.getCookies();
		String cookieSessionID = null;
		if (cookies != null) {
			for (Cookie cookie : cookies) {
				if ("JSESSIONID".equals(cookie.getName())) {
					cookieSessionID = cookie.getValue();
				}
			}
		}
		HttpSession session = req.getSession(false);
		resp.getWriter().write("ip: " + getIp() + " ,reqCookie:" + cookieSessionID + ",session=" + session);
		req.getSession();
		
	}

	public static String getIp() {
		try {
			Enumeration<NetworkInterface> interfaces = NetworkInterface.getNetworkInterfaces();
			while (interfaces.hasMoreElements()) {
				NetworkInterface iface = interfaces.nextElement();
				if (iface.isLoopback() || !iface.isUp() || iface.isVirtual() || iface.isPointToPoint())
					continue;

				Enumeration<InetAddress> addresses = iface.getInetAddresses();
				while (addresses.hasMoreElements()) {
					InetAddress addr = addresses.nextElement();

					final String ip = addr.getHostAddress();
					if (Inet4Address.class == addr.getClass())
						return ip;
				}
			}
		} catch (SocketException e) {
			throw new RuntimeException(e);
		}
		return null;
	}

}
