package esprima4java.ast.deserialize;

import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;

import esprima4java.ast.Literal;
import esprima4java.ast.Node;
import esprima4java.ast.NodeType;
import esprima4java.ast.RegExpLiteral;

public class LiteralDeserializer extends NodeDeserializer {

    @Override
    public NodeType getSupportedType() {
	return NodeType.LITERAL;
    }

    @Override
    public Node deserializePartial(JsonObject json) throws DeserializationException {
	String raw = json.get("raw").getAsString();
	if (json.get("value").isJsonNull()) {
	    return Literal.createNull(raw);
	}

	JsonPrimitive value = (JsonPrimitive) json.get("value");
	if (value.isNumber()) {
	    return Literal.createNumber(json.get("value").getAsDouble(), raw);
	} else if (value.isBoolean()) {
	    return Literal.createBoolean(value.getAsBoolean(), raw);
	} else if (json.has("regex")) {
	    JsonObject regex = (JsonObject) json.get("regex");
	    return Literal.createRegEx(RegExpLiteral.create(regex.get("pattern").getAsString(),
		    regex.get("flags").getAsString()), raw);
	} else {
	    return Literal.createString(value.getAsString(), raw);
	}
    }

}
