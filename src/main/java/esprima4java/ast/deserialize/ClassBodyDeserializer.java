package esprima4java.ast.deserialize;

import java.util.ArrayList;
import java.util.List;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import esprima4java.Esprima2Java;
import esprima4java.ast.ClassBody;
import esprima4java.ast.MethodDefinition;
import esprima4java.ast.Node;
import esprima4java.ast.NodeType;

public class ClassBodyDeserializer extends NodeDeserializer {

    @Override
    public NodeType getSupportedType() {
	return NodeType.CLASS_BODY;
    }

    @Override
    public Node deserializePartial(JsonObject json) throws DeserializationException {
	List<MethodDefinition> body = new ArrayList<>();
	for (JsonElement je : json.get("body").getAsJsonArray()) {
	    body.add((MethodDefinition) Esprima2Java.deserialize(je));
	}
	return ClassBody.create(body);
    }

}
