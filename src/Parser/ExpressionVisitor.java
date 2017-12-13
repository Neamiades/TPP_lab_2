// Generated from F:/Develope projects/InteliJ/Testing/TestingLab2/Lab2/src/Parser\Expression.g4 by ANTLR 4.7
package Parser;
import org.antlr.v4.runtime.tree.ParseTreeVisitor;

/**
 * This interface defines a complete generic visitor for a parse tree produced
 * by {@link ExpressionParser}.
 *
 * @param <T> The return type of the visit operation. Use {@link Void} for
 * operations with no return type.
 */
public interface ExpressionVisitor<T> extends ParseTreeVisitor<T> {
	/**
	 * Visit a parse tree produced by {@link ExpressionParser#command}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitCommand(ExpressionParser.CommandContext ctx);
	/**
	 * Visit a parse tree produced by {@link ExpressionParser#substractExpr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitSubstractExpr(ExpressionParser.SubstractExprContext ctx);
	/**
	 * Visit a parse tree produced by {@link ExpressionParser#mtxExpr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitMtxExpr(ExpressionParser.MtxExprContext ctx);
	/**
	 * Visit a parse tree produced by {@link ExpressionParser#atomExpr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAtomExpr(ExpressionParser.AtomExprContext ctx);
	/**
	 * Visit a parse tree produced by {@link ExpressionParser#mtx}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitMtx(ExpressionParser.MtxContext ctx);
	/**
	 * Visit a parse tree produced by {@link ExpressionParser#vctr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitVctr(ExpressionParser.VctrContext ctx);
	/**
	 * Visit a parse tree produced by {@link ExpressionParser#var}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitVar(ExpressionParser.VarContext ctx);
}