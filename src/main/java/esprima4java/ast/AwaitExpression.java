package esprima4java.ast;

import java.util.Collections;
import java.util.List;

import com.google.auto.value.AutoValue;
import com.google.auto.value.extension.memoized.Memoized;

@AutoValue
public abstract class AwaitExpression extends Node {
    public static AwaitExpression create(Node argument) {
	return new AutoValue_AwaitExpression(NodeType.AWAIT_EXPRESSION, argument);
    }

    public abstract Node argument();

    @Override
    @Memoized
    protected List<Node> getChildren() {
	return Collections.singletonList(argument());
    }

}
