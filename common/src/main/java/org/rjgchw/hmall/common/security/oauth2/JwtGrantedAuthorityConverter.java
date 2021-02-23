package org.rjgchw.hmall.common.security.oauth2;

import java.util.Collection;
import org.rjgchw.hmall.common.security.CommonSecurityUtils;
import org.springframework.core.convert.converter.Converter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;

/**
 * @author Huangw
 * @date 2021-02-23 17:35
 */
public class JwtGrantedAuthorityConverter implements Converter<Jwt, Collection<GrantedAuthority>> {

    public JwtGrantedAuthorityConverter() {
        // Bean extracting authority.
    }

    @Override
    public Collection<GrantedAuthority> convert(Jwt jwt) {
        return CommonSecurityUtils.extractAuthorityFromClaims(jwt.getClaims());
    }
}
