package com.hotelhub.backend.security.custom;


import com.hotelhub.backend.user.entity.User;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

@Getter

public class CustomUserDetails implements UserDetails {
    // Tôi tạo một class tên CustomUserDetails, và class này là một UserDetails theo chuẩn Spring Security.
    private final User user;
    // final la khai bao bien user va chi dc su dung trong Class nay va khong the trỏ sang Đối Tượng Uesr Khác

    /*
    UserDetails dung de dinh nghia 1 User qua  UserDetails
    No yeu cau username, password, authorities, account status de tuan thu User cua Spring Security
     */
    // GrantedAuthority Day la interface cua spring security dai dien cho Quyen cua User dang co
    private final Set<GrantedAuthority> authorities;
    // Danh Sách Quyền của User 
    // HashSet Dung set vi quyen khong nen bi trung

    public CustomUserDetails(User user){
        this.user = user;
        this.authorities = new HashSet<>();

        // Role
        if (user.getRole() != null){
            authorities.add(
                    new SimpleGrantedAuthority(
                            "ROLE" + user.getRole().getName().name()
                    )
            );
            // permission
            if (user.getRole().getPermissions() != null){
                user.getRole().getPermissions()
                        .forEach(permission -> authorities.add(new SimpleGrantedAuthority(
                                permission.getName().name()
                        )));
                // SimpleGrantedAuthority Day la implementation don gian cua GrantedAuthority
                // De biet User co Quyen nao

            }
        }
    }
    // Collection dung de tra danh sach quyen
    // Collection ke thua GrantedAuthority vi 1 User co the ke thua nhieu Authority
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities(){
        return authorities;
    }

    @Override
    public String getPassword(){
        return user.getPasswordHash();
    }
    @Override
    public String getUsername(){
        return user.getEmail();
    }
    @Override
    public boolean isAccountNonExpired(){
        return true;
    }
    @Override
    public boolean isAccountNonLocked(){
        return true;
    }
    @Override
    public boolean isCredentialsNonExpired(){
        return true;
    }
    @Override
    public boolean isEnabled(){
        return user.getUserStatus().name().equals("ACTIVE");
    }
}

// CustomUserDetail la 1 Object cua Entity user la cau noi giua User va Security
/*
User -> CustomUserDetails-> Security
 */
