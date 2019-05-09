package esprima4java.ast;

import java.util.ArrayList;
import java.util.List;

import com.google.auto.value.AutoValue;

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

}
