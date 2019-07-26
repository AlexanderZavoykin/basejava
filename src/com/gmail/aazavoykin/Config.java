package com.gmail.aazavoykin;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

class Config {
    private static final File PROPS = new File(".\\config\\resumes.properties");
    private static final Config INSTANCE = new Config();

    private Properties properties = new Properties();

    private File storageDir;
    private String dbUrl;
    private String dbUser;
    private String dbPassword;

    private Config() {
        try (InputStream is = new FileInputStream(PROPS)) {
            properties.load(is);
            storageDir = new File(properties.getProperty("storage.dir"));
            dbUrl = properties.getProperty("db.url");
            dbUser = properties.getProperty("db.user");
            dbPassword = properties.getProperty("db.password");
        } catch (IOException e) {
            throw new IllegalStateException("Invalid config file: " + PROPS.getAbsolutePath());
        }
    }

    static Config getInstance() {
        return INSTANCE;
    }

    File getStorageDir() {
        return storageDir;
    }

    String getDbUrl() {
        return dbUrl;
    }

    String getDbUser() {
        return dbUser;
    }

    String getDbPassword() {
        return dbPassword;
    }
}