package esprima4java.ast;

import java.util.List;

import com.google.auto.value.AutoValue;

@AutoValue
public abstract class NewExpression extends Node {
    public static NewExpression create(Node callee, List<Node> arguments) {
	return new AutoValue_NewExpression(NodeType.NEW_EXPRESSION, callee, arguments);
    }

    public abstract Node callee();

    public abstract List<Node> arguments();
}
