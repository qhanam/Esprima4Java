package esprima4java.ast;

import java.util.List;

import com.google.auto.value.AutoValue;

@AutoValue
public abstract class ObjectExpression extends Node {
    public static ObjectExpression create(List<Node> elements) {
	return new AutoValue_ObjectExpression(NodeType.OBJECT_EXPRESSION, elements);
    }

    public abstract List<Node> properties();

    @Override
    protected List<Node> getChildren() {
	return properties();
    }

}
