package cjy.service;

import cjy.bean.Express;
import org.junit.Test;

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