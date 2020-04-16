/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.srmsolutions.configs;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 *
 * @author EricR
 */
@Service
public class UserDetailsServiceImpl_SingleTable implements UserDetailsService {

    @Autowired
    JdbcTemplate jdbcTemplate;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        String SQL = "SELECT * FROM employee WHERE email = ?";
        try{
            return jdbcTemplate.queryForObject(SQL,new SimpleUserMapper(), username);
        }catch(Exception e){
            throw new UsernameNotFoundException("Username not found.");
        }
    }
    
    class SimpleUserMapper implements RowMapper<UserDetails>{

        @Override
        public UserDetails mapRow(ResultSet rs, int i) throws SQLException {
            String username = rs.getString("email");
            String password = rs.getString("password");
            String tempRole = rs.getString("user_role_id");
            String role = "";
            switch (tempRole) {
                case "1":
                    role = "ROLE_ADMIN";
                    break;
                case "2":
                    role = "ROLE_HR";
                    break;
                case "3":
                    role = "ROLE_EO";
                    break;
                case "4":
                    role = "ROLE_USER";
                    break;      
            }   
            Set<GrantedAuthority> grantedAuthorities = new HashSet<>();
            grantedAuthorities.add(new SimpleGrantedAuthority(role));
            return new User(username, password, grantedAuthorities);
        }
        
    }

}