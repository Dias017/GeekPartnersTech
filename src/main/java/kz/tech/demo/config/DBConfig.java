package kz.tech.demo.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;
import java.sql.*;

@Slf4j
@Service
public class DBConfig {

    @Value(value = "${spring.datasource.url}")
    private String url;
    @Value(value = "${spring.datasource.database}")
    private String database;
    @Value(value = "${spring.datasource.username}")
    private String userName;
    @Value(value = "${spring.datasource.password}")
    private String password;
    private DataSource dataSource;

    private void checkAccessDriver() throws Exception {
        try (Connection cnt = DriverManager.getConnection(url, userName, password)) {
            if (cnt == null) throw new SQLException();
        }
    }

    private boolean isExistDataBase() throws Exception {
        try (Connection cnt = DriverManager.getConnection(url, userName, password);
             Statement statement = cnt.createStatement()) {
            String sql = "SELECT datname FROM pg_catalog.pg_database WHERE datname='" + database + "'";
            ResultSet resultSet = statement.executeQuery(sql);
            while (resultSet.next()) {
                if (resultSet.getString(1).equals(database)) {
                    log.info("isExistDataBase result: ", resultSet);
                    try (Connection cnt1 = DriverManager.getConnection(url, userName, password)) {
                        log.info("Try to get connection with path: " + url + " userName:  " + userName + ", password: " + password);
                        return true;
                    }
                }
            }
        } catch (Exception e) {
            log.error("Не удалось подключится к БД, для проверки наличия базы данных.", e);
        }
        return false;
    }

    private void instanceDB() throws Exception {
        try (Connection cnt = DriverManager.getConnection(url, userName, password);
             Statement statement = cnt.createStatement()) {
            statement.execute("CREATE DATABASE " + database);
            try (Connection cnt1 = DriverManager.getConnection(url, userName, password)) {
                if (cnt1 == null)
                    throw new Exception("Не удалось установить соединение с вновь созданной базой данных");
            }
        }
    }

    private void instanceDataSource() {
        try {
            checkAccessDriver();
            log.info("Драйвер - ОК!");
        } catch (Exception e) {
            log.error("Драйвер недоступен \n", e);
            return;
        }
        log.info("Проверка наличия базы данных " + database + " Path+dbname: " + url);
        try {
            if (isExistDataBase())
                log.info("Подключение прошло успешно");
            else {
                log.warn("База данных не найдена. Создание новой...");
                try {
                    instanceDB();
                    log.info("База создана. Соединение прошло успешно");
                } catch (Exception e1) {
                    log.info("Не удалось создать базу данных или получить к ней доступ");
                }
            }
        } catch (Exception e) {
            log.error("Не удалось проверить наличие базы данных.", e);
        }

        dataSource = new DriverManagerDataSource(url, userName, password);

    }

    public DataSource getDataSource() {
        if (dataSource == null) {
            instanceDataSource();
        }
        return dataSource;
    }

}
