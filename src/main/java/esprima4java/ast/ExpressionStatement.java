package esprima4java.ast;

import com.google.auto.value.AutoValue;

@AutoValue
public abstract class ExpressionStatement extends Node {
    public static ExpressionStatement create(Node expression) {
	return new AutoValue_ExpressionStatement(NodeType.EXPRESSION_STATEMENT, expression);
    }

    abstract Node expression();
}
