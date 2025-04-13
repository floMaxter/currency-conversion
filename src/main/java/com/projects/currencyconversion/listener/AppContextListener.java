package com.projects.currencyconversion.listener;

import com.projects.currencyconversion.Utils.ConnectionManager;
import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;

@WebListener
public class AppContextListener implements ServletContextListener {

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        ConnectionManager.closePool();
        System.out.println("Connection pool closed");
    }
}
