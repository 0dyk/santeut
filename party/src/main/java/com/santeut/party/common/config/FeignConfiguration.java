package com.santeut.party.common.config;

import com.santeut.party.feign.JwtTokenInterceptor;
import feign.Logger;
import feign.RequestInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class FeignConfiguration {

//  @Bean
//  public RequestInterceptor requestInterceptor() {
//    return new JwtTokenInterceptor();
//  }

  @Bean
  Logger.Level feignLoggerLevel() {
    return Logger.Level.FULL;
  }

}