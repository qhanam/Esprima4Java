package esprima4java.ast;

import java.util.ArrayList;
import java.util.List;

import com.google.auto.value.AutoValue;

@AutoValue
public abstract class LogicalExpression extends Node {
    public static LogicalExpression create(LogicalOperator operator, Node left, Node right) {
	return new AutoValue_LogicalExpression(NodeType.LOGICAL_EXPRESSION, operator, left, right);
    }

    public enum LogicalOperator {
	OR("||"), AND("&&");

	private String op;

	LogicalOperator(String op) {
	    this.op = op;
	}

	public static LogicalOperator deserialize(String serial) {
	    for (LogicalOperator op : LogicalOperator.values()) {
		if (op.toString().equals(serial))
		    return op;
	    }
	    return null;
	}

	@Override
	public String toString() {
	    return op;
	}
    }

    public abstract LogicalOperator operator();

    public abstract Node left();

    public abstract Node right();

    @Override
    public List<Node> getChildren() {
	List<Node> children = new ArrayList<>();
	children.add(left());
	children.add(right());
	return children;
    }

    @Override
    public Node clone() {
	return new AutoValue_LogicalExpression(type(), operator(), left().clone(), right().clone());
    }
}
