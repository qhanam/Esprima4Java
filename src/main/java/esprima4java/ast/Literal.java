package esprima4java.ast;

import com.google.auto.value.AutoValue;

@AutoValue
public abstract class Literal extends Node {
    public static Literal createNull(String raw) {
	return new AutoValue_Literal(NodeType.LITERAL, Type.NULL, 0, "", false,
		RegExpLiteral.create(), raw);
    }

    public static Literal createNumber(double number, String raw) {
	return new AutoValue_Literal(NodeType.LITERAL, Type.NUMBER, number, "", false,
		RegExpLiteral.create(), raw);
    }

    public static Literal createString(String string, String raw) {
	return new AutoValue_Literal(NodeType.LITERAL, Type.STRING, 0, string, false,
		RegExpLiteral.create(), raw);
    }

    public static Literal createBoolean(boolean bool, String raw) {
	return new AutoValue_Literal(NodeType.LITERAL, Type.BOOLEAN, 0, "", bool,
		RegExpLiteral.create(), raw);
    }

    public static Literal createRegEx(RegExpLiteral regex, String raw) {
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

    public abstract RegExpLiteral regexValue();

    public abstract String raw();

}
