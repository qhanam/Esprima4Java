package esprima4java.ast;

import java.util.ArrayList;
import java.util.List;

import com.google.auto.value.AutoValue;

@AutoValue
public abstract class LabeledStatement extends Node {
    public static LabeledStatement create(Identifier label, Node statement) {
	return new AutoValue_LabeledStatement(NodeType.LABELED_STATEMENT, label, statement);
    }

    public abstract Identifier label();

    public abstract Node statement();

    @Override
    public List<Node> getChildren() {
	List<Node> children = new ArrayList<>();
	children.add(label());
	children.add(statement());
	return children;
    }

}
