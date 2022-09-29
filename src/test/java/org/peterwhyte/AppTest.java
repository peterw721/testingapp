package org.peterwhyte;

import org.junit.Test;

import static org.junit.jupiter.api.Assertions.*;

public class AppTest {

    @Test
    public void testGetName() {
        assertAll(() -> assertEquals("Badges", App.getName("Name" )),
                () -> assertEquals("not_found", App.getName("Nme" )));
    }

    @Test
    public void testGetBool() {
        assertAll(() -> assertEquals( App.Status.TRUE, App.getBool("CanRelist" )),
                () -> assertEquals( App.Status.NOT_FOUND, App.getBool("Canrelist" )));
    }

    @Test
    public void testGetArray() {
        assertAll(() -> assertEquals("Better position in category", App.getArray("Promotions",
                        "Name","Feature", "Description" )),
                () -> assertNotNull(App.getArray("Promotions", "Name", "Feature",
                        "Description" )));
    }
}
