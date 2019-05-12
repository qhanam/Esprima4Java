package esprima4java.ast;

import java.util.Collections;
import java.util.List;

import com.google.auto.value.AutoValue;

@AutoValue
public abstract class RestElement extends Node {
    public static RestElement create(Node argument) {
	return new AutoValue_RestElement(NodeType.REST_ELEMENT, argument);
    }

    public abstract Node argument();

    @Override
    public List<Node> getChildren() {
	return Collections.singletonList(argument());
    }

    @Override
    public Node clone() {
	return new AutoValue_RestElement(type(), argument().clone());
    }
}
