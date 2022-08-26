package com.persoff68.fatodo.builder;

import com.persoff68.fatodo.model.ItemInfo;
import lombok.Builder;

import java.util.UUID;

public class TestItemInfo extends ItemInfo {
    private static final String DEFAULT_VALUE = "test";

    @Builder
    TestItemInfo(UUID id, String title) {
        super();
        super.setId(id);
        super.setTitle(title);
    }

    public static TestItemInfoBuilder defaultBuilder() {
        return TestItemInfo.builder()
                .id(UUID.randomUUID())
                .title(DEFAULT_VALUE);
    }

    public ItemInfo toParent() {
        ItemInfo itemInfo = new ItemInfo();
        itemInfo.setId(getId());
        itemInfo.setTitle(getTitle());
        return itemInfo;
    }

}
