package king.ide.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("http://43.203.196.157:3000", "http://localhost:3000") // 허용할
                // Origin
                .allowedMethods("GET", "POST", "PUT", "DELETE").allowedHeaders("*")
                .exposedHeaders("Authorization"); // 허용할
        // HTTP
        // 메서드
    }
}