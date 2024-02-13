package com.storeorderingsystem.storeorderingsystem.service.impl;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;
import org.springframework.web.client.RestTemplate;

import com.storeorderingsystem.storeorderingsystem.model.Bill;
import com.storeorderingsystem.storeorderingsystem.repository.BillRepository;
import com.storeorderingsystem.storeorderingsystem.repository.ItemQuantityRepository;
import com.storeorderingsystem.storeorderingsystem.repository.StoreUserRepository;
import com.storeorderingsystem.storeorderingsystem.service.OrderServiceImpl;

public class OrderServiceImplTest {
    private final static String ORDER_ID = "12345";
    private final static int ORDER_TOTAL_PRICE = 58;
    private final static String ORDER_COMPLETE_UPDATER = "http://order-complete-updater";

    private RestTemplate restTemplate;
    private ItemQuantityRepository itemRepository;
    private BillRepository billRepository;
    private OrderServiceImpl orderService;
    private com.storeorderingsystem.storeorderingsystem.repository.Bill bill;

    @Before
    public void setMock() {
        restTemplate = mock(RestTemplate.class);
        billRepository = mock(BillRepository.class);
        itemRepository = mock(ItemQuantityRepository.class);
        orderService = new OrderServiceImpl(restTemplate, billRepository, itemRepository);

        Bill bill = new Bill();
    }

    @Test
    public void processPaymentSuccess() {
        when(billRepository.save(bill)).thenReturn(bill);
        //billRepository.processBill(bill);
       // verify(restTemplate).postForLocation(ORDER_COMPLETE_UPDATER + "/api/orders", bill);

    }
}
