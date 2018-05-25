package be.jstack.ticketing.exception;

import be.jstack.ticketing.service.tenant.TenantService;
import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class NoEngineFoundExceptionTest {

    @Test(expected = NoEngineFoundException.class)
    public void noEngineFoundExceptionTest() {
        TenantService tenantService = new TenantService(null);
        tenantService.stopProcessEngine("engine101");
    }
}