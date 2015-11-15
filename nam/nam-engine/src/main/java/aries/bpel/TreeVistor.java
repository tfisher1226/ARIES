package aries.bpel;

import org.eclipse.bpel.xpath10.AllNodeStep;
import org.eclipse.bpel.xpath10.BinaryExpr;
import org.eclipse.bpel.xpath10.CommentNodeStep;
import org.eclipse.bpel.xpath10.FilterExpr;
import org.eclipse.bpel.xpath10.FunctionCallExpr;
import org.eclipse.bpel.xpath10.LiteralExpr;
import org.eclipse.bpel.xpath10.LocationPath;
import org.eclipse.bpel.xpath10.NameStep;
import org.eclipse.bpel.xpath10.NumberExpr;
import org.eclipse.bpel.xpath10.PathExpr;
import org.eclipse.bpel.xpath10.Predicate;
import org.eclipse.bpel.xpath10.ProcessingInstructionNodeStep;
import org.eclipse.bpel.xpath10.TextNodeStep;
import org.eclipse.bpel.xpath10.UnaryExpr;
import org.eclipse.bpel.xpath10.UnionExpr;
import org.eclipse.bpel.xpath10.VariableReferenceExpr;
import org.eclipse.bpel.xpath10.Visitor;

import aries.codegen.util.Buf;


@SuppressWarnings({"boxing", "nls"})
public class TreeVistor extends Visitor {

	private Buf buf;

	
	public TreeVistor(Buf buf) {
		this.buf = buf;
	}
	
	@Override
	protected void visit (AllNodeStep step) {
		print("AllNodeStep> %s ", step.getText());
		super.visit(step);
	}

	@Override
	protected void visit (BinaryExpr expr) {
		print("BinaryExpr> %s", expr.getText());
		super.visit(expr);
	}

	@Override
	protected void visit(CommentNodeStep step) {
		print("CommentNodeStep> %s", step.getText());
		super.visit(step);
	}

	@Override
	protected void visit(FilterExpr expr) {
		print("FilterExpr> %s", expr.getText());
		super.visit(expr);
	}

	@Override
	protected void visit(FunctionCallExpr expr) {
		print("FunctionCallExpr> %s", expr.getText());
		super.visit(expr);
	}

	@Override
	protected void visit(LiteralExpr expr) {
		print("LiteralExpr> %s", expr.getText());
		super.visit(expr);
	}

	@Override
	protected void visit(LocationPath expr) {
		print("LocationPath> %s", expr.getText());
		super.visit(expr);
	}

	@Override
	protected void visit(NameStep step) {
		print("NameStep> %s", step.getText());
		super.visit(step);
	}

	@Override
	protected void visit(NumberExpr expr) {
		print("NumberExpr> %s", expr.getText());
		super.visit(expr);
	}

	@Override
	protected void visit(PathExpr expr) {
		print("PathExpr> %s", expr.getText());
		super.visit(expr);
	}

	@Override
	protected void visit(Predicate predicate) {
		print("Predicate> %s", predicate.getText());
		super.visit(predicate);
	}

	@Override
	protected void visit(ProcessingInstructionNodeStep step) {
		print("ProcessingInstructionNodeStep> %s", step.getText());
		super.visit(step);
	}

	@Override
	protected void visit(TextNodeStep step) {
		print("UnaryExpr> %s", step.getText());
		super.visit(step);
	}

	@Override
	protected void visit(UnaryExpr expr) {
		print("UnaryExpr> %s", expr.getText());
		super.visit(expr);
	}

	@Override
	protected void visit(UnionExpr expr) {
		print("UnionExpr> %s", expr.getText());
		super.visit(expr);
	}

	@Override
	protected void visit(VariableReferenceExpr expr) {
		print("VariableReferenceExpr> %s", expr.getText());
		buf.put(expr.getText());
		super.visit(expr);
	}

	void print(String msg, Object ... args) {
		System.out.printf(msg+"\n", args);
	}
	
}
