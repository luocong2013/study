package com.zync.swx.demo;

import java.util.ArrayList;
import java.util.List;

/**
 * @author LC
 * @version V1.0
 * @project luoc-hanlp
 * @package com.luo.demo
 * @description TODO
 * @date 2017-10-23 17:30
 */
public class ProductListGenerator {

    private static ProductListGenerator instance = null;

    public static ProductListGenerator getInstance() {
        if (instance == null) {
            instance = new ProductListGenerator();
        }
        return instance;
    }

    public List<Product> generate(int size) {
        List<Product> ret = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            Product product = new Product();
            product.setName("Product"+i);
            product.setPrice(10);
            ret.add(product);
        }
        return ret;
    }
}
