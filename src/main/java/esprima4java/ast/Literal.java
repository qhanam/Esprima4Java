package esprima4java.ast;

import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;

import esprima4java.DeserializationException;

public class Literal extends AstNode {

    public enum Type {
	UNDEFINED, NUMBER, STRING, REGEX, BOOLEAN, NULL
    }

    /**
     * JavaScript numbers are stored exclusively as 64 bit floating point values.
     */
    private double numberValue;
    private String stringValue;
    private boolean booleanValue;
    private RegEx regexValue;

    private Type type;

    private String raw;

    private Literal() {
	super(NodeType.LITERAL);
    }

    public Type getLiteralType() {
	return type;
    }

    public String getStringValue() {
	return stringValue;
    }

    public double getNumberValue() {
	return numberValue;
    }

    public boolean getBooleanValue() {
	return booleanValue;
    }

    public RegEx getRegExValue() {
	return regexValue;
    }

    public String getRaw() {
	return raw;
    }

    public static Builder builder() {
	return new Builder();
    }

    public static Literal deserialize(JsonObject json) throws DeserializationException {
	if (!json.get("type").getAsString().equals("Literal")) {
	    throw new DeserializationException("JSON object must have Literal type.");
	}

	Literal.Builder builder = Literal.builder().raw(json.get("raw").getAsString());
	if (json.get("value").isJsonNull()) {
	    return builder.keyNull().build();
	}

	JsonPrimitive value = (JsonPrimitive) json.get("value");
	if (value.isNumber())
	    builder.number(json.get("value").getAsDouble());
	else if (value.isBoolean())
	    builder.bool(value.getAsBoolean());
	else if (json.has("regex")) {
	    JsonObject regex = (JsonObject) json.get("regex");
	    builder.regex(RegEx.builder().pattern(regex.get("pattern").getAsString())
		    .flags(regex.get("flags").getAsString()).build());
	} else if (value.isJsonNull()) {
	} else {
	    builder.string(value.getAsString());
	}

	return builder.build();
    }

    @Override
    public boolean equals(Object o) {
	if (!(o instanceof Literal))
	    return false;
	Literal that = (Literal) o;
	return this.type.equals(that.type) && this.numberValue == that.numberValue
		&& this.stringValue.equals(that.stringValue)
		&& this.booleanValue == that.booleanValue && this.raw.equals(that.raw);
    }

    public static class Builder {

	private Type type;
	private double numberValue;
	private String stringValue;
	private boolean booleanValue;
	private RegEx regexValue;

	private String raw;

	private Builder() {
	    type = Type.UNDEFINED;
	    stringValue = "";
	    booleanValue = false;
	    regexValue = RegEx.builder().pattern("").flags("").build();
	}

	public Literal build() {
	    Literal literal = new Literal();
	    literal.type = type;
	    literal.numberValue = numberValue;
	    literal.stringValue = stringValue;
	    literal.booleanValue = booleanValue;
	    literal.regexValue = regexValue;
	    literal.raw = raw;
	    return literal;
	}

	public Builder number(double numberValue) {
	    this.type = Type.NUMBER;
	    this.numberValue = numberValue;
	    return this;
	}

	public Builder string(String stringValue) {
	    this.type = Type.STRING;
	    this.stringValue = stringValue;
	    return this;
	}

	public Builder regex(RegEx regexValue) {
	    this.type = Type.REGEX;
	    this.regexValue = regexValue;
	    return this;
	}

	public Builder bool(boolean booleanValue) {
	    this.type = Type.BOOLEAN;
	    this.booleanValue = booleanValue;
	    return this;
	}

	public Builder keyThis() {
	    this.type = Type.NULL;
	    return this;
	}

	public Builder keyNull() {
	    this.type = Type.NULL;
	    return this;
	}

	public Builder raw(String raw) {
	    this.raw = raw;
	    return this;
	}

    }

}
