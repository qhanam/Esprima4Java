package esprima4java.ast;

import com.google.auto.value.AutoValue;

@AutoValue
public abstract class RestElement extends Node {
    public static RestElement create(Node argument) {
	return new AutoValue_RestElement(NodeType.REST_ELEMENT, argument);
    }

    public abstract Node argument();
}
