package com.distributionbox.security;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.distributionbox.entity.User;
import com.distributionbox.mapper.UserMapper;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserMapper userMapper;

    public UserDetailsServiceImpl(UserMapper userMapper) {
        this.userMapper = userMapper;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.eq("username", username).orderByAsc("id");
        List<User> users = userMapper.selectList(wrapper);
        if (users == null || users.isEmpty()) {
            throw new UsernameNotFoundException("User not found");
        }

        User selected = users.stream()
                .filter(u -> u.getRole() != null && !u.getRole().isBlank())
                .findFirst()
                .orElse(users.get(0));

        return new CustomUserDetails(selected);
    }
}
