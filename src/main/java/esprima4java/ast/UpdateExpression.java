package esprima4java.ast;

import com.google.auto.value.AutoValue;

@AutoValue
public abstract class UpdateExpression extends Node {
    public static UpdateExpression create(UpdateOperator operator, boolean prefix, Node argument) {
	return new AutoValue_UpdateExpression(NodeType.UPDATE_EXPRESSION, operator, prefix,
		argument);
    }

    public enum UpdateOperator {
	INCREMENT("++"), DECREMENT("--");

	private String op;

	UpdateOperator(String op) {
	    this.op = op;
	}

	@Override
	public String toString() {
	    return op;
	}
    }

    public abstract UpdateOperator operator();

    public abstract boolean prefix();

    public abstract Node argument();

}
