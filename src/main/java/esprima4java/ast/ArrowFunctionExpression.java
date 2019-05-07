package esprima4java.ast;

import java.util.List;

import com.google.auto.value.AutoValue;

@AutoValue
public abstract class ArrowFunctionExpression extends Function {
    public static ArrowFunctionExpression create(boolean generator, boolean async,
	    List<Identifier> params, Node body, boolean expression) {
	return new AutoValue_ArrowFunctionExpression(NodeType.ARROW_FUNCTION_EXPRESSION, generator,
		async, params, body, expression);
    }

    public abstract List<Identifier> params();

    public abstract Node body();

    public abstract boolean expression();
}
