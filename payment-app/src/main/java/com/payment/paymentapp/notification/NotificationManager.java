package com.payment.paymentapp.notification;

public interface NotificationManager {
    void sendOnTransaction(String event);
    void sendOnWallet(String event);
    void sendOnUser(String event);
    void sendOnRollback(String event);
}
