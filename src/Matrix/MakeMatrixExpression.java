package Matrix;

import Parser.ExpressionListener;
import Parser.ExpressionParser;
import com.sun.media.sound.InvalidDataException;
import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.tree.ErrorNode;
import org.antlr.v4.runtime.tree.TerminalNode;

import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

/**
 * Created by Neami on 08.12.2017.
 */
public class MakeMatrixExpression implements ExpressionListener {
    private Map<String, Matrix> matrix = new HashMap<String, Matrix>();

    private Map<String, Double> variables = new HashMap<String, Double>();

    private Stack<Matrix> mtxStack = new Stack<Matrix>();

    private Stack<Double> varStack = new Stack<Double>();

    private Stack<Exception> exceptionsStack = new Stack<Exception>();

    private boolean _commandExecutionFailed;

    public boolean MtxCommand;

    public boolean isCommandExecutionFailed() {
        return _commandExecutionFailed;
    }

    public ArrayList<Exception> getExceptionsList() {
        ArrayList<Exception> result = new ArrayList<Exception>(exceptionsStack);
        return result;
    }

    public Matrix getMtxExpression() {
        if (mtxStack.empty() || mtxStack.contains(null))
        {
            exceptionsStack.push(new InvalidParameterException("Matrix isn't in memory or variable is incorrect"));
            _commandExecutionFailed = true;
        }

        return mtxStack.pop();
    }

    public double getVarExpression() {
        if (varStack.empty() || varStack.contains(null))
        {
            exceptionsStack.push(new InvalidParameterException("variable isn't in memory or variable is incorrect"));
            _commandExecutionFailed = true;
        }

        return varStack.pop();
    }

    @Override public void enterCommand(ExpressionParser.CommandContext ctx)
    {
        _commandExecutionFailed = false;
        exceptionsStack.clear();
        mtxStack.clear();
        varStack.clear();
    }

    @Override public void exitCommand(ExpressionParser.CommandContext ctx)
    {
        if (ctx.MtxVar() != null)
        {
            String mtxVar = ctx.MtxVar().getText();

            if (matrix.containsKey(mtxVar))
                matrix.replace(mtxVar, mtxStack.peek());
            else
                matrix.put(mtxVar, mtxStack.peek());
        }
        else if (ctx.var() != null)
        {
            double number = ctx.var().NumVar() == null
                    ? Double.valueOf(ctx.var().Number().getText())
                    : variables.get(ctx.var().NumVar().getText());

            if (ctx.NumVar() != null)
            {
                String var = ctx.NumVar().getText();

                if (variables.containsKey(var))
                {
                    variables.replace(var, number);
                }
                else
                    variables.put(var, number);
            }
            varStack.push(number);

            MtxCommand = false;
            return;
        }
        MtxCommand = true;
    }

    @Override public void enterSubstractExpr(ExpressionParser.SubstractExprContext ctx) { }

    @Override public void exitSubstractExpr(ExpressionParser.SubstractExprContext ctx) {

        Stack<Matrix> substrctMtx = new Stack<Matrix>();
        int substractionsCount = ctx.mtxExpr().size();

        for (int i = 0; i < substractionsCount && mtxStack.size() > 0; ++i)
            substrctMtx.push(mtxStack.pop());
        Matrix sum = null;
        if (substrctMtx.size() > 0)
            sum = substrctMtx.pop();
        for (int i = 1; i < substractionsCount && substrctMtx.size() > 0; ++i) {
            try {
                sum = Matrix.OpSubtraction(sum, substrctMtx.pop());
            } catch (InvalidDataException e) {
                exceptionsStack.push(e);
                _commandExecutionFailed = true;
            }
        }
        mtxStack.push(sum);
    }

    @Override public void enterMtxExpr(ExpressionParser.MtxExprContext ctx) { }

    @Override public void exitMtxExpr(ExpressionParser.MtxExprContext ctx) {
        if (ctx.MtxOperator() == null || ctx.MtxOperator(0) == null)
            return;
        String op = ctx.MtxOperator(0).getText();

        if (op.equals("T"))
        {
            mtxStack.push(mtxStack.pop().GetTransponent());
        }
        else
        {
            Matrix tempMtx = mtxStack.pop();
            try {
                mtxStack.push(tempMtx.GetReverse());
            } catch (InvalidDataException e) {
                mtxStack.push(tempMtx);
                exceptionsStack.push(e);
                _commandExecutionFailed = true;
            }
        }
    }

    @Override public void enterAtomExpr(ExpressionParser.AtomExprContext ctx) { }

    @Override public void exitAtomExpr(ExpressionParser.AtomExprContext ctx)
    {
        if (ctx.mtxExpr() != null)
        {
            if (ctx.var().NumVar() == null)
                mtxStack.push(Matrix.OpMultiply(Double.valueOf(ctx.var().Number().getText()), mtxStack.pop()));
            else
                mtxStack.push(Matrix.OpMultiply(variables.get(ctx.var().NumVar().getText()), mtxStack.pop()));
        }
    }

    @Override public void enterMtx(ExpressionParser.MtxContext ctx) { }

    @Override public void exitMtx(ExpressionParser.MtxContext ctx)
    {
        Matrix newMatrix;
        if (ctx.MtxVar() == null)
        {
            int vecSize = ctx.vctr(0).var().size();

            if (vecSize == 0)
            {
                exceptionsStack.push(new InvalidParameterException("We cant set matrix with empty row"));
                _commandExecutionFailed = true;
            }

            double[][] mtx = new double[ctx.vctr().size()][vecSize];

            int i = 0, j = 0;
            for (ExpressionParser.VctrContext item : ctx.vctr()) {

                if (item.var().size() != vecSize)
                {
                    exceptionsStack.push(new InvalidParameterException("We cant set matrix with different rows length"));
                    _commandExecutionFailed = true;
                }

                j = 0;
                for (ExpressionParser.VarContext var : item.var()) {
                    if (var.NumVar() != null)
                        mtx[i][j] = variables.get(var.NumVar().getText());
                    else if (var.Number() != null)
                        mtx[i][j] = Double.valueOf(var.Number().getText());
                    else
                    {
                        exceptionsStack.push(new InvalidParameterException("Unallowed empty variable in matrix row"));
                        _commandExecutionFailed = true;
                    }
                    j++;
                }
                i++;
            }

            newMatrix = new Matrix(mtx);
        }
        else
        {
            if (matrix.get(ctx.MtxVar().getText()) == null)
            {
                exceptionsStack.push(new InvalidParameterException("An expression refers to a matrix that is not in the repository or " +
                        "matrix name has invalid syntax"));
                _commandExecutionFailed = true;
                return;
            }

            newMatrix = matrix.get(ctx.MtxVar().getText());
        }
        mtxStack.push(newMatrix);
    }

    @Override public void enterVctr(ExpressionParser.VctrContext ctx) { }

    @Override public void exitVctr(ExpressionParser.VctrContext ctx) { }

    @Override public void enterVar(ExpressionParser.VarContext ctx) { }

    @Override public void exitVar(ExpressionParser.VarContext ctx) { }


    @Override public void enterEveryRule(ParserRuleContext ctx) { }

    @Override public void exitEveryRule(ParserRuleContext ctx) { }

    @Override public void visitTerminal(TerminalNode node) { }

    @Override public void visitErrorNode(ErrorNode node) { }
}
