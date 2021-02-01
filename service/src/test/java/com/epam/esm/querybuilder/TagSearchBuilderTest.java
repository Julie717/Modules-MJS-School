package com.epam.esm.querybuilder;

import com.epam.esm.model.TagDto;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TagSearchBuilderTest {
  /*  @Test
    public void buildQueryToSearchTags() {
        List<TagDto> tagsDto = new ArrayList<>();
        TagDto tag = new TagDto();
        tag.setName("gift");
        tagsDto.add(tag);
        tag = new TagDto();
        tag.setName("beautiful");
        tagsDto.add(tag);
        tag = new TagDto();
        tag.setName("wonderful");
        tagsDto.add(tag);
        String expected = "('gift', 'beautiful', 'wonderful')";

        String actual = TagSearchBuilder.buildQueryToSearchTags(tagsDto);

        assertEquals(expected, actual);
    }*/
}