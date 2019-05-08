package esprima4java.addons;

import com.google.auto.value.AutoValue;

import esprima4java.ast.Node;

@AutoValue
public abstract class ChangeInfo {
    public static ChangeInfo create(boolean moved, EditOp editOp, EditOp propEditOp, Node mapped,
	    Version version) {
	return new AutoValue_ChangeInfo(moved, editOp, propEditOp, mapped, version);
    }

    public enum EditOp {
	UNCHANGED, INSERTED, DELETED, UPDATED
    }

    public enum Version {
	SOURCE, DESTINATION
    }

    /** {@code true} if this node has a new parent. */
    public abstract boolean moved();

    /** The edit operation applied to this node. */
    public abstract EditOp editOp();

    /** The propagated edit operation applied to this node. */
    public abstract EditOp propEditOp();

    /** The matched node in the other version. */
    public abstract Node mapped();

    /** The version that contains this node. */
    public abstract Version version();

}
