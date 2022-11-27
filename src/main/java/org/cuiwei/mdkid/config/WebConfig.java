package org.cuiwei.mdkid.config;

import org.cuiwei.mdkid.enumeration.PhotoScale;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;
import org.springframework.core.convert.converter.ConverterFactory;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableWebMvc
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**");
    }

    @Override
    public void addFormatters(FormatterRegistry registry) {
        registry.addConverterFactory(new ConverterFactory<String, PhotoScale>() {
            @Override
            public <T extends PhotoScale> Converter<String, T> getConverter(Class<T> targetType) {
                T[] enums = targetType.getEnumConstants();
                return source -> {
                    for (T e : enums) {
                        if (e.getScale().equals(source)) {
                            return e;
                        }
                    }
                    throw new IllegalArgumentException("参数错误");
                };
            }
        });
    }

}
