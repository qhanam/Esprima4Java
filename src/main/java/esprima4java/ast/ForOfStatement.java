package esprima4java.ast;

import java.util.ArrayList;
import java.util.List;

import com.google.auto.value.AutoValue;

@AutoValue
public abstract class ForOfStatement extends Node {
    public static ForOfStatement create(Node left, Node right, Node body, boolean await) {
	return new AutoValue_ForOfStatement(NodeType.FOR_OF_STATEMENT, left, right, body, await);
    }

    public abstract Node left();

    public abstract Node right();

    public abstract Node body();

    public abstract boolean await();

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
	return new AutoValue_ForOfStatement(type(), left().clone(), right().clone(), body().clone(),
		await());
    }
}
