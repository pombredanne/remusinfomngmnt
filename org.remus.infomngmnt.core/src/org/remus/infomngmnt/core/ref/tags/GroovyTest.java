package org.remus.infomngmnt.core.ref.tags;

import groovy.lang.Binding;
import groovy.lang.GroovyShell;

public class GroovyTest {

	/**
	 * @param args
	 */
	public static void main(final String[] args) {
		Binding binding = new Binding();
		binding.setVariable("input", "world");
		GroovyShell gse = new GroovyShell(binding);
		Object evaluate = gse.evaluate("return \"true\"");
		System.out.println(evaluate.getClass());

	}

}
