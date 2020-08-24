package com.widyu.jwt;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.tomcat.util.net.openssl.ciphers.Authentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.GenericFilterBean;

import com.widyu.entities.User;
import com.widyu.repository.UserRepository;

import io.jsonwebtoken.Jwts;

@Component
public class JwtFilter extends GenericFilterBean {

	@Autowired
	JwtProperties jwtProperties;

	@Autowired
	UserRepository userRepository;

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse res = (HttpServletResponse) response;
		String message = "true";
		message = this.jwtProperties.getValidateJwt();
		try {
			if (!"true".equals(message)) {
				res.sendError(res.SC_UNAUTHORIZED, message);
			} else {
				User user = (User) JwtProperties.tokenHashMap.get(req.getHeader("Authorization"));
				if (user != null) {
					req.getRequestDispatcher(req.getRequestURL()
							.substring(req.getRequestURL().lastIndexOf(this.jwtProperties.getContextPath())
									+ this.jwtProperties.getContextPath().length()));
				} else {
					res.setStatus(HttpStatus.FORBIDDEN.value());
					message = "not a valid jwt token";
					res.sendRedirect(this.jwtProperties.getContextPath() + "nojwt/jwtexpire?message" + message);
				}
			}
		} catch (Exception e) {

		}

	}
	
	public String getUserName(String token) {
		return Jwts.parser().setSigningKey(this.jwtProperties.getSecretKey()).parseClaimsJws(token).getBody().getSubject();
	}
	
	Authentication getAuthenticate(String token) {
		String username = this.getUserName(token);
		return doAuthentication(username);
}

	Authentication doAuthentication(String username) {
		
		return null;
	}
}
