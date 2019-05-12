package esprima4java.ast;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.jdt.annotation.Nullable;

import com.google.auto.value.AutoValue;

@AutoValue
public abstract class FunctionExpression extends Function {
    public static FunctionExpression create(boolean generator, boolean async, Identifier id,
	    List<Identifier> params, BlockStatement body) {
	return new AutoValue_FunctionExpression(NodeType.FUNCTION_EXPRESSION, generator, async, id,
		params, body);
    }

    @Nullable
    public abstract Identifier id();

    public abstract List<Identifier> params();

    public abstract BlockStatement body();

    @Override
    public List<Node> getChildren() {
	List<Node> children = new ArrayList<>();
	if (id() != null)
	    children.add(id());
	children.addAll(params());
	children.add(body());
	return children;
    }

    @Override
    public Node clone() {
	List<Identifier> paramsCopy = new ArrayList<>();
	params().forEach(param -> paramsCopy.add((Identifier) param.clone()));
	return new AutoValue_FunctionExpression(type(), generator(), async(), id(), paramsCopy,
		(BlockStatement) body().clone());
    }
}
