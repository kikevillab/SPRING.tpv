package entities.core;

import static org.junit.Assert.assertEquals;

import java.math.BigDecimal;

import org.junit.Test;

public class ShoppingTest {

    @Test
    public void testGetShoppingTotal() {
        Shopping shopping = new Shopping(3, 25, new Article(), "desc", new BigDecimal(100), ShoppingState.COMMITTED);
        assertEquals(225, shopping.getShoppingTotal(), 0.001);
    }

}
