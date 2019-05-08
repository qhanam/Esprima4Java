package esprima4java.ast;

import java.util.ArrayList;
import java.util.List;

import com.google.auto.value.AutoValue;

@AutoValue
public abstract class WithStatement extends Node {
    public static WithStatement create(Node object, Node body) {
	return new AutoValue_WithStatement(NodeType.WITH_STATEMENT, object, body);
    }

    public abstract Node object();

    public abstract Node body();

    @Override
    protected List<Node> getChildren() {
	List<Node> children = new ArrayList<>();
	children.add(object());
	children.add(body());
	return children;
    }

}
