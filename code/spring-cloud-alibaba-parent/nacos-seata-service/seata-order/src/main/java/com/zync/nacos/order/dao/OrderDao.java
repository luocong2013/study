package com.zync.nacos.order.dao;

import com.zync.nacos.order.pojo.Order;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * 订单服务Dao
 *
 * @author luocong
 * @version v1.0
 * @date 2021/11/17 17:23
 */
public interface OrderDao extends JpaRepository<Order, Long> {
}
