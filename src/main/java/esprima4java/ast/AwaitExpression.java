package esprima4java.ast;

import com.google.auto.value.AutoValue;

@AutoValue
public abstract class AwaitExpression extends Node {
    public static AwaitExpression create(Node argument) {
	return new AutoValue_AwaitExpression(NodeType.AWAIT_EXPRESSION, argument);
    }

    public abstract Node argument();
}
