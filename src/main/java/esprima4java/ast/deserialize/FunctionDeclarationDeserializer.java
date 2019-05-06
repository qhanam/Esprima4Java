package esprima4java.ast.deserialize;

import java.util.ArrayList;
import java.util.List;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import esprima4java.Esprima2Java;
import esprima4java.ast.BlockStatement;
import esprima4java.ast.FunctionDeclaration;
import esprima4java.ast.Identifier;
import esprima4java.ast.Node;
import esprima4java.ast.NodeType;

public class FunctionDeclarationDeserializer implements NodeDeserializer {

    @Override
    public NodeType getSupportedType() {
	return NodeType.FUNCTION_DECLARATION;
    }

    @Override
    public Node deserialize(JsonObject json) throws DeserializationException {
	boolean generator = json.get("generator").getAsBoolean();
	Identifier id = (Identifier) Esprima2Java.deserialize(json.get("id"));
	List<Identifier> params = new ArrayList<>();
	for (JsonElement param : json.get("params").getAsJsonArray()) {
	    params.add((Identifier) Esprima2Java.deserialize(param));
	}
	BlockStatement body = (BlockStatement) Esprima2Java.deserialize(json.get("body"));
	return FunctionDeclaration.create(generator, id, params, body);
    }

}
