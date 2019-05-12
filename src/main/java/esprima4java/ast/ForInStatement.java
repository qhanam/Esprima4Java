package esprima4java.ast;

import java.util.ArrayList;
import java.util.List;

import com.google.auto.value.AutoValue;

@AutoValue
public abstract class ForInStatement extends Node {
    public static ForInStatement create(Node left, Node right, Node body) {
	return new AutoValue_ForInStatement(NodeType.FOR_IN_STATEMENT, left, right, body);
    }

    public abstract Node left();

    public abstract Node right();

    public abstract Node body();

    @Override
    public List<Node> getChildren() {
	List<Node> children = new ArrayList<>();
	children.add(left());
	children.add(right());
	children.add(body());
	return children;
    }

    @Override
    public Node clone() {
	return new AutoValue_ForInStatement(type(), left().clone(), right().clone(),
		body().clone());
    }

}
