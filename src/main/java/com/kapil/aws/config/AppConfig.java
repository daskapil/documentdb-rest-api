package com.kapil.aws.config;

import com.mongodb.internal.connection.Cluster;

import java.util.ResourceBundle;

public enum AppConfig {
    DB_CONNECTION_STRING,
    DB_CLUSTER_ENDPOINT,
    DB_CONNECTION_STRING_TEMPLATE,
    DB_USER,
    DB_PASSWORD,
    DB_NAME_DEFAULT,
    DB_READ_PREFERENCE;

    ResourceBundle appConfig = ResourceBundle.getBundle("application");

    public String getValue() {
        return appConfig.getString(name());
    }
}
