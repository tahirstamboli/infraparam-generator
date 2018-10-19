package com.reantest.infraparam.models;

import java.util.Map;

public class InfraParam {
	private Map<String, Map<String, Map<String, Object>>> input;
	private Map<String, Map<String, String>> output;

	public InfraParam(Map<String, Map<String, Map<String, Object>>> input, Map<String, Map<String, String>> output) {
		super();
		this.input = input;
		this.output = output;
	}

	public Map<String, Map<String, Map<String, Object>>> getInput() {
		return input;
	}

	public void setInput(Map<String, Map<String, Map<String, Object>>> input) {
		this.input = input;
	}

	public Map<String, Map<String, String>> getOutput() {
		return output;
	}

	public void setOutput(Map<String, Map<String, String>> output) {
		this.output = output;
	}

}
