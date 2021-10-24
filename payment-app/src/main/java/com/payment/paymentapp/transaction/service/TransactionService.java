package com.payment.paymentapp.transaction.service;

import com.payment.paymentapp.exception.NotFoundException;
import com.payment.paymentapp.transaction.models.TransactionRequest;
import com.payment.paymentapp.transaction.models.TransactionResponse;

public interface TransactionService {
    public TransactionResponse create(TransactionRequest transactionRequest);
    public TransactionResponse get(String txId) throws NotFoundException;
    public void rollback(String rollbackRequest);
    public void update(String updateRequest);

}
