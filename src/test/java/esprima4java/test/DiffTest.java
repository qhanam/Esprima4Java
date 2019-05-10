package esprima4java.test;

import static org.junit.Assert.assertEquals;

import java.util.Collections;
import java.util.Map;

import org.junit.jupiter.api.Test;

import com.google.common.collect.ImmutableMap;

import esprima4java.Esprima2Java;
import esprima4java.addons.ChangeInfo;
import esprima4java.addons.ChangeInfo.EditOp;
import esprima4java.addons.ChangeInfo.Version;
import esprima4java.ast.EmptyStatement;
import esprima4java.ast.ExpressionStatement;
import esprima4java.ast.Identifier;
import esprima4java.ast.Node;
import esprima4java.ast.Program;
import esprima4java.ast.Program.SourceType;
import esprima4java.ast.deserialize.DeserializationException;
import esprima4java.diff.GumTree;

class DiffTest {

    void testExpression(Node srcExpression, Node dstExpression, Map<Node, ChangeInfo> expected) {
	Program srcRoot = Program.create(SourceType.SCRIPT,
		Collections.singletonList(ExpressionStatement.create(srcExpression, null)));
	Program dstRoot = Program.create(SourceType.SCRIPT,
		Collections.singletonList(ExpressionStatement.create(dstExpression, null)));
	test(srcRoot, dstRoot, expected);
    }

    void testStatement(Node srcStatement, Node dstStatement, Map<Node, ChangeInfo> expected) {
	Program srcRoot = Program.create(SourceType.SCRIPT,
		Collections.singletonList(srcStatement));
	Program dstRoot = Program.create(SourceType.SCRIPT,
		Collections.singletonList(dstStatement));
	test(srcRoot, dstRoot, expected);
    }

    void test(Program src, Program dst, Map<Node, ChangeInfo> expected) {
	Esprima2Java.setParentRecursive(null, src);
	Esprima2Java.setParentRecursive(null, dst);

	GumTree.diff(src, dst);

	expected.forEach((node, changeInfo) -> assertEquals(changeInfo, node.changeInfo()));
    }

    @Test
    void testIdentifierUpdate() throws DeserializationException {
	Node src = Identifier.create("a");
	Node dst = Identifier.create("b");
	Map<Node, ChangeInfo> expected = new ImmutableMap.Builder<Node, ChangeInfo>()
		.put(src,
			ChangeInfo.create(false, EditOp.UPDATED, EditOp.UPDATED, dst,
				Version.SOURCE))
		.put(dst, ChangeInfo.create(false, EditOp.UPDATED, EditOp.UPDATED, src,
			Version.DESTINATION))
		.build();
	testExpression(src, dst, expected);
    }

    @Test
    void testStatementReplace() throws DeserializationException {
	Node src = EmptyStatement.create();
	Node dst = ExpressionStatement.create(Identifier.create("a"), null);
	Map<Node, ChangeInfo> expected = new ImmutableMap.Builder<Node, ChangeInfo>()
		.put(src,
			ChangeInfo.create(false, EditOp.DELETED, EditOp.DELETED, null,
				Version.SOURCE))
		.put(dst, ChangeInfo.create(false, EditOp.INSERTED, EditOp.INSERTED, null,
			Version.DESTINATION))
		.build();
	testStatement(src, dst, expected);
    }
}
