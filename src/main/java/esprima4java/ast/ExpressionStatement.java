package esprima4java.ast;

import java.util.Collections;
import java.util.List;

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

    @Override
    public List<Node> getChildren() {
	return Collections.singletonList(expression());
    }

}
