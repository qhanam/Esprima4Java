package esprima4java.ast.deserialize;

import java.util.ArrayList;
import java.util.List;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import esprima4java.Esprima2Java;
import esprima4java.ast.Node;
import esprima4java.ast.NodeType;
import esprima4java.ast.Program;

public class ProgramDeserializer implements NodeDeserializer {

    @Override
    public NodeType getSupportedType() {
	return NodeType.PROGRAM;
    }

    @Override
    public Node deserialize(JsonObject json) throws DeserializationException {
	List<Node> statements = new ArrayList<Node>();
	JsonArray jsonStatements = json.get("body").getAsJsonArray();
	for (JsonElement jsonStatement : jsonStatements) {
	    statements.add(Esprima2Java.deserialize(jsonStatement));
	}
	return Program.create(statements);
    }

}
