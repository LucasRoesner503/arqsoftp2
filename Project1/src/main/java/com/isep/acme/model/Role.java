package com.isep.acme.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;

import lombok.Value;

import javax.persistence.Id;


@AllArgsConstructor
@NoArgsConstructor
public class Role implements GrantedAuthority {


    public static final String Admin = "Admin";

    public static final String Mod = "Mod";

    public static final String RegisteredUser = "RegisteredUser";

    @Id
    @org.springframework.data.neo4j.core.schema.Id
    private String authority;


    @Override
    public String getAuthority() {
        return this.authority;
    }
}
