package com.gildedrose;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class GlidedRoseFullFunctionalityTest {

    @Test
    public void shouldHaveSellInAndQualityValuesTest() {
        Item[] items = new Item[] { new Item("foo", 0, 0) };
        GildedRose app = new GildedRose(items);
        app.updateQuality();
        assertNotNull(app.items[0].sellIn);
        assertNotNull(app.items[0].quality);
    }

    @Test
    public void atTheEndOfTheDayShouldLowerSellInAndQualityValuesTest() {
        Item[] items = new Item[] { new Item("foo", 1, 1) };
        GildedRose app = new GildedRose(items);
        app.updateQuality();
        assertEquals(0, app.items[0].sellIn);
        assertEquals(0, app.items[0].quality);
    }

    @Test
    public void whenSellByDateHasPassedQualityDegradeTwiceAsFastTest() {
        Item[] items = new Item[] { new Item("foo", -1, 2) };
        GildedRose app = new GildedRose(items);
        app.updateQuality();
        assertEquals(-2, app.items[0].sellIn);
        assertEquals(0, app.items[0].quality);
    }

    @Test
    public void qualityShouldNeverBeNegativeTest() {
        Item[] items = new Item[] { new Item("foo", 1, 0) };
        GildedRose app = new GildedRose(items);
        app.updateQuality();
        assertEquals(0, app.items[0].sellIn);
        assertEquals(0, app.items[0].quality);
    }

    @Test
    public void agedBrieShouldIncreaseQualityWhenGetsOlderTest() {
        Item[] items = new Item[] { new Item("Aged Brie", 1, 2) };
        GildedRose app = new GildedRose(items);
        app.updateQuality();
        assertEquals(0, app.items[0].sellIn);
        assertEquals(3, app.items[0].quality);
    }

    @Test
    public void qualityShouldNeverBeMoreThan50Test() {
        Item[] items = new Item[] { new Item("Aged Brie", 1, 50) };
        GildedRose app = new GildedRose(items);
        app.updateQuality();
        assertEquals(0, app.items[0].sellIn);
        assertEquals(50, app.items[0].quality);
    }

    @Test
    public void sulfurasNeverHasToBeSoldOrDecreasesInQualityTest() {
        Item[] items = new Item[] {
            new Item("Sulfuras, Hand of Ragnaros", 1, 15),
            new Item("Sulfuras, Hand of Ragnaros", -1, 15)
        };
        GildedRose app = new GildedRose(items);
        app.updateQuality();
        assertEquals(15, app.items[0].quality);
        assertEquals(15, app.items[1].quality);
    }

    @Test
    /* “Backstage passes”, like aged brie, increases in Quality as it’s SellIn value approaches;
        Quality increases by 2 when there are 10 days or less and by 3 when there are 5 days or less but Quality
        drops to 0 after the concert */
    public void backStagePassesFeatureTest() {
        Item[] items = new Item[] {
            new Item("Backstage passes to a TAFKAL80ETC concert", 10, 15),
            new Item("Backstage passes to a TAFKAL80ETC concert", 5, 15),
            new Item("Backstage passes to a TAFKAL80ETC concert", -1, 15)
        };
        GildedRose app = new GildedRose(items);
        app.updateQuality();
        assertEquals(17, app.items[0].quality);
        assertEquals(18, app.items[1].quality);
        assertEquals(0, app.items[2].quality);
    }
}
