package treemas.backend.Service;

import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;

@Configuration
public class JpaConfig {
     @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactory(
        DataSource dataSource) {
        LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();
        em.setDataSource(dataSource);
        em.setPackagesToScan("treemas.backend.Model"); // Set your entity package here
        em.setJpaVendorAdapter(new HibernateJpaVendorAdapter());
        return em;
}
}
