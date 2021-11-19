package com.zync.nacos.storage.dao;

import com.zync.nacos.storage.pojo.Storage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

/**
 * 库存Dao
 *
 * @author luocong
 * @version v1.0
 * @date 2021/11/17 11:37
 */
public interface StorageDao extends JpaRepository<Storage, Long> {

    /**
     * 根据ID修改数量
     * @param amount
     * @param id
     * @return
     */
    @Modifying
    @Query("update Storage set num = num - ?1 where id = ?2 and num >= ?1")
    int modifyNumById(Long amount, Long id);
}
