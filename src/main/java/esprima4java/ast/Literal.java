package esprima4java.ast;

import org.eclipse.jdt.annotation.Nullable;

import com.google.auto.value.AutoValue;

@AutoValue
public abstract class Literal extends Node {
    public static Literal createNull(String raw) {
	return new AutoValue_Literal(NodeType.LITERAL, Type.NULL, null, null, null, null, raw);
    }

    public static Literal createNumber(double number, String raw) {
	return new AutoValue_Literal(NodeType.LITERAL, Type.NUMBER, number, null, null, null, raw);
    }

    public static Literal createString(String string, String raw) {
	return new AutoValue_Literal(NodeType.LITERAL, Type.STRING, null, string, null, null, raw);
    }

    public static Literal createBoolean(boolean bool, String raw) {
	return new AutoValue_Literal(NodeType.LITERAL, Type.BOOLEAN, null, null, bool, null, raw);
    }

    public static Literal createRegEx(RegExpLiteral regex, String raw) {
	return new AutoValue_Literal(NodeType.LITERAL, Type.REGEX, null, null, null, regex, raw);
    }

    public enum Type {
	UNDEFINED, NUMBER, STRING, REGEX, BOOLEAN, NULL
    }

    public abstract Type literalType();

    /** JavaScript numbers are exclusively 64 bit floating point */
    @Nullable
    public abstract Double numValue();

    @Nullable
    public abstract String strValue();

    @Nullable
    public abstract Boolean boolValue();

    @Nullable
    public abstract RegExpLiteral regexValue();

    @Nullable
    public abstract String raw();

    @Override
    public Node clone() {
	return new AutoValue_Literal(type(), literalType(), numValue(), strValue(), boolValue(),
		regexValue(), raw());
    }

}
