package com.emailsvc.backend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;

@SpringBootApplication
public class BackendApplication implements WebMvcConfigurer {

	static RestTemplate restTemplate = new RestTemplate();

	private static void useExchangeMethodsRestTemplate(){
		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.setContentType(MediaType.APPLICATION_JSON);
		httpHeaders.set("X-API-KEY", "value");

		HttpEntity<Object> requestEntity = new HttpEntity<>(httpHeaders);
		String url = null;
		ResponseEntity<String> responseEntity = restTemplate.exchange(url,
				HttpMethod.GET, requestEntity, String.class);
	}

	private final LocaleChangeInterceptor localeChangeInterceptor;
	public BackendApplication(LocaleChangeInterceptor localeChangeInterceptor){
		this.localeChangeInterceptor = localeChangeInterceptor;
	}

	@Override
	public void addInterceptors(InterceptorRegistry interceptorRegistry){
		interceptorRegistry.addInterceptor(localeChangeInterceptor);
	}

	public static void main(String[] args) {
		SpringApplication.run(BackendApplication.class, args);
	}

}
