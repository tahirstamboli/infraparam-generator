package com.reantest.infraparam.core;

/**
 * @author tahir
 *
 */
public interface CommandLineParser<T> {
	public T parse(String[] args);
}
