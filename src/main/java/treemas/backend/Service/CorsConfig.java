package treemas.backend.Service;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableWebMvc
public class CorsConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        // Defining CORS rules
        registry.addMapping("/api/**") // Restrict CORS to specific paths
                .allowedOrigins("*") // Android's domain
                .allowedMethods("GET", "POST", "PUT", "DELETE") // Allowed HTTP methods
                .allowCredentials(true) // Allow credentials (e.g., cookies)
                .maxAge(3600); // Max age of the CORS preflight request
    }
}
