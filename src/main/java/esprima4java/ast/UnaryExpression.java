package esprima4java.ast;

import com.google.auto.value.AutoValue;

@AutoValue
public abstract class UnaryExpression extends Node {
    public static UnaryExpression create(UnaryOperator operator, boolean prefix, Node argument) {
	return new AutoValue_UnaryExpression(NodeType.UNARY_EXPRESSION, operator, prefix, argument);
    }

    public enum UnaryOperator {
	SUB("-"), ADD("+"), NOT("!"), BIT_NOT("~"), TYPEOF("typeof"), VOID("void"), DELETE(
		"delete");

	private String op;

	UnaryOperator(String op) {
	    this.op = op;
	}

	@Override
	public String toString() {
	    return op;
	}
    }

    public abstract UnaryOperator operator();

    public abstract boolean prefix();

    public abstract Node argument();

}
