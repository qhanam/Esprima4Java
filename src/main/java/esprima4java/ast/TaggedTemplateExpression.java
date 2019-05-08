package esprima4java.ast;

import java.util.ArrayList;
import java.util.List;

import com.google.auto.value.AutoValue;

@AutoValue
public abstract class TaggedTemplateExpression extends Node {
    public static TaggedTemplateExpression create(Node tag, TemplateLiteral quasi) {
	return new AutoValue_TaggedTemplateExpression(NodeType.TAGGED_TEMPLATE_EXPRESSION, tag,
		quasi);
    }

    public abstract Node tag();

    public abstract TemplateLiteral quasi();

    @Override
    protected List<Node> getChildren() {
	List<Node> children = new ArrayList<>();
	children.add(tag());
	children.add(quasi());
	return children;
    }

}
