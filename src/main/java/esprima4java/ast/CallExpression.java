package esprima4java.ast;

import java.util.List;

import com.google.auto.value.AutoValue;

@AutoValue
public abstract class CallExpression extends Node {
    public static CallExpression create(Node callee, List<Node> arguments) {
	return new AutoValue_CallExpression(NodeType.CALL_EXPRESSION, callee, arguments);
    }

    public abstract Node callee();

    public abstract List<Node> arguments();
}
