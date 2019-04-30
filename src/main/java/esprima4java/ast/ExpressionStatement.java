package esprima4java.ast;

import com.google.auto.value.AutoValue;
import com.google.gson.JsonObject;

import esprima4java.DeserializationException;
import esprima4java.Esprima2Java;

@AutoValue
public abstract class ExpressionStatement extends AstNode {
    public static ExpressionStatement create(AstNode expression) {
	return new AutoValue_ExpressionStatement(NodeType.EXPRESSION_STATEMENT, expression);
    }

    public static ExpressionStatement deserialize(JsonObject jo) throws DeserializationException {
	if (!jo.get("type").getAsString().equals("ExpressionStatement")) {
	    throw new DeserializationException("JSON object must have `ExpressionStatement` type.");
	}
	return ExpressionStatement
		.create(Esprima2Java.deserialize((JsonObject) jo.get("expression")));
    }

    abstract AstNode expression();
}
