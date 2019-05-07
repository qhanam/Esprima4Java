package esprima4java.ast.deserialize;

import com.google.gson.JsonObject;

import esprima4java.ast.Node;
import esprima4java.ast.NodeType;
import esprima4java.ast.TemplateElement;
import esprima4java.ast.TemplateValue;

public class TemplateElementDeserializer implements NodeDeserializer {

    @Override
    public NodeType getSupportedType() {
	return NodeType.TEMPLATE_ELEMENT;
    }

    @Override
    public Node deserialize(JsonObject json) throws DeserializationException {
	boolean tail = json.get("tail").getAsBoolean();
	TemplateValue value = TemplateValue.create(
		json.get("value").getAsJsonObject().get("cooked").getAsString(),
		json.get("value").getAsJsonObject().get("raw").getAsString());
	return TemplateElement.create(tail, value);
    }

}
