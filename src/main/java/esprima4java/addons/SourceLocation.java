package esprima4java.addons;

import org.eclipse.jdt.annotation.Nullable;

import com.google.auto.value.AutoValue;

@AutoValue
public abstract class SourceLocation {
    public static SourceLocation create(Integer startLine, Integer endLine, Integer startColumn,
	    Integer endColumn, Integer startChar, Integer endChar) {
	return new AutoValue_SourceLocation(startLine, endLine, startColumn, endColumn, startChar,
		endChar);
    }

    @Nullable
    public abstract Integer startLine();

    @Nullable
    public abstract Integer endLine();

    @Nullable
    public abstract Integer startColumn();

    @Nullable
    public abstract Integer endColumn();

    @Nullable
    public abstract Integer startChar();

    @Nullable
    public abstract Integer endChar();

}
