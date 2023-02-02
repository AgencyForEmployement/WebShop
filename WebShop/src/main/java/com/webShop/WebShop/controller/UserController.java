package com.webShop.WebShop.controller;

import com.webShop.WebShop.model.User;
import com.webShop.WebShop.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.websocket.server.PathParam;

@RestController
@RequestMapping("/users")
@AllArgsConstructor
public class UserController {

    private final UserService userService;


    @GetMapping(value="/{email}")
    public User getUserByEmail(@PathVariable String email) {
        System.out.println("EMAIL " + email);
        return userService.findByEmail(email);
    }
}
