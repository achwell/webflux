package com.vinsguru.userservice.util;

import com.vinsguru.userservice.dto.TransactionRequestDto;
import com.vinsguru.userservice.dto.TransactionResponseDto;
import com.vinsguru.userservice.dto.TransactionStatus;
import com.vinsguru.userservice.dto.UserDto;
import com.vinsguru.userservice.entity.User;
import com.vinsguru.userservice.entity.UserTransaction;
import org.springframework.beans.BeanUtils;

import static java.time.LocalDateTime.now;

public class EntityDtoUtil {

    public static UserDto toDto(User user) {
        final UserDto dto = new UserDto();
        BeanUtils.copyProperties(user, dto);
        return dto;
    }

    public static User toEntity(UserDto dto) {
        final User user = new User();
        BeanUtils.copyProperties(dto, user);
        return user;
    }

    public static UserTransaction toEntity(TransactionRequestDto dto) {
        UserTransaction userTransaction = new UserTransaction();
        userTransaction.setUserId(dto.getUserId());
        userTransaction.setAmount(dto.getAmount());
        userTransaction.setTransactionDate(now());
        return userTransaction;
    }

    public static TransactionResponseDto toDto(TransactionRequestDto dto, TransactionStatus status) {
        final TransactionResponseDto transactionResponseDto = new TransactionResponseDto();
        transactionResponseDto.setUserId(dto.getUserId());
        transactionResponseDto.setAmount(dto.getAmount());
        transactionResponseDto.setStatus(status);
        return transactionResponseDto;
    }
}
