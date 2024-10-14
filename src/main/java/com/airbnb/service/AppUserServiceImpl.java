package com.airbnb.service;

import com.airbnb.dto.AppUserDto;
import com.airbnb.dto.LoginDto;
import com.airbnb.entity.AppUser;
import com.airbnb.exception.UserExists;
import com.airbnb.repository.AppUserRepository;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AppUserServiceImpl implements AppUserService{

    private AppUserRepository appUserRepository;
    private JWTService jwsService;

    public AppUserServiceImpl(AppUserRepository appUserRepository, JWTService jwsService){
        this.appUserRepository = appUserRepository;
        this.jwsService = jwsService;
    }

    @Override
    public AppUserDto createAppUser(AppUserDto dto) {
        Optional<AppUser> opEmail = appUserRepository.findByEmail(dto.getEmail());
        if(opEmail.isPresent()){
            throw new UserExists("email already exists");
        }
        Optional<AppUser> opUsername = appUserRepository.findByUsername(dto.getUsername());
        if(opUsername.isPresent()){
            throw new UserExists("username already exists");
        }

        AppUser save = mapToEntity(dto);
        save.setRole("ROLE_USER");
        AppUser saved = appUserRepository.save(save);


        return mapToDto(saved);
    }

    @Override
    public String verifyUser(LoginDto dto) {
        Optional<AppUser> opUser = appUserRepository.findByUsername(dto.getUsername());
        if(opUser.isPresent()){
            AppUser appUser = opUser.get();
            if(BCrypt.checkpw(dto.getPassword(), appUser.getPassword())){
                return jwsService.generateToken(appUser);
            }
        }
        return null;
    }


    public AppUser mapToEntity(AppUserDto dto) {
        AppUser entity = new AppUser();
        entity.setName(dto.getName());
        entity.setEmail(dto.getEmail());
        entity.setUsername(dto.getUsername());
        String hashpw = BCrypt.hashpw(dto.getPassword(), BCrypt.gensalt());
        entity.setPassword(hashpw);
        return entity;
    }

    public AppUserDto mapToDto(AppUser entity) {
        AppUserDto dto = new AppUserDto();
        dto.setId(entity.getId());
        dto.setName(entity.getName());
        dto.setEmail(entity.getEmail());
        dto.setUsername(entity.getUsername());
        return dto;
    }

}
