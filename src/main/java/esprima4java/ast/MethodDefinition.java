package esprima4java.ast;

import java.util.ArrayList;
import java.util.List;

import com.google.auto.value.AutoValue;

@AutoValue
public abstract class MethodDefinition extends Node {
    public static MethodDefinition create(Node key, FunctionExpression value, Kind kind,
	    boolean computed, boolean stat) {
	return new AutoValue_MethodDefinition(NodeType.METHOD_DEFINITION, key, value, kind,
		computed, stat);
    }

    public enum Kind {
	CONSTRUCTOR("constructor"), METHOD("method"), GET("get"), SET("set");

	String kind;

	Kind(String kind) {
	    this.kind = kind;
	}

	public static Kind deserialize(String serial) {
	    for (Kind kind : Kind.values()) {
		if (kind.toString().equals(serial))
		    return kind;
	    }
	    return null;
	}

	@Override
	public String toString() {
	    return kind;
	}
    }

    public abstract Node key();

    public abstract FunctionExpression value();

    public abstract Kind kind();

    public abstract boolean computed();

    public abstract boolean stat();

    @Override
    public List<Node> getChildren() {
	List<Node> children = new ArrayList<>();
	children.add(key());
	children.add(value());
	return children;
    }

    @Override
    public Node clone() {
	return new AutoValue_MethodDefinition(type(), key().clone(),
		(FunctionExpression) value().clone(), kind(), computed(), stat());
    }
}
