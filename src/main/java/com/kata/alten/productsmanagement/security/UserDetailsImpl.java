package com.kata.alten.productsmanagement.security;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.kata.alten.productsmanagement.persistence.entities.UserEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
import java.util.Objects;

/**
 * user details implementation service used for security
 */
public class UserDetailsImpl implements UserDetails {
  private static final long serialVersionUID = 1L;

  private Long id;

  private String username;

  private String email;



  @JsonIgnore
  private String password;

  private Collection<? extends GrantedAuthority> authorities;

  /**
   * constructor
   * @param id id
   * @param username username
   * @param email email
   * @param password password
   * @param authorities authorities or roles
   */
  public UserDetailsImpl(Long id, String username, String email, String password,
      Collection<? extends GrantedAuthority> authorities) {
    this.id = id;
    this.username = username;
    this.email = email;
    this.password = password;
    this.authorities = authorities;
  }

  /**
   * build user details
   * @param user user
   * @param lngSessionId session id
   * @return user details impl
   */
  public static UserDetailsImpl build(UserEntity user) {
    List<GrantedAuthority> authorities =  List.of(
    new SimpleGrantedAuthority(user.getUserRole().name()));

    return new UserDetailsImpl(
        user.getId().longValue(),
        user.getUsername(),
        user.getEmail(),
        user.getPasswordHash(),
        authorities);
  }

  /**
   * get authorities or roles
   * @return list
   */
  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    return authorities;
  }

  public Long getId() {
    return id;
  }

  public String getEmail() {
    return email;
  }

  @Override
  public String getPassword() {
    return password;
  }

  @Override
  public String getUsername() {
    return username;
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
  public boolean equals(Object o) {
    if (this == o)
      return true;
    if (o == null || getClass() != o.getClass())
      return false;
    UserDetailsImpl user = (UserDetailsImpl) o;
    return Objects.equals(id, user.id);
  }
}
