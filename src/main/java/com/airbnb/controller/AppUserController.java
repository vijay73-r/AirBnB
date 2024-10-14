package com.airbnb.controller;

import com.airbnb.dto.AppUserDto;
import com.airbnb.dto.JWTToken;
import com.airbnb.dto.LoginDto;
import com.airbnb.service.AppUserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/appuser")
// This controller will handle CRUD operations for the AppUser entity.
public class AppUserController {

    private AppUserService appUserService;

    public AppUserController(AppUserService appUserService) {
        this.appUserService = appUserService;
    }

    @PostMapping("/addUser")
    public ResponseEntity<AppUserDto> addUser(@RequestBody AppUserDto dto) {
        AppUserDto user = appUserService.createAppUser(dto);
        return new ResponseEntity<>(user, HttpStatus.CREATED);

    }

    @PostMapping("/login")
    public ResponseEntity<?> verifyLogin(@RequestBody LoginDto dto) {
        String token =appUserService.verifyUser(dto);

        if(token != null){
            JWTToken jwtToken = new JWTToken();
            jwtToken.setTokenType("JWT");
            jwtToken.setToken(token);
            return new ResponseEntity<>(jwtToken, HttpStatus.OK);
        }else{
            return new ResponseEntity<>("Invalid Credentials", HttpStatus.UNAUTHORIZED);
        }


    }
}
