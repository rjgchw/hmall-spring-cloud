package org.rjgchw.hmall.common.feign;

import org.rjgchw.hmall.common.security.oauth2.AuthorizationHeaderUtil;
import org.springframework.context.annotation.Bean;

import feign.RequestInterceptor;

/**
 *
 * @author Huangw
 * @date 2021-02-18 22:15
 */
public class OAuth2InterceptedFeignConfiguration {

    @Bean(name = "oauth2RequestInterceptor")
    public RequestInterceptor getOAuth2RequestInterceptor(AuthorizationHeaderUtil authorizationHeaderUtil) {
        return new TokenRelayRequestInterceptor(authorizationHeaderUtil);
    }
}
