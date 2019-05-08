package esprima4java.ast;

import org.eclipse.jdt.annotation.Nullable;

import com.google.auto.value.AutoValue;

@AutoValue
public abstract class ExpressionStatement extends Node {
    public static ExpressionStatement create(Node expression, String directive) {
	return new AutoValue_ExpressionStatement(NodeType.EXPRESSION_STATEMENT, expression,
		directive);
    }

    abstract Node expression();

    @Nullable
    abstract String directive();
}
