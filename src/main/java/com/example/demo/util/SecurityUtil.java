package com.example.demo.util;

import org.springframework.stereotype.Component;

@Component
public class SecurityUtil {
    public static final String JWT_ALGORITHM = "HS256"; // Giữ lại nếu cần

    // Constructor mặc định (không cần tham số)
    public SecurityUtil() {
    }

    // Nếu không cần tạo token nữa, xóa hoặc thay bằng logic khác
    public String someUtilityMethod() {
        return "example";
    }
}