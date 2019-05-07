package esprima4java.ast.deserialize;

import com.google.gson.JsonObject;

import esprima4java.Esprima2Java;
import esprima4java.ast.ClassBody;
import esprima4java.ast.ClassDeclaration;
import esprima4java.ast.Identifier;
import esprima4java.ast.Node;
import esprima4java.ast.NodeType;

public class ClassDefinitionDeserializer implements NodeDeserializer {

    @Override
    public NodeType getSupportedType() {
	return NodeType.CLASS_DECLARATION;
    }

    @Override
    public Node deserialize(JsonObject json) throws DeserializationException {
	Identifier id = (Identifier) Esprima2Java.deserialize(json.get("id"));
	Node superClass = json.has("superClass") ? Esprima2Java.deserialize(json.get("superClass"))
		: null;
	ClassBody body = (ClassBody) Esprima2Java.deserialize(json.get("body"));
	return ClassDeclaration.create(id, superClass, body);
    }

}
