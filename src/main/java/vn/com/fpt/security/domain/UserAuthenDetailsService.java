package vn.com.fpt.security.domain;


import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import vn.com.fpt.entity.authentication.Account;
import vn.com.fpt.repositories.AccountRepository;

@Component
@RequiredArgsConstructor
public class UserAuthenDetailsService implements IUserDetailsAuth {

    private final AccountRepository accountRepository;

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Account account = accountRepository.findByUserName(username)
                .orElseThrow(() -> new UsernameNotFoundException("Không tìm thấy tài khoản : " + username));

        return AccountAuthenticationDetail.build(account);
    }
}
