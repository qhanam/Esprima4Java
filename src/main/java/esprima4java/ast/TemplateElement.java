package esprima4java.ast;

import com.google.auto.value.AutoValue;

@AutoValue
public abstract class TemplateElement extends Node {
    public static TemplateElement create(boolean tail, TemplateValue value) {
	return new AutoValue_TemplateElement(NodeType.TEMPLATE_ELEMENT, tail, value);
    }

    public abstract boolean tail();

    public abstract TemplateValue value();
}
