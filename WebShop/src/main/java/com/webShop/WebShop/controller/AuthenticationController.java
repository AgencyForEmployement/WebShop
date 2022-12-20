package com.webShop.WebShop.controller;

import com.webShop.WebShop.WebShopApplication;
import com.webShop.WebShop.model.User;
import com.webShop.WebShop.service.UserService;
import com.webShop.WebShop.utils.TokenUtils;
import com.webShop.WebShop.dto.AuthenticationRequestDto;
import com.webShop.WebShop.dto.UserReqistrationDto;
import com.webShop.WebShop.dto.UserTokenStateDto;
import lombok.AllArgsConstructor;
import org.apache.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/authentication")
@AllArgsConstructor
public class AuthenticationController {

    private final AuthenticationManager authenticationManager;
    private final TokenUtils tokenUtils;
    private final UserService userService;
    final static Logger log = Logger.getLogger(WebShopApplication.class.getName());

    @PostMapping("/login")
    public ResponseEntity<UserTokenStateDto> login(@RequestBody AuthenticationRequestDto authenticationRequest) {

        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                authenticationRequest.email, authenticationRequest.password));

        SecurityContextHolder.getContext().setAuthentication(authentication);

        User user = (User)authentication.getPrincipal();
        if(user == null){
            log.error("Login failed! Wrong credentials, user not found. Try again.");
        }
        String jwt = tokenUtils.generateToken(user.getEmail(), user.getRole().getRoleName());
        log.info("User with user id  " + user.getId() + " is logged in.");
        return ResponseEntity.ok(new UserTokenStateDto(jwt, user.getRole().getRoleName()));
    }

    @PostMapping(value="/register")
    public ResponseEntity<User> registration(@RequestBody UserReqistrationDto userReqistrationDto) {
        User user = userService.findByEmail(userReqistrationDto.email);
        if (user == null){
            user = userService.save(new User(userReqistrationDto.name, userReqistrationDto.surname, userReqistrationDto.email, userReqistrationDto.password, userReqistrationDto.telephoneNumber, userReqistrationDto.address, userReqistrationDto.education, userReqistrationDto.experience));
          log.info("New user is registered! User id is " + user.getId());
            return new ResponseEntity<>(user, HttpStatus.CREATED);
        }
        else {
            log.error("Registration failed. This user is already registered with id " + user.getId());
            log.warn("Duplicate registration request for user with id "+ user.getId());
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

}
