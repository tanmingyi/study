package com.sky.config;

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.sky.properties.AliOssProperties;
import com.sky.utils.AliOssUtil;

import lombok.extern.slf4j.Slf4j;

@Configuration
@Slf4j
public class AliOssConfiguration {
    
    @Bean
    @ConditionalOnMissingBean // 当容器中没有这个Bean的情况下，才会创建这个Bean
    // 当容器中没有这个Bean的情况下，才会创建这个Bean
    public AliOssUtil getAliOssUtil(AliOssProperties aliOssProperties){
        log.info("开始创建阿里云OSS工具类对象：{}",aliOssProperties);
        AliOssUtil aliOssUtil = new AliOssUtil(aliOssProperties.getEndpoint(), aliOssProperties.getAccessKeyId(), aliOssProperties.getAccessKeySecret(), aliOssProperties.getBucketName());
        log.info("阿里云OSS工具类对象创建完成：{}",aliOssUtil);
        return aliOssUtil;
    }
}
