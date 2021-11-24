package com.vinsguru.orderservice.service;

import com.vinsguru.orderservice.dto.PurchaseOrderResponseDto;
import com.vinsguru.orderservice.repository.PurchaseOrderRepository;
import com.vinsguru.orderservice.util.EntityDtoUtil;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.scheduler.Schedulers;

@Service
public class OrderQueryService {

    private final PurchaseOrderRepository purchaseOrderRepository;

    public OrderQueryService(PurchaseOrderRepository purchaseOrderRepository) {
        this.purchaseOrderRepository = purchaseOrderRepository;
    }

    public Flux<PurchaseOrderResponseDto> getProductsByUserId(Integer userId) {
        return Flux.fromStream(() -> purchaseOrderRepository.findByUserId(userId).stream()) //blocking
                .map(EntityDtoUtil::getPurchaseOrderResponseDto)
                .subscribeOn(Schedulers.boundedElastic());
    }
}
