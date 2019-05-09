package esprima4java.ast;

import java.util.ArrayList;
import java.util.List;

import com.google.auto.value.AutoValue;

@AutoValue
public abstract class TemplateLiteral extends Node {
    public static TemplateLiteral create(List<TemplateElement> quasis, List<Node> expressions) {
	return new AutoValue_TemplateLiteral(NodeType.TEMPLATE_LITERAL, quasis, expressions);
    }

    public abstract List<TemplateElement> quasis();

    public abstract List<Node> expressions();

    @Override
    public List<Node> getChildren() {
	List<Node> children = new ArrayList<>();
	children.addAll(quasis());
	children.addAll(expressions());
	return children;
    }

}
