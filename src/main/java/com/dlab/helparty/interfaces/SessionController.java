package com.dlab.helparty.interfaces;

import com.dlab.helparty.application.UserService;
import com.dlab.helparty.domain.SessionRequestDto;
import com.dlab.helparty.domain.SessionResponseDto;
import com.dlab.helparty.domain.User;
import com.dlab.helparty.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;
import java.net.URISyntaxException;

@RestController
public class SessionController {

    @Autowired
    private UserService userService;

    @Autowired
    private JwtUtil jwtUtil;

    // 로그인
    @PostMapping("/session")
    public ResponseEntity<?> create(
            @RequestBody SessionRequestDto resource
            ) throws URISyntaxException {

        String email = resource.getEmail();
        String password = resource.getPassword();

        User user = userService.authenticate(email, password);

        String accessToken = jwtUtil.createToken(user.getId(), user.getName());

        String url = "/session";

        return ResponseEntity.created(new URI(url))
                .body(SessionResponseDto.builder()
                        .accessToken(accessToken)
                        .build());
    }
}
