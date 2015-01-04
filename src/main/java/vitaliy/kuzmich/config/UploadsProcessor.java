package vitaliy.kuzmich.config;


import vitaliy.kuzmich.async.SessionMonitor;
import vitaliy.kuzmich.dao.ImgStorage;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.core.env.Environment;
import org.springframework.web.context.WebApplicationContext;

/*@Configuration
@PropertySource("classpath:app.properties")*/
public class UploadsProcessor implements BeanPostProcessor {
    @Autowired
    WebApplicationContext webApplicationContext;

    @Autowired
    Environment environment;

    @Autowired
    ImgStorage storage;


    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        if (bean instanceof SessionMonitor) {
            Const.URL_ROOT = environment.getProperty("url_path");
            Const.PATH_UPLOADS = environment.getProperty("uploads_path");
            storage.clearUploads();
            try {
                webApplicationContext.getServletContext().addListener((SessionMonitor) bean);

            } catch (UnsupportedOperationException ex) {
                ex.printStackTrace();
            }

        }

        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        return bean;
    }
}
