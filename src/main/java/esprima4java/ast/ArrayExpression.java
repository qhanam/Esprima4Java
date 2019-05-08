package esprima4java.ast;

import java.util.List;

import com.google.auto.value.AutoValue;
import com.google.auto.value.extension.memoized.Memoized;

@AutoValue
public abstract class ArrayExpression extends Node {
    public static ArrayExpression create(List<Node> elements) {
	return new AutoValue_ArrayExpression(NodeType.ARRAY_EXPRESSION, elements);
    }

    public abstract List<Node> elements();

    @Override
    @Memoized
    protected List<Node> getChildren() {
	return elements();
    }

}
