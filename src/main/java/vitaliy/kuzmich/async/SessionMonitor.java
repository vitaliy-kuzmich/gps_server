package vitaliy.kuzmich.async;

import vitaliy.kuzmich.config.Const;
import vitaliy.kuzmich.dao.ImgStorage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

@Component

public class SessionMonitor implements HttpSessionListener {

    @Autowired
    ImgStorage storage;


    public void sessionCreated(HttpSessionEvent sessionEvent) {
        sessionEvent.getSession().setAttribute(Const.KEY_ROOT_PATH, Const.URL_ROOT);
    }

    public void sessionDestroyed(HttpSessionEvent sessionEvent) {
        storage.clearUploads();
    }

}