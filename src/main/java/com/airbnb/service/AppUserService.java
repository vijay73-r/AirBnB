package com.airbnb.service;

import com.airbnb.dto.AppUserDto;
import com.airbnb.dto.LoginDto;

public interface AppUserService {

    public AppUserDto createAppUser(AppUserDto dto);

    public String verifyUser(LoginDto dto);
}
