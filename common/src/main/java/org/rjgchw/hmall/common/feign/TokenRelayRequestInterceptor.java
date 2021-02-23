package org.rjgchw.hmall.common.feign;

import feign.RequestInterceptor;
import feign.RequestTemplate;
import java.util.Optional;
import org.rjgchw.hmall.common.security.oauth2.AuthorizationHeaderUtil;

/**
 * @author Huangw
 * @date 2021-02-23 17:39
 */
public class TokenRelayRequestInterceptor implements RequestInterceptor {

    public static final String AUTHORIZATION = "Authorization";

    private final AuthorizationHeaderUtil authorizationHeaderUtil;

    public TokenRelayRequestInterceptor(AuthorizationHeaderUtil authorizationHeaderUtil) {
        super();
        this.authorizationHeaderUtil = authorizationHeaderUtil;
    }

    @Override
    public void apply(RequestTemplate template) {
        Optional<String> authorizationHeader = authorizationHeaderUtil.getAuthorizationHeader();
        authorizationHeader.ifPresent(s -> template.header(AUTHORIZATION, s));
    }
}
