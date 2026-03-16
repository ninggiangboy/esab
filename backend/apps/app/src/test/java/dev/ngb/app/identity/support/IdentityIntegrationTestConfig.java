package dev.ngb.app.identity.support;

import dev.ngb.app.identity.infrastructure.LoggingOtpSender;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.BeanDefinitionRegistryPostProcessor;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.core.Ordered;

@TestConfiguration
public class IdentityIntegrationTestConfig {

    @Bean
    @Primary
    public TestOtpSender testOtpSender() {
        return new TestOtpSender();
    }

    /** Remove LoggingOtpSender so the application uses TestOtpSender and OTP is captured. */
    @Bean
    public static BeanDefinitionRegistryPostProcessor removeLoggingOtpSender() {
        return new LoggingOtpSenderRemovingPostProcessor();
    }

    private static final class LoggingOtpSenderRemovingPostProcessor
            implements BeanDefinitionRegistryPostProcessor, Ordered {

        @Override
        public void postProcessBeanDefinitionRegistry(BeanDefinitionRegistry registry) throws BeansException {
            String[] names = registry.getBeanDefinitionNames();
            for (String name : names) {
                BeanDefinition def = registry.getBeanDefinition(name);
                if (LoggingOtpSender.class.getName().equals(def.getBeanClassName())) {
                    registry.removeBeanDefinition(name);
                    break;
                }
            }
        }

        @Override
        public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {}

        @Override
        public int getOrder() {
            return Ordered.LOWEST_PRECEDENCE;
        }
    }
}
