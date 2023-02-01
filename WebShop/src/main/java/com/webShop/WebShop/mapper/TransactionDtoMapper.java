package com.webShop.WebShop.mapper;

import com.webShop.WebShop.dto.TransactionDto;
import com.webShop.WebShop.model.Transaction;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface TransactionDtoMapper {
    @Mapping(source = "id", target = "id")
    @Mapping(source = "services", target = "transactionServices")
    @Mapping(source = "amount", target = "amount")
    @Mapping(source = "merchantOrderTimestamp", target = "merchantOrderTimestamp")
    @Mapping(source = "merchantOrderId", target = "merchantOrderId")
    @Mapping(source = "status", target = "status")
    Transaction fromDtoToTransaction(TransactionDto transaction);

    @Mapping(source = "id", target = "id")
    @Mapping(source = "transactionServices", target = "services")
    @Mapping(source = "amount", target = "amount")
    @Mapping(source = "merchantOrderTimestamp", target = "merchantOrderTimestamp")
    @Mapping(source = "merchantOrderId", target = "merchantOrderId")
    @Mapping(source = "status", target = "status")
    TransactionDto fromTransactionToDto(Transaction transaction);
}
