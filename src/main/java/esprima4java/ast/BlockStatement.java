package esprima4java.ast;

import java.util.List;

import com.google.auto.value.AutoValue;

@AutoValue
public abstract class BlockStatement extends Node {
    public static BlockStatement create(List<Node> body) {
	return new AutoValue_BlockStatement(NodeType.BLOCK_STATEMENT, body);
    }

    public abstract List<Node> body();
}
