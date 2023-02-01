package com.webShop.WebShop.controller;

import com.webShop.WebShop.WebShopApplication;
import com.webShop.WebShop.dto.*;
import com.webShop.WebShop.model.User;
import com.webShop.WebShop.service.UserService;
import com.webShop.WebShop.utils.TokenUtils;
import lombok.AllArgsConstructor;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping("/authentication")
@AllArgsConstructor
public class AuthenticationController {

    private final AuthenticationManager authenticationManager;
    private final TokenUtils tokenUtils;
    private final UserService userService;
    final static Logger log = Logger.getLogger(WebShopApplication.class.getName());
    @Autowired
    RestTemplate restTemplate;

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

    @GetMapping("/getPayments")
    public ResponseEntity<?> changePayments() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Authorization", "Bearer 1a2s3d4f5g6h7j8k");
        OrderIdDTO dto = new OrderIdDTO("nothing");
        HttpEntity<OrderIdDTO> entity = new HttpEntity<OrderIdDTO>(dto, headers);
        PaymentOptionsDTO result = restTemplate.postForObject("http://localhost:8081/authentication/getCompanyPayments", entity, PaymentOptionsDTO.class);
        if (result != null) {
            return new ResponseEntity<>(result, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(null, HttpStatus.OK);
        }
    }

}
