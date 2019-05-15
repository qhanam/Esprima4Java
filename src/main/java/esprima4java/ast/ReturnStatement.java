package esprima4java.ast;

import java.util.Collections;
import java.util.List;

import org.eclipse.jdt.annotation.Nullable;

import com.google.auto.value.AutoValue;

import esprima4java.cfg.Cfg;
import esprima4java.cfg.builders.CfgBuilderForReturnStatements;

@AutoValue
public abstract class ReturnStatement extends Node {
    public static ReturnStatement create(Node argument) {
	return new AutoValue_ReturnStatement(NodeType.RETURN_STATEMENT, argument);
    }

    @Nullable
    public abstract Node argument();

    @Override
    public List<Node> getChildren() {
	if (argument() != null)
	    return Collections.singletonList(argument());
	return Collections.emptyList();
    }

    @Override
    public Cfg buildCfg() {
	return CfgBuilderForReturnStatements.build(this);
    }

    @Override
    public Node clone() {
	Node argumentClone = argument() != null ? argument().clone() : null;
	return new AutoValue_ReturnStatement(type(), argumentClone);
    }
}
