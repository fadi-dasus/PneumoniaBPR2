
package com.bachelor.config;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.filter.ShallowEtagHeaderFilter;

@Configuration
public class EtagConfig {

	@Bean
	public FilterRegistrationBean<ShallowEtagHeaderFilter> shallowEtagHeaderFilter() {
		FilterRegistrationBean<ShallowEtagHeaderFilter> filterRegistrationBean = new FilterRegistrationBean<>(
				new ShallowEtagHeaderFilter());
		filterRegistrationBean.addUrlPatterns("/imageTag/*");
		filterRegistrationBean.setName("etagFilter");
		return filterRegistrationBean;
	}
}
