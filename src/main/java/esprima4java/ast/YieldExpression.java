package esprima4java.ast;

import java.util.Collections;
import java.util.List;

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

    @Override
    public List<Node> getChildren() {
	if (argument() != null)
	    return Collections.singletonList(argument());
	return Collections.emptyList();
    }

    @Override
    public Node clone() {
	return new AutoValue_YieldExpression(type(), argument().clone(), delegate());
    }
}
