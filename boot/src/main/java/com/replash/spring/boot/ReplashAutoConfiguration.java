package com.replash.spring.boot;

import com.replash.spring.ReplashCommandDiscoveryBeanPostProcessor;
import com.replash.spring.SpringReplashBuilder;
import com.replash.spring.SpringReplashRunnerBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ReplashAutoConfiguration {
    @Bean
    @ConditionalOnMissingBean(SpringReplashBuilder.class)
    public SpringReplashBuilder springReplashBuilder() {
        return new SpringReplashBuilder();
    }

    @Bean
    @ConditionalOnMissingBean(SpringReplashRunnerBean.class)
    @ConditionalOnExpression("${com.replash.spring.boot.autostart:false}")
    public SpringReplashRunnerBean springReplashRunnerBean(SpringReplashBuilder springReplashBuilder) {
        return new SpringReplashRunnerBean(springReplashBuilder);
    }

    @Bean
    public ReplashCommandDiscoveryBeanPostProcessor replashCommandDiscoveryBeanPostProcessor(SpringReplashBuilder replashBuilder) {
        return new ReplashCommandDiscoveryBeanPostProcessor(replashBuilder);
    }
}
