package esprima4java.ast;

import java.util.Collections;
import java.util.List;

import org.eclipse.jdt.annotation.Nullable;

import com.google.auto.value.AutoValue;

import esprima4java.cfg.Cfg;
import esprima4java.cfg.CfgBuilderForContinueStatements;

@AutoValue
public abstract class ContinueStatement extends Node {
    public static ContinueStatement create(Identifier label) {
	return new AutoValue_ContinueStatement(NodeType.CONTINUE_STATEMENT, label);
    }

    @Nullable
    public abstract Identifier label();

    @Override
    public List<Node> getChildren() {
	if (label() != null)
	    return Collections.singletonList(label());
	return Collections.emptyList();
    }

    @Override
    public Cfg buildCfg() {
	return CfgBuilderForContinueStatements.build(this);
    }

    @Override
    public Node clone() {
	return new AutoValue_ContinueStatement(type(), (Identifier) label().clone());
    }
}
