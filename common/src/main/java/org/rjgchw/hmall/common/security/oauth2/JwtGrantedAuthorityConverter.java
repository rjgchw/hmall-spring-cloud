package org.rjgchw.hmall.common.security.oauth2;

import org.rjgchw.hmall.common.security.SecurityWebUtils;
import org.springframework.core.convert.converter.Converter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;

import java.util.Collection;

public class JwtGrantedAuthorityConverter implements Converter<Jwt, Collection<GrantedAuthority>> {

    public JwtGrantedAuthorityConverter() {
        // Bean extracting authority.
    }

    @Override
    public Collection<GrantedAuthority> convert(Jwt jwt) {
        return SecurityWebUtils.extractAuthorityFromClaims(jwt.getClaims());
    }
}
