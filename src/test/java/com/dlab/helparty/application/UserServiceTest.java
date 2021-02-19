package com.dlab.helparty.application;

import com.dlab.helparty.domain.User;
import com.dlab.helparty.domain.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

class UserServiceTest {

    private UserService userService;

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @BeforeEach
    public void setUp(){
        MockitoAnnotations.openMocks(this);
        userService = new UserService(userRepository, passwordEncoder);
    }

    @Test
    public void authenticateWithValidAttributes(){
        String email = "tester@example.com";
        String password = "test";

        User mockUser = User.builder()
                .email(email).build();

        given(userRepository.findByEmail(email))
                .willReturn(Optional.ofNullable(mockUser));

        given(passwordEncoder.matches(any(), any())).willReturn(true);

        User user = userService.authenticate(email, password);

        assertEquals(user.getEmail(), email);
    }

    @Test
    public void authenticateWithNotExistedEmailAttributes(){

        String email = "x@example.com";
        String password = "test";

        given(userRepository.findByEmail(email)).willReturn(Optional.empty());

        EmailNotExistedException emailNotExistedException
                = assertThrows(EmailNotExistedException.class,
                () -> userService.authenticate(email, password));

        assertEquals("Email is not registered: x@example.com",
                emailNotExistedException.getMessage());

    }

    @Test
    public void authenticateWithWrongPassword(){
        String email = "tester@example.com";
        String password = "x";

        User user = User.builder()
                .password(password)
                .build();

        given(userRepository.findByEmail(email))
                .willReturn(Optional.of(user));

        given(passwordEncoder.matches(any(), any())).willReturn(false);

        PasswordWrongException passwordWrongException
                = assertThrows(PasswordWrongException.class,
                () -> userService.authenticate(email, password));
        assertEquals(passwordWrongException.getMessage(),
                "Password is wrong");
    }

    @Test
    public void registerUserWithExistedEmail(){

        String email = "tester@example.com";
        String name = "tester";
        String password = "test";

        User mockUser = User.builder()
                .email("tetser@example.com")
                .name("tester")
                .build();

        given(userRepository.findByEmail(email)).willReturn(Optional.of(mockUser));

        EmailExistedException emailExistedException
                 = assertThrows(EmailExistedException.class,
                () -> userService.registerUser(email, name, password));

        assertEquals("Email is already registered: tester@example.com", emailExistedException.getMessage());
        verify(userRepository, never()).save(any());

    }

}