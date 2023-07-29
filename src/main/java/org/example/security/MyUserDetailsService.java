package org.example.security;

import org.example.dao.AuthGroupRepoI;
import org.example.dao.CustomerRepoI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class MyUserDetailsService implements UserDetailsService {

    CustomerRepoI customerRepoI;
    AuthGroupRepoI authGroupRepoI;

    @Autowired
    public MyUserDetailsService(CustomerRepoI customerRepoI, AuthGroupRepoI authGroupRepoI) {
        this.customerRepoI = customerRepoI;
        this.authGroupRepoI = authGroupRepoI;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        return new MyUserPrincipal
                (customerRepoI.findByEmailAllIgnoreCase(username)
                .orElseThrow(() -> new UsernameNotFoundException("Email not found, " + username)),
                        authGroupRepoI.findByEmail(username));
    }
}
