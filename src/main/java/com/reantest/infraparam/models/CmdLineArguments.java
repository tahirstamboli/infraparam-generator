package com.reantest.infraparam.models;

/**
 * @author tahir
 *
 */
public enum CmdLineArguments {
	TF_STATE_PATH("--tfstate-file-path"), INFRAPARAM_OUTPUT_PATH("--infraparam-output-path");

	private final String cmd;

	private CmdLineArguments(String cmd) {
		this.cmd = cmd;
	}

	public String value() {
		return this.cmd;
	}
}
