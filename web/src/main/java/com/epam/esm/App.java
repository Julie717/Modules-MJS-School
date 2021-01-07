package com.epam.esm;

import com.epam.esm.dao.TagDao;
import com.epam.esm.dao.connection.ApplicationConfig;
import com.epam.esm.dao.impl.TagDaoImpl;
import com.epam.esm.model.Tag;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.jdbc.core.JdbcTemplate;


import java.util.List;

public class App {
    public static void main(String[] args) {
        AnnotationConfigApplicationContext context=new AnnotationConfigApplicationContext(ApplicationConfig.class);
        TagDao tagDao = new TagDaoImpl(context.getBean(JdbcTemplate.class));
        Tag tag=new Tag();
        tag.setIdTag(2);
        tag.setName("makes me fun");
        tagDao.deleteById(2);
        System.out.println("---------------");
        List<Tag> tags = tagDao.findAll();
         tags.stream().forEach(s-> System.out.println(s));
        System.out.println("++++++++++++++++");
        System.out.println(tagDao.findById(2));
    }
}