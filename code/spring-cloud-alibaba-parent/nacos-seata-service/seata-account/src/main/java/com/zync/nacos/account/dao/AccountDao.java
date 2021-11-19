package com.zync.nacos.account.dao;

import com.zync.nacos.account.pojo.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

/**
 * 账户服务Dao
 *
 * @author luocong
 * @version v1.0
 * @date 2021/11/17 16:42
 */
public interface AccountDao extends JpaRepository<Account, Long> {

    /**
     * 根据用户ID查询账户
     * @param userId
     * @return
     */
    Account findByUserId(String userId);

    /**
     * 根据ID修改金额
     * @param money
     * @param userId
     * @return
     */
    @Modifying
    @Query("update Account set money = money - ?1 where userId = ?2 and money >= ?1")
    int modifyMoneyById(Long money, String userId);
}
