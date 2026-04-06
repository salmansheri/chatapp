package com.salman.ChatAppBackend.websocket;

import java.util.List;

import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.MediaType;
import org.springframework.messaging.converter.DefaultContentTypeResolver;
import org.springframework.messaging.converter.JacksonJsonMessageConverter;
import org.springframework.messaging.converter.MessageConverter;
import org.springframework.messaging.handler.invocation.HandlerMethodArgumentResolver;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.security.messaging.context.AuthenticationPrincipalArgumentResolver;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

import lombok.RequiredArgsConstructor;
import tools.jackson.databind.json.JsonMapper;

@Configuration
@EnableWebSocket
@Order(Ordered.HIGHEST_PRECEDENCE + 99)
@RequiredArgsConstructor
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {
    
    @Override
    public void configureMessageBroker(final MessageBrokerRegistry registry) {
       registry.enableSimpleBroker("/user"); 
       registry.setApplicationDestinationPrefixes("/app"); 
       registry.setUserDestinationPrefix("/users"); 
       
    }

    @Override
    public void registerStompEndpoints(final StompEndpointRegistry registry) {
       registry.addEndpoint("/websocket")
                .setAllowedOrigins("http://locahost:3000")
                .withSockJS(); 
    }

    @Override
    public void addArgumentResolvers(final List<HandlerMethodArgumentResolver> argumentResolvers) {
       argumentResolvers.add(new AuthenticationPrincipalArgumentResolver()); 
    }

    @Override
    public boolean configureMessageConverters(List<MessageConverter> messageConverters) {
       DefaultContentTypeResolver resolver = new DefaultContentTypeResolver(); 

       resolver.setDefaultMimeType(MediaType.APPLICATION_JSON);

       JsonMapper jsonMapper = JsonMapper.builder()
        .findAndAddModules()
        .build(); 

        JacksonJsonMessageConverter converter = new JacksonJsonMessageConverter(jsonMapper); 

        converter.setContentTypeResolver(resolver);

        messageConverters.add(converter); 

        return false;
    }

    
    
}
