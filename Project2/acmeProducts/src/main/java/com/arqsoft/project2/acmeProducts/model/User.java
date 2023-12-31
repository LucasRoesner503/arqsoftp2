package com.arqsoft.project2.acmeProducts.model;

import com.arqsoft.project2.acmeProducts.repositories.Idget;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import javax.validation.constraints.Email;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Getter
@Setter
public class User implements UserDetails, Idget<Long> {

    private static final long serialVersionUID = 1L;

    @Id
    @org.springframework.data.neo4j.core.schema.Id
    @org.springframework.data.neo4j.core.schema.GeneratedValue
    @GeneratedValue
    private Long userId;

    @Column(unique = true)
    @Email
    private String username;

    private String password;

    private String fullName;

    @ElementCollection
    private List<Role> authorities = new ArrayList<>();

    @Column(nullable = false, unique = true)
    private String nif;

    @Column(nullable = false)
    private String morada;

/*    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Review> review = new ArrayList<Review>(); */

    protected User() {
        generateUserID();
    }

    public User(final String username, final String password){
        this.username = username;
        this.password = password;
        generateUserID();
    }


    public User(final String username, final String password, final String fullName, final String nif, final String morada) {
        this.username = username;
        this.password = password;
        this.fullName = fullName;
        setNif(nif);
        this.morada = morada;
        generateUserID();
    }

    public User(final Long userId, final String username, final String password, final String fullName, final String nif, final String morada) {
        this.userId = userId;
        this.username = username;
        this.password = password;
        this.fullName = fullName;
        setNif(nif);
        this.morada = morada;
    }

    public User(Long aLong) {
        this.userId = aLong;
    }


    public void addAuthority(Role r) {
        authorities.add(r);
    }

    public void setNif(String nif) {
        if(nif.length() != 9) {
            throw new IllegalArgumentException("NIF must be 9 characters.");
        }
        this.nif = nif;
    }

    public void generateUserID(){
        if(this.userId == null){
            long fullID = UUID.randomUUID().getMostSignificantBits() & Long.MAX_VALUE;
            // Convert the long to a string and get the last 13 characters
            String idString = String.valueOf(fullID);
            int length = idString.length();
            if (length > 13) {
                idString = idString.substring(length - 13);
            }
            this.userId = Long.parseLong(idString);
        }
    }


    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    @Override
    public Long getId() {
        return this.userId;
    }
}

