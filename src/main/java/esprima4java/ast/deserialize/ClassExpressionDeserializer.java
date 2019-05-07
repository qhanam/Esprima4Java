package esprima4java.ast.deserialize;

import com.google.gson.JsonObject;

import esprima4java.Esprima2Java;
import esprima4java.ast.ClassBody;
import esprima4java.ast.ClassExpression;
import esprima4java.ast.Identifier;
import esprima4java.ast.Node;
import esprima4java.ast.NodeType;

public class ClassExpressionDeserializer implements NodeDeserializer {

    @Override
    public NodeType getSupportedType() {
	return NodeType.CLASS_EXPRESSION;
    }

    @Override
    public Node deserialize(JsonObject json) throws DeserializationException {
	Identifier id = json.has("id") ? (Identifier) Esprima2Java.deserialize(json.get("id"))
		: null;
	Node superClass = json.has("superClass") ? Esprima2Java.deserialize(json.get("superClass"))
		: null;
	ClassBody body = (ClassBody) Esprima2Java.deserialize(json.get("body"));
	return ClassExpression.create(id, superClass, body);
    }

}
