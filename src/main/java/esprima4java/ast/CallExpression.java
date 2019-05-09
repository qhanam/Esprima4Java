package esprima4java.ast;

import java.util.ArrayList;
import java.util.List;

import com.google.auto.value.AutoValue;
import com.google.auto.value.extension.memoized.Memoized;

@AutoValue
public abstract class CallExpression extends Node {
    public static CallExpression create(Node callee, List<Node> arguments) {
	return new AutoValue_CallExpression(NodeType.CALL_EXPRESSION, callee, arguments);
    }

    public abstract Node callee();

    public abstract List<Node> arguments();

    @Override
    @Memoized
    public List<Node> getChildren() {
	List<Node> children = new ArrayList<>();
	children.add(callee());
	children.addAll(arguments());
	return children;
    }

}
