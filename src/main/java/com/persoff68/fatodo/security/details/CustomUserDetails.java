package com.persoff68.fatodo.security.details;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;
import java.util.UUID;

@Getter
@EqualsAndHashCode(callSuper = true)
public class CustomUserDetails extends User {

    private final UUID id;

    public CustomUserDetails(UUID id, String username, String password,
                             Collection<? extends GrantedAuthority> authorities) {
        super(username, password, authorities);
        this.id = id;
    }

}
