package esprima4java.ast;

import com.google.auto.value.AutoValue;

@AutoValue
public abstract class ExpressionStatement extends Node {
    public static ExpressionStatement create(Node expression) {
	return new AutoValue_ExpressionStatement(NodeType.EXPRESSION_STATEMENT, expression, "");
    }

    public static ExpressionStatement create(Node expression, String directive) {
	return new AutoValue_ExpressionStatement(NodeType.EXPRESSION_STATEMENT, expression,
		directive);
    }

    abstract Node expression();

    abstract String directive();
}
