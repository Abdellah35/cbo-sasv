package com.cbo.core.service;

import com.cbo.core.exception.IncorrectUsernameOrPasswordException;
import com.cbo.core.model.JwtRequest;
import com.cbo.core.response.JwtResponse;
import com.cbo.core.utility.JwtUtil;
import com.cbo.core.model.User;
import com.cbo.core.repo.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class JwtService implements UserDetailsService {

    @Autowired
    private UserRepository userRepo;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private AuthenticationManager authenticationManager;




    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user =userRepo.findByUserName(username);
        System.out.println(user.getEmail());

        if (user != null ){

            return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), getAuthorities(user));
        }
        else {
            throw new UsernameNotFoundException("Username is not valid");
        }
    }


    public JwtResponse createJwtToken(JwtRequest jwtRequest) throws Exception {
        String username = jwtRequest.getUserName();
        String password = jwtRequest.getUserPassword();
        System.out.println(username);
        System.out.println(password);
        authenticate(username,password);
        System.out.println("Authenticated");

        final UserDetails userDetails = loadUserByUsername(username);

        String newGeneratedToken = jwtUtil.generateToken(userDetails);

        User user = userRepo.findByUserName(username);

        return new JwtResponse(user, newGeneratedToken);
    }


    private Set getAuthorities(User user){
        Set authorities = new HashSet();
        if (user != null){
            user.getRoles().forEach(role -> {
                authorities.add(new SimpleGrantedAuthority(role.getName().toString()));
            });
        }

        return authorities;
    }


    private void authenticate(String username, String password) throws Exception {
        try {
            System.out.println(username);
            User user = userRepo.findByUserName(username);
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username,password, getAuthorities(user)));

        } catch (DisabledException e){
            throw new IncorrectUsernameOrPasswordException("User is disabled");
        } catch ( BadCredentialsException e){
            throw new IncorrectUsernameOrPasswordException("Incorrect Username Or Password!");
        } catch (AuthenticationException e){
            throw new IncorrectUsernameOrPasswordException("Authentication is failed");
        }
    }


}
