package com.vkams.personal.lasyEats.exceptions;

public class CartNotFoundException extends lasyEatsExceptions{
    @Override
    public int getErrorType() {
        return CART_NOT_FOUND;
    }
}
