package esprima4java.ast;

import java.util.ArrayList;
import java.util.List;

import com.google.auto.value.AutoValue;
import com.google.auto.value.extension.memoized.Memoized;

@AutoValue
public abstract class ArrayPattern extends Node {
    public static ArrayPattern create(List<Node> elements) {
	return new AutoValue_ArrayPattern(NodeType.ARRAY_PATTERN, elements);
    }

    public abstract List<Node> elements();

    @Override
    @Memoized
    public List<Node> getChildren() {
	return elements();
    }

    @Override
    public Node clone() {
	List<Node> elementsCopy = new ArrayList<>();
	elements().forEach(element -> elementsCopy.add(element.clone()));
	return new AutoValue_ArrayPattern(type(), elementsCopy);
    }
}
