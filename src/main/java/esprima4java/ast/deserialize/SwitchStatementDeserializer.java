package esprima4java.ast.deserialize;

import java.util.ArrayList;
import java.util.List;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import esprima4java.Esprima2Java;
import esprima4java.ast.Node;
import esprima4java.ast.NodeType;
import esprima4java.ast.SwitchCase;
import esprima4java.ast.SwitchStatement;

public class SwitchStatementDeserializer implements NodeDeserializer {

    @Override
    public NodeType getSupportedType() {
	return NodeType.SWITCH_STATEMENT;
    }

    @Override
    public Node deserialize(JsonObject json) throws DeserializationException {
	Node discriminant = Esprima2Java.deserialize((JsonObject) json.get("discriminant"));
	List<SwitchCase> cases = new ArrayList<>();
	for (JsonElement je : json.get("cases").getAsJsonArray()) {
	    cases.add((SwitchCase) Esprima2Java.deserialize((JsonObject) je));
	}
	return SwitchStatement.create(discriminant, cases);
    }

}
