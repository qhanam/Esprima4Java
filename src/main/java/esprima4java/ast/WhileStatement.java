package esprima4java.ast;

import java.util.ArrayList;
import java.util.List;

import com.google.auto.value.AutoValue;

import esprima4java.cfg.Cfg;
import esprima4java.cfg.builders.CfgBuilderForWhileStatements;

@AutoValue
public abstract class WhileStatement extends Node {
    public static WhileStatement create(Node test, Node body) {
	return new AutoValue_WhileStatement(NodeType.WHILE_STATEMENT, test, body);
    }

    public abstract Node test();

    public abstract Node body();

    @Override
    public List<Node> getChildren() {
	List<Node> children = new ArrayList<>();
	children.add(test());
	children.add(body());
	return children;
    }

    @Override
    public Cfg buildCfg() {
	return CfgBuilderForWhileStatements.build(this);
    }

    @Override
    public Node clone() {
	return new AutoValue_WhileStatement(type(), test().clone(), body().clone());
    }
}
