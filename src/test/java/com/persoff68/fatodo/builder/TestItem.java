package com.persoff68.fatodo.builder;

import com.persoff68.fatodo.model.event.Item;
import lombok.Builder;

import java.util.UUID;

public class TestItem extends Item {

    private static final String DEFAULT_VALUE = "test";

    @Builder
    TestItem(UUID id, String title) {
        super();
        super.setId(id);
        super.setTitle(title);
    }

    public static TestItemBuilder defaultBuilder() {
        return TestItem.builder()
                .id(UUID.randomUUID())
                .title(DEFAULT_VALUE);
    }

    public Item toParent() {
        Item item = new Item();
        item.setId(getId());
        item.setTitle(getTitle());
        return item;
    }

}
