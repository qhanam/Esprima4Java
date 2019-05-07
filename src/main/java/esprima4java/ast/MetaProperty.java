package esprima4java.ast;

import com.google.auto.value.AutoValue;

@AutoValue
public abstract class MetaProperty extends Node {
    public static MetaProperty create(Identifier meta, Identifier property) {
	return new AutoValue_MetaProperty(NodeType.META_PROPERTY, meta, property);
    }

    public abstract Identifier meta();

    public abstract Identifier property();
}
