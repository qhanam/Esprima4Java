package esprima4java.ast;

import java.util.Collections;
import java.util.List;

import com.google.auto.value.AutoValue;

@AutoValue
public abstract class SpreadElement extends Node {
    public static SpreadElement create(Node expression) {
	return new AutoValue_SpreadElement(NodeType.SPREAD_ELEMENT, expression);
    }

    abstract Node argument();

    @Override
    protected List<Node> getChildren() {
	return Collections.singletonList(argument());
    }

}
