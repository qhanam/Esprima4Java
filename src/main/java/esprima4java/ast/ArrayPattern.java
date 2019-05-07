package esprima4java.ast;

import java.util.List;

import com.google.auto.value.AutoValue;

@AutoValue
public abstract class ArrayPattern extends Node {
    public static ArrayPattern create(List<Node> elements) {
	return new AutoValue_ArrayPattern(NodeType.ARRAY_PATTERN, elements);
    }

    public abstract List<Node> elements();
}
