package org.rjgchw.hmall.storage.client;

import org.springframework.context.annotation.Bean;

import feign.RequestInterceptor;

import org.rjgchw.hmall.storage.security.oauth2.AuthorizationHeaderUtil;

public class OAuth2InterceptedFeignConfiguration {

    @Bean(name = "oauth2RequestInterceptor")
    public RequestInterceptor getOAuth2RequestInterceptor(AuthorizationHeaderUtil authorizationHeaderUtil) {
        return new TokenRelayRequestInterceptor(authorizationHeaderUtil);
    }
}
