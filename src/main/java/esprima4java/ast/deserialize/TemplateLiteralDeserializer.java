package esprima4java.ast.deserialize;

import java.util.ArrayList;
import java.util.List;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import esprima4java.Esprima2Java;
import esprima4java.ast.Node;
import esprima4java.ast.NodeType;
import esprima4java.ast.TemplateElement;
import esprima4java.ast.TemplateLiteral;

public class TemplateLiteralDeserializer extends NodeDeserializer {

    @Override
    public NodeType getSupportedType() {
	return NodeType.TEMPLATE_LITERAL;
    }

    @Override
    public Node deserializePartial(JsonObject json) throws DeserializationException {
	List<TemplateElement> quasis = new ArrayList<>();
	for (JsonElement je : json.get("quasis").getAsJsonArray()) {
	    quasis.add((TemplateElement) Esprima2Java.deserialize(je));
	}
	List<Node> expressions = new ArrayList<>();
	for (JsonElement je : json.get("expressions").getAsJsonArray()) {
	    expressions.add(Esprima2Java.deserialize(je));
	}
	return TemplateLiteral.create(quasis, expressions);
    }

}
