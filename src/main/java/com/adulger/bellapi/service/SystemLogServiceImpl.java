package com.adulger.bellapi.service;

import com.adulger.bellapi.model.SystemLog;
import org.springframework.data.repository.CrudRepository;

/**
 * Created by adulger on 26.12.2021
 **/
public interface SystemLogServiceImpl extends CrudRepository<SystemLog, Long> {

}
