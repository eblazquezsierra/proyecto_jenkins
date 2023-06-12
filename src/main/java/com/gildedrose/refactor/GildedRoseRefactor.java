package com.gildedrose.refactor;

import com.gildedrose.Item;

public class GildedRoseRefactor {
    public Item[] items;

    public GildedRoseRefactor(Item[] items) {
        this.items = items;
    }

    public void updateQuality() {
        for (int i = 0; i < items.length; i++) {
            items[i].sellIn = items[i].sellIn - 1;
            switch (items[i].name) {
                case "Aged Brie":
                    this.checkAgedBrieItem(items[i]);
                    break;
                case "Backstage passes to a TAFKAL80ETC concert":
                    this.checkBackstagePassesItem(items[i]);
                    break;
                default:
                    this.checkOtherItems(items[i]);
            }
        }
    }

    private void checkAgedBrieItem(Item item) {
        if (item.quality < 50) {
            ++item.quality;
        }
    }

    private void checkBackstagePassesItem(Item item) {
        if (item.sellIn < 0)
            item.quality = 0;
        else if (item.quality < 50 && (item.sellIn < 10 && item.sellIn > 5)) {
            item.quality = item.quality +2;
        } else if (item.quality < 50 && item.sellIn < 5) {
            item.quality = item.quality +3;
        }
    }

    private void checkOtherItems(Item item) {
        if (!item.name.equals("Sulfuras, Hand of Ragnaros") && item.quality > 0) {
            if (item.name.equals("Conjured") || item.sellIn < 0) {
                item.quality = item.quality - 2;
            } else {
                --item.quality;
            }
        }
    }
}
