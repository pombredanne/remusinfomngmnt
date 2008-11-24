package org.remus.infomngmnt.core.extension;

public interface IRule {

	String getId();

	String getName();

	AbstractRuleDefinition getRuleDefinition();
}
