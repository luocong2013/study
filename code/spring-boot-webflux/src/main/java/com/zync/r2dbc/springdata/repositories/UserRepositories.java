package com.zync.r2dbc.springdata.repositories;

import com.zync.r2dbc.springdata.model.User;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

import java.util.Collection;

/**
 * 用户Repositories
 *
 * @author luocong
 * @version v2.5.0
 * @since 2025/1/8 16:41
 */
@Repository
public interface UserRepositories extends R2dbcRepository<User, Long> {

    /**
     * where id in (xxx) and name like ?
     *
     * @param id
     * @param name
     * @return
     */
    Flux<User> findByIdInAndNameLike(Collection<Long> id, String name);

    @Query("select * from user")
    Flux<User> findCustomAll();

}
