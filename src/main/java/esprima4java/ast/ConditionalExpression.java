package esprima4java.ast;

import com.google.auto.value.AutoValue;

@AutoValue
public abstract class ConditionalExpression extends Node {
    public static ConditionalExpression create(Node test, Node alternate, Node expression) {
	return new AutoValue_ConditionalExpression(NodeType.CONDITIONAL_EXPRESSION, test, alternate,
		expression);
    }

    public abstract Node test();

    public abstract Node alternate();

    public abstract Node expression();
}
