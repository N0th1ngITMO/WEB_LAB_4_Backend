package ru.kkettch.config;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.internal.bind.DateTypeAdapter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.MediaType;
import org.springframework.http.converter.ByteArrayHttpMessageConverter;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.GsonHttpMessageConverter;
import org.springframework.http.converter.xml.SourceHttpMessageConverter;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.Collections;
import java.util.List;

@Configuration
@ComponentScan("ru.kkettch")
@PropertySource("classpath:application.properties")
@EnableWebMvc
public class AppConfig implements WebMvcConfigurer {
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/api/**")
                .allowedOrigins("http://localhost:5173")
                .allowedMethods("GET", "POST", "PUT", "DELETE")
                .allowCredentials(true).maxAge(3600);
    }

    @Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
        WebMvcConfigurer.super.configureMessageConverters(converters);
        StringHttpMessageConverter stringConverter = new StringHttpMessageConverter();
        stringConverter.setWriteAcceptCharset(false);
        stringConverter.setSupportedMediaTypes(Collections
                .singletonList(MediaType.TEXT_PLAIN));
        converters.add(stringConverter);
        converters.add(new ByteArrayHttpMessageConverter());
        converters.add(new SourceHttpMessageConverter<>());
        GsonHttpMessageConverter gsonHttpMessageConverter = new GsonHttpMessageConverter();
        gsonHttpMessageConverter.setGson(gson());
        gsonHttpMessageConverter.setSupportedMediaTypes(List.of(MediaType.APPLICATION_JSON));
        converters.removeIf(itm -> itm.getSupportedMediaTypes().contains(MediaType.APPLICATION_JSON));
        converters.add(gsonHttpMessageConverter);
    }

    @Bean
    public Gson gson() {
        GsonBuilder b = new GsonBuilder();
        b.registerTypeAdapterFactory(DateTypeAdapter.FACTORY);
        return b.create();
    }
}

