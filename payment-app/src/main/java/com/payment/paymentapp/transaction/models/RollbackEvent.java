package com.payment.paymentapp.transaction.models;

import lombok.*;


@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class RollbackEvent {
    private String fromUser;
    private String toUser;
    private Double transactionAmount;
    private String transactionId;
    private String reason;
}
