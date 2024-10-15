package com.shoppingapp.shoppingapp.exceptions;

import lombok.Getter;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;

@Getter
public enum ErrorCode {
    UNCATEGORIZED_EXCEPTION(9999, "Uncategorized error", HttpStatus.INTERNAL_SERVER_ERROR),
    USERNAME_EXISTED(1000, "Username existed", HttpStatus.BAD_REQUEST),
    INVALID_KEY(1001, "Uncategorized error", HttpStatus.BAD_REQUEST),
    USER_EXISTED(1002, "User existed", HttpStatus.BAD_REQUEST),
    USERNAME_INVALID(1003, "Username must be at least 3 characters", HttpStatus.BAD_REQUEST),
    INVALID_PASSWORD(1004, "Password must be at least 8 characters", HttpStatus.BAD_REQUEST),
    USER_NOT_EXISTED(1005, "User not existed", HttpStatus.NOT_FOUND),
    UNAUTHENTICATED(1006, "Unauthenticated", HttpStatus.UNAUTHORIZED),
    UNAUTHORIZED(1007, "You do not have permission", HttpStatus.FORBIDDEN),
    EMAIL_EXISTED(1008, "Email existed", HttpStatus.BAD_REQUEST),

    CATEGORY_EXISTED(1009, "Category existed", HttpStatus.BAD_REQUEST),
    CATEGORY_NOT_EXISTED(1010, "Category not existed", HttpStatus.BAD_REQUEST),
    PRODUCT_EXISTED(1011, "Product existed", HttpStatus.BAD_REQUEST),
    PRODUCT_NOT_EXISTED(1012, "Product not existed", HttpStatus.BAD_REQUEST),
    SHOP_EXISTED(1013, "Shop existed", HttpStatus.BAD_REQUEST),
    SHOP_NOT_EXISTED(1014, "Shop not existed", HttpStatus.BAD_REQUEST),
    INVALID_PASSWORD_CONFIRM(1015, "Password is incorrect", HttpStatus.BAD_REQUEST),
    PRODUCT_IS_NOT_ACTIVE(1016,"PRODUCT IS NOT ACTIVE",HttpStatus.BAD_REQUEST),
    ORDER_NOT_EXISTED(2000,"ORDER NOT EXITED",HttpStatus.BAD_REQUEST),
    USER_NOT_ACTIVE(2001,"You have been banned from our site",HttpStatus.BAD_REQUEST),
    ROLE_NOT_FOUND(2002,"Role not found",HttpStatus.BAD_REQUEST),
    ;

    ErrorCode(int code, String message, HttpStatusCode statusCode) {
        this.code = code;
        this.message = message;
        this.statusCode = statusCode;
    }

    private int code;
    private String message;
    private HttpStatusCode statusCode;
}