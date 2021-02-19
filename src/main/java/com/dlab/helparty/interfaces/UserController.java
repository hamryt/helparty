package com.dlab.helparty.interfaces;

import com.dlab.helparty.application.UserService;
import com.dlab.helparty.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Optional;

@RestController
public class UserController {

    @Autowired
    private UserService userService;

    // 회원가입
    @PostMapping("/users")
    public ResponseEntity<?> create(
            @RequestBody User resource
            ) throws URISyntaxException {

        String email = resource.getEmail();
        String name = resource.getName();
        String password = resource.getPassword();

        User user = userService.registerUser(email, name, password);

        String url = "/users/" + user.getId();
        return ResponseEntity.created(new URI(url)).body("{}");
    }

}
