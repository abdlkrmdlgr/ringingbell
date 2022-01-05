package com.adulger.bellapi.service;

import com.adulger.bellapi.model.Bell;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by adulger on 25.12.2021
 **/
@EnableJpaRepositories
public interface BellServiceImpl extends CrudRepository<Bell, Long> {
    @Transactional
    @Modifying
    @Query("Update Bell set notificationEnabled = :status where bellUniqueId = :bellUniqueId and user.id = :userId")
    void changeNotificationStatus(String bellUniqueId, boolean status, Long userId);

    @Transactional
    @Modifying
    @Query("Update Bell set notificationEnabled = :status where bellUniqueId = :bellUniqueId")
    void changeNotificationStatus(String bellUniqueId, boolean status);

    @Query("SELECT b from Bell b join b.user u where u.id = :userId and b.bellUniqueId= :bellUniqueId")
    Bell getBellBy(Long userId, String bellUniqueId);

    @Query("SELECT b from Bell b join b.user u where b.bellUniqueId = :bellUniqueId and u.id = :userId")
    Bell findByUniqueId(String bellUniqueId, Long userId);

    @Transactional
    @Modifying
    @Query("delete from Bell b where b.bellUniqueId = :bellUniqueId and b.user.id = :userId")
    void deleteUserBell(String bellUniqueId, Long userId);

}
