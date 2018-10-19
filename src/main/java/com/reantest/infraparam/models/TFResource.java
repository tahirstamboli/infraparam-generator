package com.reantest.infraparam.models;

import java.util.HashMap;
import java.util.Map;

public class TFResource {
	String name;
	String type;
	String id;
	Map<String, String> tags;

	Map<String, Object> attributes;

	String status = "NOT_STARTED";

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Map<String, String> getTags() {
		return tags;
	}

	public void setTags(Map<String, String> tags) {
		this.tags = tags;
	}

	public void addTag(String key, String value) {
		if (tags == null) {
			tags = new HashMap<String, String>();
		}
		tags.put(key, value);
	}

	public void addTag(String key) {
		if (tags == null) {
			tags = new HashMap<String, String>();
		}
		tags.put(key, null);
	}

	public Map<String, Object> getAttributes() {
		return attributes;
	}

	public void setAttributes(Map<String, Object> attributes) {
		this.attributes = attributes;
	}

	public void addAttribute(String key, Object value) {
		if (attributes == null) {
			attributes = new HashMap<String, Object>();
		}
		attributes.put(key, value);
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	@Override
	public String toString() {
		return "TFService : TFResource{" + "name = " + name + ",type = " + type + "id = " + id + "status = " + status
				+ "tags = " + tags + "otherAttributes = " + attributes + "}";
	}

}
