package com.vkams.personal.lasyEats.exceptions;

public class EmptyCartException extends lasyEatsExceptions{
    @Override
    public int getErrorType() {
        return EMPTY_CART;
    }
}
