package com.clouder.clouderapi.document;

import org.codehaus.jackson.annotate.JsonIgnore;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "cloud")
public abstract class Cloud {
	@Id
	@JsonIgnore
	protected String id;

	protected String cloudType;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getCloudType() {
		return cloudType;
	}

	public void setCloudType(String cloudType) {
		this.cloudType = cloudType;
	}

	public Cloud(String cloudType) {
		super();
		this.cloudType = cloudType;
	}

	@Override
	public String toString() {
		return "Cloud [id=" + id + ", cloudType=" + cloudType + "]";
	}

}
