package com.epam.esm.dao;

import com.epam.esm.model.Tag;

import java.util.List;
import java.util.Optional;

public interface TagDao extends CommonDao<Tag> {
    Optional<Tag> findTagByName(String nameTag);
    List<Tag> findTagByPartName(String nameTag);
}