package esprima4java.ast;

import java.util.ArrayList;
import java.util.List;

import com.google.auto.value.AutoValue;

@AutoValue
public abstract class ObjectPattern extends Node {
    public static ObjectPattern create(List<Node> properties) {
	return new AutoValue_ObjectPattern(NodeType.OBJECT_PATTERN, properties);
    }

    public abstract List<Node> properties();

    @Override
    public List<Node> getChildren() {
	return properties();
    }

    @Override
    public Node clone() {
	List<Node> propertiesCopy = new ArrayList<>();
	properties().forEach(property -> propertiesCopy.add(property.clone()));
	return new AutoValue_ObjectExpression(type(), propertiesCopy);
    }
}
