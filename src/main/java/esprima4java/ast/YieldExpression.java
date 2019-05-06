package esprima4java.ast;

import org.eclipse.jdt.annotation.Nullable;

import com.google.auto.value.AutoValue;

@AutoValue
public abstract class YieldExpression extends Node {
    public static YieldExpression create(Node argument, boolean delegate) {
	return new AutoValue_YieldExpression(NodeType.YIELD_EXPRESSION, argument, delegate);
    }

    @Nullable
    public abstract Node argument();

    public abstract boolean delegate();
}
