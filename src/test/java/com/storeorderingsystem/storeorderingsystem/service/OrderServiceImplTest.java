package com.storeorderingsystem.storeorderingsystem.service;

import static org.mockito.Mockito.mock;

import java.util.ArrayList;
import java.util.List;

import org.assertj.core.api.Assertions;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.storeorderingsystem.storeorderingsystem.model.BillAmount;
import com.storeorderingsystem.storeorderingsystem.model.ItemQuantity;
import com.storeorderingsystem.storeorderingsystem.repository.BillRepository;
import com.storeorderingsystem.storeorderingsystem.repository.ItemQuantityRepository;
import com.storeorderingsystem.storeorderingsystem.repository.StoreUserRepository;
import com.storeorderingsystem.storeorderingsystem.util.Constants;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class OrderServiceImplTest {
	
	 @Autowired MockMvc mockMvc;
    
	@MockBean
    private ItemQuantityRepository itemRepository;
    
	@MockBean
	private BillRepository billRepository;
    
	@MockBean
	private StoreUserRepository storeUserRepository;
    
    @Autowired
    private OrderServiceImpl orderService;
  //@MockBean
    
    private final String URI = "http://localhost:8080/api/generateBillAmount";
    private com.storeorderingsystem.storeorderingsystem.model.Bill bill;
    private List<ItemQuantity> itemList;
    private BillAmount billAmount;    

    @Before
    public void setMock() {
    	billRepository = mock(BillRepository.class);
    	itemRepository = mock(ItemQuantityRepository.class);
    	storeUserRepository = mock(StoreUserRepository.class);
    	orderService = new OrderServiceImpl(billRepository, itemRepository, storeUserRepository);
    }

   private ItemQuantity createItemQuantityModel(String name, int pric, int quantity, String itemType) {

	   ItemQuantity item = new ItemQuantity();
	   item.setName("Bread");
	   item.setPrice(20);
	   item.setQuantity(5);
	   item.setType(Constants.ITEM_TYPE_GROCERIES);
	   return item;
   }
        
    @Test
    public void processPaymentSuccess() {
    	
        bill = createBillModel("123", "1", 15022024L);
        itemList = new ArrayList<ItemQuantity>();
        itemList.add( createItemQuantityModel("Bread", 11, 2, Constants.ITEM_TYPE_GROCERIES));
        itemList.add( createItemQuantityModel("Tissue papper box", 16, 6, Constants.ITEM_TYPE_STATIONARY));
        itemList.add( createItemQuantityModel("Eggs", 5, 2, Constants.ITEM_TYPE_GROCERIES));
        bill.setItems(itemList);
        
    	BillAmount billAmount = orderService.processBill(bill);
    	 Assertions.assertThat(billAmount).isNotNull();
         Assertions.assertThat(billAmount instanceof BillAmount);
         BillAmount reponseBillAmount = (BillAmount) billAmount;
         Assertions.assertThat(reponseBillAmount).isNotNull();
    }

	private com.storeorderingsystem.storeorderingsystem.model.Bill createBillModel(String billId, String storeUserId, long date) {
		bill = new com.storeorderingsystem.storeorderingsystem.model.Bill();
        bill.setBillId(billId);
        bill.setStoreUserId(storeUserId);
        bill.setCreatedDate(date);     
        return bill;
	}
}
