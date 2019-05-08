package esprima4java.ast.deserialize;

import com.google.gson.JsonObject;

import esprima4java.Esprima2Java;
import esprima4java.ast.Node;
import esprima4java.ast.NodeType;
import esprima4java.ast.TaggedTemplateExpression;
import esprima4java.ast.TemplateLiteral;

public class TaggedTemplateExpressionDeserializer extends NodeDeserializer {

    @Override
    public NodeType getSupportedType() {
	return NodeType.TAGGED_TEMPLATE_EXPRESSION;
    }

    @Override
    public Node deserializePartial(JsonObject json) throws DeserializationException {
	Node tag = Esprima2Java.deserialize(json.get("tag"));
	TemplateLiteral quasi = (TemplateLiteral) Esprima2Java.deserialize(json.get("quasi"));
	return TaggedTemplateExpression.create(tag, quasi);
    }

}
