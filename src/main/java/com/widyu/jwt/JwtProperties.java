package com.widyu.jwt;

import java.util.HashMap;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@ConfigurationProperties("jwt")
@Component
public class JwtProperties {

	public static HashMap<String, Object> tokenHashMap = new HashMap<>();

	@Value("${security.jwt.token.secretkey}")
	private String secretKey;

	@Value("${validity}")
	private long validity;

	@Value("${validateJwt}")
	private String validateJwt;

	@Value("{server.servlet.context-path}")
	private String contextPath;

	public String getSecretKey() {
		return secretKey;
	}

	public long getValidity() {
		return validity;
	}

	public String getValidateJwt() {
		return validateJwt;
	}

	public String getContextPath() {
		return contextPath;
	}

}
