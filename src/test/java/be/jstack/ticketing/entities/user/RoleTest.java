package be.jstack.ticketing.entities.user;

import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.Assert.assertEquals;

@SpringBootTest
public class RoleTest {

    @Test
    public void createAndReadRoleTest() {
        Role role = new Role();
        role.setId(0L);
        role.setRole("Administrator");

        assertEquals(Long.valueOf(0), role.getId());
        assertEquals("Administrator", role.getRole());
    }

}