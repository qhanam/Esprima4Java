package esprima4java.ast;

import com.google.auto.value.AutoValue;

@AutoValue
public abstract class TaggedTemplateExpression extends Node {
    public static TaggedTemplateExpression create(Node tag, TemplateLiteral quasi) {
	return new AutoValue_TaggedTemplateExpression(NodeType.TAGGED_TEMPLATE_EXPRESSION, tag,
		quasi);
    }

    public abstract Node tag();

    public abstract TemplateLiteral quasi();
}
