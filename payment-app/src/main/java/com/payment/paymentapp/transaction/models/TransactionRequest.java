package com.payment.paymentapp.transaction.models;

import com.payment.paymentapp.transaction.entity.TransactionType;
import lombok.*;


@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class TransactionRequest {
    private String fromUser;
    private String toUser;
    private TransactionType transactionType;
    private Double transactionAmount;
}
