package com.vinsguru.userservice.service;

import com.vinsguru.userservice.dto.TransactionRequestDto;
import com.vinsguru.userservice.dto.TransactionResponseDto;
import com.vinsguru.userservice.entity.UserTransaction;
import com.vinsguru.userservice.repository.UserRepository;
import com.vinsguru.userservice.repository.UserTransactionRepository;
import com.vinsguru.userservice.util.EntityDtoUtil;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import static com.vinsguru.userservice.dto.TransactionStatus.APPROVED;
import static com.vinsguru.userservice.dto.TransactionStatus.DECLINED;

@Service
public class TransactionService {

    private final UserRepository userRepository;
    private final UserTransactionRepository userTransactionRepository;

    public TransactionService(UserRepository userRepository, UserTransactionRepository userTransactionRepository) {
        this.userRepository = userRepository;
        this.userTransactionRepository = userTransactionRepository;
    }

    public Mono<TransactionResponseDto> createTransaction(final TransactionRequestDto requestDto) {
        return userRepository
                .updateUserBalance(requestDto.getUserId(), requestDto.getAmount())
                .filter(Boolean::booleanValue)
                .map(b -> EntityDtoUtil.toEntity(requestDto))
                .flatMap(userTransactionRepository::save)
                .map(ut -> EntityDtoUtil.toDto(requestDto, APPROVED))
                .defaultIfEmpty(EntityDtoUtil.toDto(requestDto, DECLINED));
    }

    public Flux<UserTransaction> getByUserId(Integer userId) {
        return userTransactionRepository.findByUserId(userId);
    }
}
