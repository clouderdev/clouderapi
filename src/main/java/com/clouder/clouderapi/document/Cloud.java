package com.clouder.clouderapi.document;

import org.codehaus.jackson.annotate.JsonIgnore;
import org.codehaus.jackson.annotate.JsonProperty;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Field;

import com.clouder.clouderapi.enums.CloudType;

public abstract class Cloud {

	@Transient
	@JsonIgnore
	protected CloudType cloudType;

	@JsonProperty("cloudType")
	@Field("cloudType")
	protected String cloudTypeText;

	public Cloud(CloudType cloudType) {
		super();
		this.cloudType = cloudType;
		this.cloudTypeText = cloudType.name();
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
		return "Cloud [cloudType=" + cloudType + ", cloudTypeText=" + cloudTypeText + "]";
	}

}
