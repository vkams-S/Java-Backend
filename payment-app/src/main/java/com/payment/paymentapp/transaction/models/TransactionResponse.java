package com.payment.paymentapp.transaction.models;

import com.payment.paymentapp.transaction.entity.TransactionStatus;
import lombok.*;


@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class TransactionResponse {
    private String transactionId;
    private TransactionStatus status;
    private Double transactionAmount;
}
