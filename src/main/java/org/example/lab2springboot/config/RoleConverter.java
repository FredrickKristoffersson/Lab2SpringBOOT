package org.example.lab2springboot.config;

import org.springframework.core.convert.converter.Converter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;

import java.util.Collection;

public class RoleConverter implements Converter<Jwt, Collection<GrantedAuthority>> {

        @Override
        public Collection<GrantedAuthority> convert(Jwt jwt) {
                JwtGrantedAuthoritiesConverter grantedAuthoritiesConverter = new JwtGrantedAuthoritiesConverter();
                grantedAuthoritiesConverter.setAuthorityPrefix("ROLE_");
                grantedAuthoritiesConverter.setAuthoritiesClaimName("roles");
                return grantedAuthoritiesConverter.convert(jwt);
        }
}

