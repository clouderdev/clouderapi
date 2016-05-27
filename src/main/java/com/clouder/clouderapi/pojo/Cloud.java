package com.clouder.clouderapi.pojo;

import org.codehaus.jackson.annotate.JsonIgnore;
import org.codehaus.jackson.annotate.JsonProperty;
import org.codehaus.jackson.annotate.JsonSubTypes;
import org.codehaus.jackson.annotate.JsonSubTypes.Type;
import org.codehaus.jackson.annotate.JsonTypeInfo;
import org.codehaus.jackson.annotate.JsonTypeInfo.As;
import org.codehaus.jackson.annotate.JsonTypeInfo.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Field;

import com.clouder.clouderapi.enums.CloudType;

@JsonTypeInfo(use = Id.NAME, include = As.PROPERTY, property = "cloudType")
@JsonSubTypes({ @Type(value = GoogleDrive.class, name = "GOOGLEDRIVE"), @Type(value = DropBox.class, name = "DROPBOX"),
    @Type(value = OneDrive.class, name = "ONEDRIVE") })
public abstract class Cloud {

    @Transient
    @JsonIgnore
    private CloudType cloudType;

    @JsonProperty("cloudType")
    @Field("cloudType")
    private String cloudTypeText;

    private String email;

    public Cloud() {
    }

    public Cloud(CloudType cloudType) {
        super();
        this.cloudType = cloudType;
        this.cloudTypeText = cloudType.name();
    }

    public Cloud(CloudType cloudType, String email) {
        super();
        this.email = email;
        this.cloudType = cloudType;
        this.cloudTypeText = cloudType.name();
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public CloudType getCloudType() {
        return cloudType;
    }

    public void setCloudType(CloudType cloudType) {
        this.cloudType = cloudType;
        this.cloudTypeText = cloudType.name();
    }

    public String getCloudTypeText() {
        return cloudTypeText;
    }

    public void setCloudTypeText(String cloudTypeText) {
        this.cloudTypeText = cloudTypeText;
        this.cloudType = CloudType.valueOf(cloudTypeText);
    }

    @Override
    public String toString() {
        return "Cloud [cloudType=" + cloudType + ", cloudTypeText=" + cloudTypeText + ", email=" + email + "]";
    }

}
