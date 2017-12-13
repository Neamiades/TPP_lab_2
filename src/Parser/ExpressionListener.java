// Generated from F:/Develope projects/InteliJ/Testing/TestingLab2/Lab2/src/Parser\Expression.g4 by ANTLR 4.7
package Parser;
import org.antlr.v4.runtime.tree.ParseTreeListener;

/**
 * This interface defines a complete listener for a parse tree produced by
 * {@link ExpressionParser}.
 */
public interface ExpressionListener extends ParseTreeListener {
	/**
	 * Enter a parse tree produced by {@link ExpressionParser#command}.
	 * @param ctx the parse tree
	 */
	void enterCommand(ExpressionParser.CommandContext ctx);
	/**
	 * Exit a parse tree produced by {@link ExpressionParser#command}.
	 * @param ctx the parse tree
	 */
	void exitCommand(ExpressionParser.CommandContext ctx);
	/**
	 * Enter a parse tree produced by {@link ExpressionParser#substractExpr}.
	 * @param ctx the parse tree
	 */
	void enterSubstractExpr(ExpressionParser.SubstractExprContext ctx);
	/**
	 * Exit a parse tree produced by {@link ExpressionParser#substractExpr}.
	 * @param ctx the parse tree
	 */
	void exitSubstractExpr(ExpressionParser.SubstractExprContext ctx);
	/**
	 * Enter a parse tree produced by {@link ExpressionParser#mtxExpr}.
	 * @param ctx the parse tree
	 */
	void enterMtxExpr(ExpressionParser.MtxExprContext ctx);
	/**
	 * Exit a parse tree produced by {@link ExpressionParser#mtxExpr}.
	 * @param ctx the parse tree
	 */
	void exitMtxExpr(ExpressionParser.MtxExprContext ctx);
	/**
	 * Enter a parse tree produced by {@link ExpressionParser#atomExpr}.
	 * @param ctx the parse tree
	 */
	void enterAtomExpr(ExpressionParser.AtomExprContext ctx);
	/**
	 * Exit a parse tree produced by {@link ExpressionParser#atomExpr}.
	 * @param ctx the parse tree
	 */
	void exitAtomExpr(ExpressionParser.AtomExprContext ctx);
	/**
	 * Enter a parse tree produced by {@link ExpressionParser#mtx}.
	 * @param ctx the parse tree
	 */
	void enterMtx(ExpressionParser.MtxContext ctx);
	/**
	 * Exit a parse tree produced by {@link ExpressionParser#mtx}.
	 * @param ctx the parse tree
	 */
	void exitMtx(ExpressionParser.MtxContext ctx);
	/**
	 * Enter a parse tree produced by {@link ExpressionParser#vctr}.
	 * @param ctx the parse tree
	 */
	void enterVctr(ExpressionParser.VctrContext ctx);
	/**
	 * Exit a parse tree produced by {@link ExpressionParser#vctr}.
	 * @param ctx the parse tree
	 */
	void exitVctr(ExpressionParser.VctrContext ctx);
	/**
	 * Enter a parse tree produced by {@link ExpressionParser#var}.
	 * @param ctx the parse tree
	 */
	void enterVar(ExpressionParser.VarContext ctx);
	/**
	 * Exit a parse tree produced by {@link ExpressionParser#var}.
	 * @param ctx the parse tree
	 */
	void exitVar(ExpressionParser.VarContext ctx);
}