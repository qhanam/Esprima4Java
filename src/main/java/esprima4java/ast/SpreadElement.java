package esprima4java.ast;

import java.util.Collections;
import java.util.List;

import com.google.auto.value.AutoValue;

@AutoValue
public abstract class SpreadElement extends Node {
    public static SpreadElement create(Node argument) {
	return new AutoValue_SpreadElement(NodeType.SPREAD_ELEMENT, argument);
    }

    abstract Node argument();

    @Override
    public List<Node> getChildren() {
	return Collections.singletonList(argument());
    }

    @Override
    public Node clone() {
	return new AutoValue_SpreadElement(type(), argument().clone());
    }
}
