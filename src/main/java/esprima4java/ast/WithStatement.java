package esprima4java.ast;

import java.util.ArrayList;
import java.util.List;

import com.google.auto.value.AutoValue;

import esprima4java.cfg.Cfg;
import esprima4java.cfg.CfgBuilderForWithStatements;

@AutoValue
public abstract class WithStatement extends Node {
    public static WithStatement create(Node object, Node body) {
	return new AutoValue_WithStatement(NodeType.WITH_STATEMENT, object, body);
    }

    public abstract Node object();

    public abstract Node body();

    @Override
    public List<Node> getChildren() {
	List<Node> children = new ArrayList<>();
	children.add(object());
	children.add(body());
	return children;
    }

    @Override
    public Cfg buildCfg() {
	return CfgBuilderForWithStatements.build(this);
    }

    @Override
    public Node clone() {
	return new AutoValue_WithStatement(type(), object().clone(), body().clone());
    }
}
