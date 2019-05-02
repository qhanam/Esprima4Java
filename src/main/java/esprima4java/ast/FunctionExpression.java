package esprima4java.ast;

import java.util.List;

import org.eclipse.jdt.annotation.Nullable;

import com.google.auto.value.AutoValue;

@AutoValue
public abstract class FunctionExpression extends Node {
    public static FunctionExpression create(Identifier id, List<Identifier> params,
	    BlockStatement body) {
	return new AutoValue_FunctionExpression(NodeType.FUNCTION_EXPRESSION, id, params, body);
    }

    public static FunctionExpression create(List<Identifier> params, BlockStatement body) {
	return new AutoValue_FunctionExpression(NodeType.FUNCTION_EXPRESSION, null, params, body);
    }

    @Nullable
    public abstract Identifier id();

    public abstract List<Identifier> params();

    public abstract BlockStatement body();
}
