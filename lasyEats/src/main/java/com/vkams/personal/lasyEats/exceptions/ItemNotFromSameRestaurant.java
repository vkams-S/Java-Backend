package com.vkams.personal.lasyEats.exceptions;

public class ItemNotFromSameRestaurant extends lasyEatsExceptions{
    @Override
    public int getErrorType() {
        return ITEM_NOT_FROM_SAME_RESTAURANT;
    }
}
