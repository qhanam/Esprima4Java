package esprima4java.ast;

import com.google.auto.value.AutoValue;

import esprima4java.cfg.Cfg;
import esprima4java.cfg.CfgBuilderForStatements;

@AutoValue
public abstract class EmptyStatement extends Node {
    public static EmptyStatement create() {
	return new AutoValue_EmptyStatement(NodeType.EMPTY_STATEMENT);
    }

    @Override
    public Cfg buildCfg() {
	return CfgBuilderForStatements.build(this);
    }

    @Override
    public Node clone() {
	return new AutoValue_EmptyStatement(type());
    }
}
