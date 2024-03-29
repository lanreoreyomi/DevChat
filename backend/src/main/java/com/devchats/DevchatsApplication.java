package com.devchats;

import com.devchats.Audit.SpringAuditorAware;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@EnableJpaAuditing(auditorAwareRef = "auditorAware")
//@EnableSwagger2
public class DevchatsApplication {

  @Bean
  public AuditorAware auditorAware() {
    return new SpringAuditorAware();
  }

  @Bean
  public Docket devChatsApi() {
    return new Docket(DocumentationType.SWAGGER_2).select()
        .apis(RequestHandlerSelectors.basePackage("com.devchats.devchats")).build();
  }

  @Bean
  public JavaTimeModule dateTimeModule(){
    return new JavaTimeModule();
  }
  public static void main(String[] args) {
    SpringApplication.run(DevchatsApplication.class, args);

  }


}
