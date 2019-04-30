package esprima4java.ast;

import com.google.auto.value.AutoValue;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;

import esprima4java.DeserializationException;

@AutoValue
public abstract class Literal extends AstNode {
    public static Literal createNull(String raw) {
	return new AutoValue_Literal(NodeType.LITERAL, Type.NULL, 0, "", false, RegEx.create(),
		raw);
    }

    public static Literal createNumber(double number, String raw) {
	return new AutoValue_Literal(NodeType.LITERAL, Type.NUMBER, number, "", false,
		RegEx.create(), raw);
    }

    public static Literal createString(String string, String raw) {
	return new AutoValue_Literal(NodeType.LITERAL, Type.STRING, 0, string, false,
		RegEx.create(), raw);
    }

    public static Literal createBoolean(boolean bool, String raw) {
	return new AutoValue_Literal(NodeType.LITERAL, Type.BOOLEAN, 0, "", bool, RegEx.create(),
		raw);
    }

    public static Literal createRegEx(RegEx regex, String raw) {
	return new AutoValue_Literal(NodeType.LITERAL, Type.REGEX, 0, "", false, regex, raw);
    }

    public enum Type {
	UNDEFINED, NUMBER, STRING, REGEX, BOOLEAN, NULL
    }

    public abstract Type literalType();

    /** JavaScript numbers are exclusively 64 bit floating point */
    public abstract double numValue();

    public abstract String strValue();

    public abstract boolean boolValue();

    public abstract RegEx regexValue();

    public abstract String raw();

    public static Literal deserialize(JsonObject json) throws DeserializationException {
	if (!json.get("type").getAsString().equals("Literal")) {
	    throw new DeserializationException("JSON object must have Literal type.");
	}

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
	    return Literal.createRegEx(RegEx.create(regex.get("pattern").getAsString(),
		    regex.get("flags").getAsString()), raw);
	} else {
	    return Literal.createString(value.getAsString(), raw);
	}
    }

}
