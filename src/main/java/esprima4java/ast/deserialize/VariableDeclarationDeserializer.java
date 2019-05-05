package esprima4java.ast.deserialize;

import java.util.ArrayList;
import java.util.List;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import esprima4java.Esprima2Java;
import esprima4java.ast.Node;
import esprima4java.ast.NodeType;
import esprima4java.ast.VariableDeclaration;
import esprima4java.ast.VariableDeclarator;

public class VariableDeclarationDeserializer implements NodeDeserializer {

    @Override
    public NodeType getSupportedType() {
	return NodeType.VARIABLE_DECLARATION;
    }

    @Override
    public Node deserialize(JsonObject json) throws DeserializationException {
	List<VariableDeclarator> declarations = new ArrayList<>();
	for (JsonElement je : json.get("declarations").getAsJsonArray()) {
	    declarations.add((VariableDeclarator) Esprima2Java.deserialize(je));
	}
	return VariableDeclaration.create(declarations);
    }

}
