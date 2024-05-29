package com.ftn.sbnz.service.controllers;

import com.ftn.sbnz.model.DTO.LoginDTO;
import com.ftn.sbnz.model.DTO.TokenDTO;
import com.ftn.sbnz.model.DTO.UserDTO;
import com.ftn.sbnz.model.ErrorResponseMessage;
import com.ftn.sbnz.service.security.SecurityUser;
import com.ftn.sbnz.service.security.jwt.JwtTokenUtil;
import com.ftn.sbnz.service.services.interfaces.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import java.util.Locale;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import javax.mail.MessagingException;
import org.springframework.context.MessageSource;

@CrossOrigin
@RestController
@RequestMapping("api/user")
public class UserController {
    private final IUserService userService;

    @Autowired
    private  JwtTokenUtil jwtTokenUtil;
    private final JwtTokenUtil tokens;


    @Autowired
    private AuthenticationManager authenticationManager;
    private final MessageSource messageSource;

    @Autowired
    public UserController(IUserService userService, JwtTokenUtil tokens,  MessageSource messageSource) {
        this.userService = userService;
        this.tokens = tokens;
        this.messageSource = messageSource;
    }


    @PostMapping(value = "/register")
    public ResponseEntity<UserDTO> createUser(@RequestBody @Valid UserDTO userDTO){
        if(this.userService.findByEmail(userDTO.getEmail()).isPresent())
            return new ResponseEntity(
                    new ErrorResponseMessage("User with that email already exists!"),
                    HttpStatus.BAD_REQUEST
            );
        UserDTO user = this.userService.createUserDTO(userDTO);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }
    @PostMapping("/login")
    public ResponseEntity<TokenDTO> logIn(@Valid @RequestBody LoginDTO login) {
        try {

            TokenDTO token = new TokenDTO();
            SecurityUser userDetails = (SecurityUser) this.userService.findByUsername(login.getEmail());

            String tokenValue = this.jwtTokenUtil.generateToken(userDetails);
            token.setToken(tokenValue);
            token.setRefreshToken(this.jwtTokenUtil.generateRefreshToken(userDetails));
            Authentication authentication =
                    this.authenticationManager.authenticate(
                            new UsernamePasswordAuthenticationToken(login.getEmail(),
                                    login.getPassword()));

            SecurityContextHolder.getContext().setAuthentication(authentication);

            return new ResponseEntity<>(token, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity(new ErrorResponseMessage(
                    this.messageSource.getMessage("user.badCredentials", null, Locale.getDefault())
            ), HttpStatus.BAD_REQUEST);
        }

    }



}
