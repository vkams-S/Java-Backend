package com.payment.paymentapp.transaction.repository;

import com.payment.paymentapp.transaction.entity.Transaction;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TransactionRepository extends CrudRepository<Transaction, Long> {
    Optional<Transaction> findByTxId(String transactionId);
}
