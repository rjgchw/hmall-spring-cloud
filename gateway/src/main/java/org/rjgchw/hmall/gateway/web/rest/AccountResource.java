package org.rjgchw.hmall.gateway.web.rest;

import org.rjgchw.hmall.common.config.Constants;
import org.rjgchw.hmall.gateway.web.rest.vo.UserVO;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import java.security.Principal;
import java.util.Map;
import java.util.stream.Collectors;

/**
 *
 * @author Huangw
 * @date 2021-02-23 17:31
 */
@RestController
@RequestMapping("/api")
public class AccountResource {

    private static class AccountResourceException extends RuntimeException {

        private static final long serialVersionUID = 1L;

        private AccountResourceException(String message) {
            super(message);
        }
    }

    /**
     * {@code GET  /account} : get the current user.
     *
     * @param principal the current user; resolves to {@code null} if not authenticated.
     * @return the current user.
     * @throws AccountResourceException {@code 500 (Internal Server Error)} if the user couldn't be returned.
     */
    @GetMapping("/account")
    @SuppressWarnings("unchecked")
    public Mono<UserVO> getAccount(Principal principal) {
        if (principal instanceof AbstractAuthenticationToken) {
            AbstractAuthenticationToken authToken = (AbstractAuthenticationToken) principal;
            Map<String, Object> attributes;
            if (authToken instanceof OAuth2AuthenticationToken) {
                attributes = ((OAuth2AuthenticationToken) authToken).getPrincipal().getAttributes();
            } else if (authToken instanceof JwtAuthenticationToken) {
                attributes = ((JwtAuthenticationToken) authToken).getTokenAttributes();
            } else {
                throw new IllegalArgumentException("AuthenticationToken is not OAuth2 or JWT!");
            }
            UserVO user = getUser(attributes);
            user.setAuthorities(authToken.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toSet()));
            return Mono.just(user);
        } else {
            throw new AccountResourceException("User could not be found");
        }
    }

    private static UserVO getUser(Map<String, Object> details) {
        UserVO user = new UserVO();
        // handle resource server JWT, where sub claim is email and uid is ID
        String uidKey = "uid";
        String subKey = "sub";
        if (details.get(uidKey) != null) {
            user.setId((String) details.get(uidKey));
            user.setLogin((String) details.get(subKey));
        } else {
            user.setId((String) details.get(subKey));
        }
        String preferredUsernameKey = "preferred_username";
        if (details.get(preferredUsernameKey) != null) {
            user.setLogin(((String) details.get(preferredUsernameKey)).toLowerCase());
        } else if (user.getLogin() == null) {
            user.setLogin(user.getId());
        }
        String givenNameKey = "given_name";
        if (details.get(givenNameKey) != null) {
            user.setFirstName((String) details.get(givenNameKey));
        }
        String familyNameKey = "family_name";
        if (details.get(familyNameKey) != null) {
            user.setLastName((String) details.get(familyNameKey));
        }
        String emailVerifiedKey = "email_verified";
        if (details.get(emailVerifiedKey) != null) {
            user.setActivated((Boolean) details.get(emailVerifiedKey));
        }
        String emailKey = "email";
        if (details.get(emailKey) != null) {
            user.setEmail(((String) details.get(emailKey)).toLowerCase());
        } else {
            user.setEmail((String) details.get(subKey));
        }
        String langKeyKey = "langKey";
        String localeKey = "locale";
        String underScoreKey = "_";
        String joinerKey = "-";
        if (details.get(langKeyKey) != null) {
            user.setLangKey((String) details.get(langKeyKey));
        } else if (details.get(localeKey) != null) {
            // trim off country code if it exists
            String locale = (String) details.get(localeKey);
            if (locale.contains(underScoreKey)) {
                locale = locale.substring(0, locale.indexOf('_'));
            } else if (locale.contains(joinerKey)) {
                locale = locale.substring(0, locale.indexOf('-'));
            }
            user.setLangKey(locale.toLowerCase());
        } else {
            // set langKey to default if not specified by IdP
            user.setLangKey(Constants.DEFAULT_LANGUAGE);
        }
        String pictureKey = "picture";
        if (details.get(pictureKey) != null) {
            user.setImageUrl((String) details.get(pictureKey));
        }
        user.setActivated(true);
        return user;
    }
}
