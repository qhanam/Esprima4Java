package esprima4java.ast;

import java.util.ArrayList;
import java.util.List;

import com.google.auto.value.AutoValue;
import com.google.auto.value.extension.memoized.Memoized;

@AutoValue
public abstract class AssignmentExpression extends Node {
    public static AssignmentExpression create(AssignmentOperator operator, Node left, Node right) {
	return new AutoValue_AssignmentExpression(NodeType.ASSIGNMENT_EXPRESSION, operator, left,
		right);
    }

    public enum AssignmentOperator {
	ASSIGN("="), ASSIGN_ADD("+="), ASSIGN_SUB("-="), //
	ASSIGN_MULT("*="), ASSIGN_DIV("/="), ASSIGN_MOD("%="), //
	ASSIGN_LSH("<<="), ASSIGN_RSH(">>="), ASSIGN_URSH(">>>="), //
	ASSIGN_BITOR("|="), ASSIGN_BITNOT("^="), ASSIGN_BITAND("&="), //
	ASSIGN_EXP("**=");

	private String op;

	AssignmentOperator(String op) {
	    this.op = op;
	}

	public static AssignmentOperator deserialize(String serial) {
	    for (AssignmentOperator op : AssignmentOperator.values()) {
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

    public abstract AssignmentOperator operator();

    public abstract Node left();

    public abstract Node right();

    @Override
    @Memoized
    protected List<Node> getChildren() {
	List<Node> children = new ArrayList<>();
	children.add(left());
	children.add(right());
	return children;
    }

}
