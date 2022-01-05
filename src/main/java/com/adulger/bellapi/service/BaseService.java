package com.adulger.bellapi.service;

import com.adulger.bellapi.model.SystemLog;
import com.adulger.bellapi.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Locale;
import java.util.ResourceBundle;

/**
 * Created by adulger on 26.12.2021
 **/
public class BaseService {

    @Autowired
    private SystemLogServiceImpl systemLogServiceImpl;

    protected User getAuthUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication instanceof UsernamePasswordAuthenticationToken) {
            return (User) authentication.getPrincipal();
        }
        return null;
    }

    protected void logging(SystemLog systemLog){
        systemLogServiceImpl.save(systemLog);
    }

    protected String getBundleString(String key){
        return ResourceBundle.getBundle("messages", Locale.getDefault()).getString(key);
    }
}
