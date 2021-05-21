package com.leyou.client;

import com.leyou.pojo.Category;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest
class CategoryClientTest {

    @Autowired
    private CategoryClient categoryClient;

    @Test
    void findCategoryByPid() {
        List<Long> categories = new ArrayList<>();
        categories.add(1L);
        categories.add(2L);
        categories.add(3L);
        List<Category> categoryList = categoryClient.findCategoryByPid(categories);
        Assert.assertEquals(3,categoryList.size());
    }
}