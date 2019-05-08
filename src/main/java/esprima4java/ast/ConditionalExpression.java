package esprima4java.ast;

import java.util.ArrayList;
import java.util.List;

import com.google.auto.value.AutoValue;

@AutoValue
public abstract class ConditionalExpression extends Node {
    public static ConditionalExpression create(Node test, Node alternate, Node expression) {
	return new AutoValue_ConditionalExpression(NodeType.CONDITIONAL_EXPRESSION, test, alternate,
		expression);
    }

    public abstract Node test();

    public abstract Node alternate();

    public abstract Node expression();

    @Override
    protected List<Node> getChildren() {
	List<Node> children = new ArrayList<>();
	children.add(test());
	children.add(alternate());
	children.add(expression());
	return children;
    }

}
