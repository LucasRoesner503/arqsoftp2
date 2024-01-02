package com.arqsoft.project2.acmeAggregatedRatings.property;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "file")
public class FileStorageProperties {

    private String uploadDir;


    public String getUploadDir() {
        return uploadDir;
    }

    public void setUploadDir( final String uploadDir) {
        this.uploadDir = uploadDir;
    }
}
