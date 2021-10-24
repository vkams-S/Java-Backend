package com.vkams.personal.lasyEats.exceptions;

public class ItemNotFoundInRestaurantException extends lasyEatsExceptions{
    public ItemNotFoundInRestaurantException(String message)
    {
         super(message);
    }
    @Override
    public int getErrorType() {
        return ITEM_NOT_FOUND_IN_RETAURANT_MENU;
    }
}
