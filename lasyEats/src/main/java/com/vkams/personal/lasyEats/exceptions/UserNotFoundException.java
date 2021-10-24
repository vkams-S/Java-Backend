package com.vkams.personal.lasyEats.exceptions;

public class UserNotFoundException extends lasyEatsExceptions{
    @Override
    public int getErrorType() {
        return USER_NOT_FOUND;
    }
}
