package esprima4java.ast;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.jdt.annotation.Nullable;

import com.google.auto.value.AutoValue;

import esprima4java.cfg.Cfg;
import esprima4java.cfg.CfgBuilderForSwitchCases;

@AutoValue
public abstract class SwitchCase extends Node {
    public static SwitchCase create(List<Node> consequent) {
	return new AutoValue_SwitchCase(NodeType.SWITCH_CASE, null, consequent);
    }

    public static SwitchCase create(Node test, List<Node> consequent) {
	return new AutoValue_SwitchCase(NodeType.SWITCH_CASE, test, consequent);
    }

    @Nullable
    public abstract Node test();

    public abstract List<Node> consequent();

    @Override
    public List<Node> getChildren() {
	List<Node> children = new ArrayList<>();
	if (test() != null)
	    children.add(test());
	children.addAll(consequent());
	return children;
    }

    @Override
    public Cfg buildCfg() {
	return CfgBuilderForSwitchCases.build(this);
    }

    @Override
    public Node clone() {
	List<Node> consequentCopy = new ArrayList<>();
	consequent().forEach(statement -> consequentCopy.add(statement.clone()));
	Node testClone = test() != null ? test().clone() : null;
	return new AutoValue_SwitchCase(type(), testClone, consequentCopy);
    }
}
