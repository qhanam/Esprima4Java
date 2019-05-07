package esprima4java.ast;

import com.google.auto.value.AutoValue;

@AutoValue
public abstract class BinaryExpression extends Node {
    public static BinaryExpression create(BinaryOperator operator, Node left, Node right) {
	return new AutoValue_BinaryExpression(NodeType.BINARY_EXPRESSION, operator, left, right);
    }

    public enum BinaryOperator {
	EQ("="), NE("!="), SHEQ("==="), SHNE("!=="), //
	LT("<"), LTE("<="), GT(">"), GTE(">="), //
	LSH("<<"), RSH(">>"), URSH(">>>"), //
	ADD("+"), SUB("-"), MULT("*"), DIV("/"), MOD("%"), //
	BITOR("|"), BITNOT("^"), BITAND("&"), //
	IN("in"), INSTANCEOF("instanceof"), //
	EXP("**");

	private String op;

	BinaryOperator(String op) {
	    this.op = op;
	}

	public static BinaryOperator deserialize(String serial) {
	    for (BinaryOperator op : BinaryOperator.values()) {
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

    public abstract BinaryOperator operator();

    public abstract Node left();

    public abstract Node right();

}
