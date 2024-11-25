//package com.test.auth.TestEmpAuth.service;
//
//import com.test.auth.TestEmpAuth.model.UserPrincipal;
//import com.test.auth.TestEmpAuth.model.Users;
//import com.test.auth.TestEmpAuth.repository.UserRepository;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.core.userdetails.UsernameNotFoundException;
//import org.springframework.stereotype.Service;
//
//@Service
//public class CustomUserDetailsService implements UserDetailsService {
//
//    @Autowired
//    private UserRepository userRepository;
//
//
//    @Override
//    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//        Users user = userRepository.findByUsername(username);
//
//        if (user == null)
//        {
//            System.out.println("User not found!!");
//            throw new UsernameNotFoundException("User not found!!");
//        }
//        return new UserPrincipal(user);
//    }
//}
