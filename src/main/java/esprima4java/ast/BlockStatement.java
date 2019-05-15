package esprima4java.ast;

import java.util.ArrayList;
import java.util.List;

import com.google.auto.value.AutoValue;
import com.google.auto.value.extension.memoized.Memoized;

import esprima4java.cfg.Cfg;
import esprima4java.cfg.builders.CfgBuilderForBlockStatements;

@AutoValue
public abstract class BlockStatement extends Node {
    public static BlockStatement create(List<Node> body) {
	return new AutoValue_BlockStatement(NodeType.BLOCK_STATEMENT, body);
    }

    public abstract List<Node> body();

    @Override
    @Memoized
    public List<Node> getChildren() {
	return body();
    }

    @Override
    public Cfg buildCfg() {
	return CfgBuilderForBlockStatements.build(this);
    }

    @Override
    public Node clone() {
	List<Node> bodyCopy = new ArrayList<>();
	body().forEach(statement -> bodyCopy.add(statement.clone()));
	return new AutoValue_BlockStatement(type(), bodyCopy);
    }
}
