package esprima4java.ast;

import java.util.ArrayList;
import java.util.List;

import com.google.auto.value.AutoValue;

@AutoValue
public abstract class MetaProperty extends Node {
    public static MetaProperty create(Identifier meta, Identifier property) {
	return new AutoValue_MetaProperty(NodeType.META_PROPERTY, meta, property);
    }

    public abstract Identifier meta();

    public abstract Identifier property();

    @Override
    public List<Node> getChildren() {
	List<Node> children = new ArrayList<>();
	children.add(meta());
	children.add(property());
	return children;
    }

}
