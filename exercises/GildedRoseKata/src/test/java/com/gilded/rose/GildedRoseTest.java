package com.gilded.rose;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class GildedRoseTest {
    @Test
    void sanity() {
        Item[] items = new Item[] { new Item("Thing", 0, 0) };
        GildedRose app = new GildedRose(items);
        app.updateQuality();
        assertEquals("Thing", app.items[0].name);
    }

}
