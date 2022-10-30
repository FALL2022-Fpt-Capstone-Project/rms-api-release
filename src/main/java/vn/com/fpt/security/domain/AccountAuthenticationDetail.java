package vn.com.fpt.security.domain;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import vn.com.fpt.entity.authentication.Account;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class AccountAuthenticationDetail extends Account implements UserDetails {
    private static final long serialVersionUID = 1L;

    private final Collection<? extends GrantedAuthority> authorities;

    public AccountAuthenticationDetail(Long id, String username, String password,
                                       Collection<? extends GrantedAuthority> authorities) {
        super(id, username, password);
        this.authorities = authorities;
    }

    public static AccountAuthenticationDetail build(Account account) {
        List<GrantedAuthority> authorities = account.getRoles().stream()
                .map(role -> new SimpleGrantedAuthority(role.getName().name()))
                .collect(Collectors.toList());

        return new AccountAuthenticationDetail(
                account.getId(),
                account.getUserName(),
                account.getPassword(),
                authorities);
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return super.getPassword();
    }

    @Override
    public String getUsername() {
        return super.getUserName();
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
}
