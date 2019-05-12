package esprima4java.ast;

import java.util.ArrayList;
import java.util.List;

import com.google.auto.value.AutoValue;
import com.google.auto.value.extension.memoized.Memoized;

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

    @Override
    @Memoized
    public List<Node> getChildren() {
	List<Node> children = new ArrayList<>();
	children.addAll(params());
	children.add(body());
	return children;
    }

    @Override
    public Node clone() {
	List<Identifier> paramsCopy = new ArrayList<>();
	params().forEach(param -> paramsCopy.add((Identifier) param.clone()));
	return new AutoValue_ArrowFunctionExpression(type(), generator(), async(), paramsCopy,
		body().clone(), expression());
    }
}
