package esprima4java.ast;

import com.google.auto.value.AutoValue;

@AutoValue
public abstract class RegExpLiteral {
    public static RegExpLiteral create(String pattern, String flags) {
	return new AutoValue_RegExpLiteral(pattern, flags);
    }

    public static RegExpLiteral create() {
	return new AutoValue_RegExpLiteral("", "");
    }

    public abstract String pattern();

    public abstract String flags();
}
