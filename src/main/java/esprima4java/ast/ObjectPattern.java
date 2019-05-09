package esprima4java.ast;

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

}
