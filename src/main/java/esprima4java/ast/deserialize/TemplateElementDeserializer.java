package esprima4java.ast.deserialize;

import com.google.gson.JsonObject;

import esprima4java.ast.Node;
import esprima4java.ast.NodeType;
import esprima4java.ast.TemplateElement;
import esprima4java.ast.TemplateValue;

public class TemplateElementDeserializer extends NodeDeserializer {

    @Override
    public NodeType getSupportedType() {
	return NodeType.TEMPLATE_ELEMENT;
    }

    @Override
    public Node deserializePartial(JsonObject json) throws DeserializationException {
	boolean tail = json.get("tail").getAsBoolean();
	JsonObject serialValue = json.get("value").getAsJsonObject();
	String cooked = serialValue.has("cooked") && !serialValue.get("cooked").isJsonNull()
		? serialValue.get("cooked").toString()
		: null;
	String raw = serialValue.get("raw").toString();
	TemplateValue value = TemplateValue.create(cooked, raw);
	return TemplateElement.create(tail, value);
    }

}
