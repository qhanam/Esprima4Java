package esprima4java.ast;

import java.util.Collections;
import java.util.List;

import org.eclipse.jdt.annotation.Nullable;

import com.google.auto.value.AutoValue;
import com.google.auto.value.extension.memoized.Memoized;

import esprima4java.cfg.Cfg;
import esprima4java.cfg.CfgBuilderForBreakStatements;

@AutoValue
public abstract class BreakStatement extends Node {
    public static BreakStatement create(Identifier label) {
	return new AutoValue_BreakStatement(NodeType.BREAK_STATEMENT, label);
    }

    @Nullable
    public abstract Identifier label();

    @Override
    @Memoized
    public List<Node> getChildren() {
	if (label() == null)
	    return Collections.emptyList();
	return Collections.singletonList(label());
    }

    @Override
    public Cfg buildCfg() {
	return CfgBuilderForBreakStatements.build(this);
    }

    @Override
    public Node clone() {
	return new AutoValue_BreakStatement(type(), (Identifier) label().clone());
    }
}
