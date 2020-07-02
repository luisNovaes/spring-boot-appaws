package com.magsoltec.appaws;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableAutoConfiguration
@ComponentScan
public class ServletInitializer extends SpringBootServletInitializer {

	@Override

	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {

		return application.sources(AppawsApplication.class);

	}

}