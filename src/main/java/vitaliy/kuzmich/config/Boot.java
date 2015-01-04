package vitaliy.kuzmich.config;

import vitaliy.kuzmich.dao.UsersDao;
import org.springframework.core.annotation.Order;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

@Order(1)
public class Boot extends
        AbstractAnnotationConfigDispatcherServletInitializer {
  /*  public void onStartup(ServletContext servletContext) throws ServletException {

        AnnotationConfigWebApplicationContext ctx = new AnnotationConfigWebApplicationContext();
        ctx.register(Config.class);

        ctx.getEnvironment().setActiveProfiles("openshift");
        ServletRegistration.Dynamic servlet = servletContext.addServlet("dispatcher", new DispatcherServlet(ctx));
        servlet.addMapping("/");

        servlet.setLoadOnStartup(1);

        ctx.setServletContext(servletContext);
        super.onStartup(servletContext);

    }*/

    @Override
    protected String[] getServletMappings() {
        return new String[] { "/" };
    }

    @Override
    protected Class<?>[] getRootConfigClasses() {
        return new Class[]{Config.class, UsersDao.class};
    }

    @Override
    protected Class<?>[] getServletConfigClasses() {
        return null;
    }
}
