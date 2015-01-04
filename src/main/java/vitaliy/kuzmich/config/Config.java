package vitaliy.kuzmich.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.servlet.config.annotation.DefaultServletHandlerConfigurer;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter;
import org.springframework.web.servlet.mvc.support.ControllerClassNameHandlerMapping;

import java.util.ArrayList;
import java.util.List;


@Configuration
@ComponentScan("vitaliy.")
@EnableWebMvc
public class Config extends WebMvcConfigurerAdapter {
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/**")
                .addResourceLocations("/");
    }

    /*  @Bean
      public UrlBasedViewResolver viewResolver() {
          InternalResourceViewResolver resolver = new InternalResourceViewResolver();
          resolver.setPrefix(Const.PATH_VIEWS);
          resolver.setSuffix(Const.SUFFIX_VIEWS);
          resolver.setViewClass(JstlView.class);
          return resolver;
      }
  */
    @Bean
    public CommonsMultipartResolver multipartResolver() {
        CommonsMultipartResolver res = new CommonsMultipartResolver();
        res.setMaxUploadSize(Const.SIZE_UPLOADS_MAX);
        return res;
    }

    @Override
    public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer) {
        configurer.enable();
    }

    @Bean
    public RequestMappingHandlerAdapter converterAdapter() {
        RequestMappingHandlerAdapter res = new RequestMappingHandlerAdapter();
        List<HttpMessageConverter<?>> l = new ArrayList<>();
        l.add(new MappingJackson2HttpMessageConverter());
        res.setMessageConverters(l);
        return res;
    }


    @Bean
    public ControllerClassNameHandlerMapping controllerClassNameHandlerMapping() {
        ControllerClassNameHandlerMapping res = new ControllerClassNameHandlerMapping();
        LocaleChangeInterceptor interceptor = new LocaleChangeInterceptor();
        res.setInterceptors(new Object[]{interceptor});
        return res;
    }

    @Bean
    public ReloadableResourceBundleMessageSource bundle() {
        ReloadableResourceBundleMessageSource res = new ReloadableResourceBundleMessageSource();
        res.setBasename("classpath:messages");
        return res;
    }

}  
