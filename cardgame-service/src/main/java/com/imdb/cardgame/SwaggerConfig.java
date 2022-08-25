package com.imdb.cardgame;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
* Abrir URL: http://localhost:8081/swagger-ui/index.html
 * 
 * @EnableWebMvc - resolver problema de NullPointerException (analisar melhor o motivo)
 * 
 */
@Configuration
@EnableWebMvc
@EnableSwagger2
public class SwaggerConfig {

	@Bean
	public Docket api() {
		return new Docket(DocumentationType.SWAGGER_2)
				.apiInfo(buildApiInfo())
				.select()
				//.apis(RequestHandlerSelectors.basePackage("com.imdb.cardgame.controller"))
				//.apis(RequestHandlerSelectors.basePackage("com.imdb.cardgame.security.controller"))
				.paths(PathSelectors.regex("/cardgame.*"))
				//.paths(PathSelectors.regex("/authentication.*"))
				.build();
	}
	
	private ApiInfo buildApiInfo() {
		return new ApiInfoBuilder()
				.title("Movies Card Game")
				.description("REST API Movies Card Game")
				.version("1.0")
				.contact(new Contact("Wagner", null, "wagner@email.com"))
				.build();
	}
}