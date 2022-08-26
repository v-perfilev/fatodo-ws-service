package com.persoff68.fatodo.builder;

import com.persoff68.fatodo.model.event.ItemGroup;
import lombok.Builder;

import java.util.UUID;

public class TestItemGroup extends ItemGroup {

    private static final String DEFAULT_VALUE = "test";

    @Builder
    TestItemGroup(UUID id, String title) {
        super();
        super.setId(id);
        super.setTitle(title);
    }

    public static TestItemGroupBuilder defaultBuilder() {
        return TestItemGroup.builder()
                .id(UUID.randomUUID())
                .title(DEFAULT_VALUE);
    }

    public ItemGroup toParent() {
        ItemGroup itemGroup = new ItemGroup();
        itemGroup.setId(getId());
        itemGroup.setTitle(getTitle());
        return itemGroup;
    }

}
