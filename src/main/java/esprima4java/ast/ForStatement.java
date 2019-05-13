package esprima4java.ast;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.jdt.annotation.Nullable;

import com.google.auto.value.AutoValue;

import esprima4java.cfg.Cfg;
import esprima4java.cfg.CfgBuilderForForStatements;

@AutoValue
public abstract class ForStatement extends Node {
    public static ForStatement create(Node init, Node test, Node update, Node body) {
	return new AutoValue_ForStatement(NodeType.FOR_STATEMENT, init, test, update, body);
    }

    @Nullable
    public abstract Node init();

    @Nullable
    public abstract Node test();

    @Nullable
    public abstract Node update();

    public abstract Node body();

    @Override
    public List<Node> getChildren() {
	List<Node> children = new ArrayList<>();
	if (init() != null)
	    children.add(init());
	if (test() != null)
	    children.add(test());
	if (update() != null)
	    children.add(update());
	children.add(body());
	return children;
    }

    @Override
    public Cfg buildCfg() {
	return CfgBuilderForForStatements.build(this);
    }

    @Override
    public Node clone() {
	Node initClone = init() != null ? init().clone() : null;
	Node testClone = test() != null ? test().clone() : null;
	Node updateClone = update() != null ? update() : null;
	return new AutoValue_ForStatement(type(), initClone, testClone, updateClone,
		body().clone());
    }
}
