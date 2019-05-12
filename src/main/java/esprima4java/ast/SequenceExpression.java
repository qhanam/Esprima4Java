package esprima4java.ast;

import java.util.ArrayList;
import java.util.List;

import com.google.auto.value.AutoValue;

@AutoValue
public abstract class SequenceExpression extends Node {
    public static SequenceExpression create(List<Node> expressions) {
	return new AutoValue_SequenceExpression(NodeType.SEQUENCE_EXPRESSION, expressions);
    }

    public abstract List<Node> expressions();

    @Override
    public List<Node> getChildren() {
	return expressions();
    }

    @Override
    public Node clone() {
	List<Node> expressionsCopy = new ArrayList<>();
	expressions().forEach(expression -> expressionsCopy.add(expression.clone()));
	return new AutoValue_SequenceExpression(type(), expressionsCopy);
    }
}
