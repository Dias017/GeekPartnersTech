package kz.tech.demo.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

@Configuration
public class AppConfiguration {
    @Autowired
    private DBConfig dbConfig;

    @Bean(name = "dataSource")
    @ConditionalOnMissingBean
    public DataSource initDB() {
        return dbConfig.getDataSource();
    }
}
