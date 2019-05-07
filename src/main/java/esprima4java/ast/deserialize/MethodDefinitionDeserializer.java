package esprima4java.ast.deserialize;

import com.google.gson.JsonObject;

import esprima4java.Esprima2Java;
import esprima4java.ast.FunctionExpression;
import esprima4java.ast.MethodDefinition;
import esprima4java.ast.MethodDefinition.Kind;
import esprima4java.ast.Node;
import esprima4java.ast.NodeType;

public class MethodDefinitionDeserializer implements NodeDeserializer {

    @Override
    public NodeType getSupportedType() {
	return NodeType.METHOD_DEFINITION;
    }

    @Override
    public Node deserialize(JsonObject json) throws DeserializationException {
	Node key = Esprima2Java.deserialize(json.get("key"));
	FunctionExpression value = (FunctionExpression) Esprima2Java.deserialize(json.get("value"));
	Kind kind = Kind.deserialize(json.get("kind").getAsString());
	boolean computed = json.get("computed").getAsBoolean();
	boolean stat = json.get("static").getAsBoolean();
	return MethodDefinition.create(key, value, kind, computed, stat);
    }

}
