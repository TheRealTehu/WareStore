package com.codecool.WareStoreProject.unit;

import com.codecool.WareStoreProject.repositry.jpa.WarehouseJPARepository;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
public class WarehouseServiceTest {

   @MockBean
   WarehouseJPARepository mockRepository;


}
