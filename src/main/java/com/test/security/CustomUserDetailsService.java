//package com.test.security;
//
//import com.test.entity.UserPassword;
//import com.test.entity.Users;
//import com.test.repository.UserPasswordRepository;
//import com.test.repository.UserRepository;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.core.GrantedAuthority;
//import org.springframework.security.core.authority.SimpleGrantedAuthority;
//import org.springframework.security.core.userdetails.User;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.core.userdetails.UsernameNotFoundException;
//import org.springframework.stereotype.Service;
//
//import java.util.Optional;
//import java.util.Set;
//import java.util.stream.Collectors;
//
//@Service
//public class CustomUserDetailsService implements UserDetailsService {
//    @Autowired
//
//    private UserRepository userRepository;
//@Autowired
//    private UserPasswordRepository passwordRepository;
//
//    public CustomUserDetailsService(UserRepository userRepository) {
//        this.userRepository = userRepository;
//    }
//
//    @Override
//    public UserDetails loadUserByUsername(String usernameOrEmail) throws UsernameNotFoundException {
//          Users user = userRepository.findByUserNameOrEmail(usernameOrEmail, usernameOrEmail)
//                 .orElseThrow(() ->
//                         new UsernameNotFoundException("User not found with username or email: "+ usernameOrEmail));
//
//
////        Optional<UserPassword> password = passwordRepository.findByUserId(user.getId());
//        Optional<UserPassword> password = Optional.ofNullable(passwordRepository.findByUserIDAndIsActiveFalse(user));
//
//            if (!password.isPresent()) {
//                throw new UsernameNotFoundException("Password Not Found for user: " + user);
//            }
//
//    Set<GrantedAuthority> authorities = user
//                .getUserVsRoles()
//                .stream()
//                .map((role) -> new SimpleGrantedAuthority(role.getRoleID().getRuleName().toString())).collect(Collectors.toSet());
//
//        return new User(user.getEmail(),
//                password.get().getPassword(),authorities);
//    }
//}
