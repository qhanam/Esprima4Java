package esprima4java.ast;

import java.util.Collections;
import java.util.List;

import org.eclipse.jdt.annotation.Nullable;

import com.google.auto.value.AutoValue;

import esprima4java.cfg.Cfg;
import esprima4java.cfg.CfgBuilderForStatements;

@AutoValue
public abstract class ExpressionStatement extends Node {
    public static ExpressionStatement create(Node expression, String directive) {
	return new AutoValue_ExpressionStatement(NodeType.EXPRESSION_STATEMENT, expression,
		directive);
    }

    abstract Node expression();

    @Nullable
    abstract String directive();

    @Override
    public List<Node> getChildren() {
	return Collections.singletonList(expression());
    }

    @Override
    public Cfg buildCfg() {
	return CfgBuilderForStatements.build(this);
    }

    @Override
    public Node clone() {
	return new AutoValue_ExpressionStatement(type(), expression().clone(), directive());
    }
}
