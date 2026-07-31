package com.nhom2oop.dangkymonhoc.service;

import com.nhom2oop.dangkymonhoc.model.User;
import com.nhom2oop.dangkymonhoc.repository.UserRepository;
import com.nhom2oop.dangkymonhoc.utils.PasswordUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    @Autowired
    private UserRepository userRepository;

    /**
     * Đăng nhập bằng username và mật khẩu.
     * @return User nếu thành công, ngược lại trả về null.
     */
    public User login(String username, String password) {

        User user = userRepository.findByUsername(username);

        if (user == null) {
            return null;
        }

        return user.login(password) ? user : null;
    }

    /**
     * Mã hóa mật khẩu trước khi lưu.
     */
    public String hashPassword(String rawPassword) {
        return PasswordUtils.hash(rawPassword);
    }
}