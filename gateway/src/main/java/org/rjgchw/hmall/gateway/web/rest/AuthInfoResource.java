package org.rjgchw.hmall.gateway.web.rest;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author Huangw
 * @date 2021-02-23 17:32
 */
@RestController
@RequestMapping("/api")
public class AuthInfoResource {

    @Value("${spring.security.oauth2.client.provider.oidc.issuer-uri:}")
    private String issuer;
    @Value("${spring.security.oauth2.client.registration.oidc.client-id:}")
    private String clientId;


    @GetMapping("/auth-info")
    public AuthInfoVO getAuthInfo() {
        return new AuthInfoVO(issuer, clientId);
    }

    /**
     *
     * @author Huangw
     * @date 2021-02-23 17:15
     */
    class AuthInfoVO {
        private String issuer;
        private String clientId;

        AuthInfoVO(String issuer, String clientId) {
            this.issuer = issuer;
            this.clientId = clientId;
        }

        public String getIssuer() {
            return this.issuer;
        }

        public void setIssuer(String issuer) {
            this.issuer = issuer;
        }

        public String getClientId() {
            return clientId;
        }

        public void setClientId(String clientId) {
            this.clientId = clientId;
        }

        @Override
        public String toString() {
            return "AuthInfoVO{" +
                "issuer='" + issuer + '\'' +
                ", clientId='" + clientId + '\'' +
                '}';
        }
    }
}
