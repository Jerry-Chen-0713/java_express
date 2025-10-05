package com.yemage.service;

import com.yemage.bean.Express;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * @author yemage
 */
public class ExpressServiceTest {

    @Test
    public void insert() {
        Express express = new Express("868687","你爹","176366026099","顺丰快递","19888888888","1119911");
        ExpressService.insert(express);
    }
}