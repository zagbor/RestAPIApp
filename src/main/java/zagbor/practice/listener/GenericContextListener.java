package zagbor.practice.listener;

import zagbor.practice.CloudStorage.impl.CloudStorageAmazonS3Manager;
import zagbor.practice.db.DBInit;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

@WebListener
public class GenericContextListener implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        DBInit.init();
        CloudStorageAmazonS3Manager.init();
    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {

    }
}
