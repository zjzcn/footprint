package com.footprint.client.log;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

public class LogContextListener implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        LogConfigure.init();
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
    	//NOOP
    }
}
