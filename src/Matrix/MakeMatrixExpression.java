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

public class MakeMatrixExpression implements ExpressionListener {
    private Map<String, Matrix> matrix = new HashMap<String, Matrix>();

    private Map<String, Double> variables = new HashMap<>();


    private Stack<Matrix> mtxStack = new Stack<Matrix>();

    private Stack<Double> varStack = new Stack<>();

    private Stack<Exception> exceptionsStack = new Stack<>();


    private boolean _commandExecutionFailed;

    private CommandType Command;

    public CommandType getCommand() {
        return Command;
    }

    public boolean isCommandExecutionFailed() {
        return _commandExecutionFailed;
    }

    public ArrayList<Exception> getExceptionsList() {
        ArrayList<Exception> result = new ArrayList<>(exceptionsStack);
        return result;
    }

    public Matrix getMtxExpression() {
        if (mtxStack.empty() || mtxStack.contains(null))
            throw new InvalidParameterException("Matrix isn't in memory or variable is incorrect");

        return mtxStack.pop();
    }

    public double getVarExpression() {
        if (varStack.empty() || varStack.contains(null))
            throw new InvalidParameterException("Variable isn't in memory or variable is incorrect");

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
        if (_commandExecutionFailed)
            return;

        if (ctx.addExpr() != null)
        {
            Command = CommandType.Matrix;

            if (ctx.MtxVar() != null)
                matrix.put(ctx.MtxVar().getText(), mtxStack.peek());

        }
        else if (ctx.var() != null)
        {
            Command = CommandType.Number;

            if (ctx.var().NumVar() != null)
            {
                String varName = ctx.var().NumVar().getText();
                if(variables.containsKey(varName))
                    varStack.push(variables.get(varName));
                else
                {
                    exceptionsStack.push(new InvalidParameterException("'" + varName + "' " +
                                                                       "variable isn't in repo"));
                    _commandExecutionFailed = true;
                }
            }
            else
                varStack.push(Double.valueOf(ctx.var().Number().getText()));

            if (ctx.NumVar() != null)
                variables.put(ctx.NumVar().getText(), varStack.peek());
        }
    }

    @Override
    public void exitAddExpr(ExpressionParser.AddExprContext ctx)
    {
        if (_commandExecutionFailed)
            return;

        int additionsCount = ctx.mtxExpr().size();

        Matrix sum = mtxStack.pop();
        for (int i = 1; i < additionsCount && mtxStack.size() > 0; ++i) {
            try {
                sum = Matrix.OpAddition(sum, mtxStack.pop());
            } catch (InvalidDataException e) {
                exceptionsStack.push(e);
                _commandExecutionFailed = true;
            }
        }
        mtxStack.push(sum);
    }

    @Override
    public void exitMtxExpr(ExpressionParser.MtxExprContext ctx)
    {
        if (_commandExecutionFailed)
            return;

        int atomCount = ctx.atomExpr().size();

        if (ctx.var() != null && ctx.var().size() != 0)
        {
            int varCount = ctx.var().size();
            double mul = 1;

            for (int i = 0; i < varCount; i++)
            {
                if (ctx.var(i).NumVar() != null)
                {
                    String varName = ctx.var(i).NumVar().getText();
                    if(variables.containsKey(varName))
                        mul *= variables.get(varName);
                    else
                    {
                        exceptionsStack.push(new InvalidParameterException("'" + varName + "' " +
                                                                           "variable isn't in repo"));
                        _commandExecutionFailed = true;
                    }
                }
                else
                    mul *= Double.valueOf(ctx.var(i).Number().getText());
            }

            mtxStack.push(Matrix.OpMultiply(mul, mtxStack.pop()));
        }
        else if(atomCount > 1)
        {
            Matrix product = mtxStack.pop();
            for (int i = 1; i < atomCount; i++) {
                try {
                    product = Matrix.OpMultiply(product, mtxStack.pop());
                } catch (InvalidDataException e) {

                    exceptionsStack.push(new InvalidParameterException(e.getMessage()));
                    _commandExecutionFailed = true;
                }
            }

            mtxStack.push(product);
        }
    }

    @Override
    public void exitAtomExpr(ExpressionParser.AtomExprContext ctx)
    {
        if (_commandExecutionFailed)
            return;

        if (ctx.Inversion() != null && ctx.Inversion().size() % 2 != 0)
        {
            try {
                mtxStack.push(mtxStack.pop().GetReverse());
            } catch (InvalidDataException e) {

                exceptionsStack.push(new InvalidParameterException(e.getMessage()));
                _commandExecutionFailed = true;
            }
        }
    }

    @Override
    public void exitMtx(ExpressionParser.MtxContext ctx)
    {
        if (_commandExecutionFailed)
            return;

        Matrix newMatrix;
        if (ctx.MtxVar() == null)
        {
            int vecSize = ctx.vctr(0).var().size();

            if (vecSize == 0)
            {
                exceptionsStack.push(new InvalidParameterException("We cant set matrix with empty row"));
                _commandExecutionFailed = true;
                return;
            }

            double[][] mtx = new double[ctx.vctr().size()][vecSize];

            int i = 0;
            int j;

            for (ExpressionParser.VctrContext item : ctx.vctr()) {

                if (item.var().size() != vecSize)
                {
                    exceptionsStack.push(new InvalidParameterException("We cant set matrix with different rows length"));
                    _commandExecutionFailed = true;
                }

                j = 0;
                for (ExpressionParser.VarContext var : item.var()) {
                    if (var.NumVar() != null)
                    {
                        if (variables.containsKey(var.NumVar().getText()))
                            mtx[i][j] = variables.get(var.NumVar().getText());
                        else
                        {
                            exceptionsStack.push(new InvalidParameterException("'" + var.NumVar().getText() + "' " +
                                                                               "variable isn't in repo"));
                            _commandExecutionFailed = true;
                            return;
                        }
                    }
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
                exceptionsStack.push(new InvalidParameterException("'" + ctx.MtxVar().getText() + "'" +
                                                                   " matrix that " +
                                                                   "is not in the repository or " +
                                                                   "matrix name has invalid syntax"));
                _commandExecutionFailed = true;
                return;
            }

            newMatrix = matrix.get(ctx.MtxVar().getText());
        }
        mtxStack.push(newMatrix);
    }


    @Override
    public void exitVctr(ExpressionParser.VctrContext ctx) { }

    @Override
    public void exitVar(ExpressionParser.VarContext ctx) { }


    @Override
    public void enterAddExpr(ExpressionParser.AddExprContext ctx) { }

    @Override
    public void enterMtxExpr(ExpressionParser.MtxExprContext ctx) { }

    @Override
    public void enterAtomExpr(ExpressionParser.AtomExprContext ctx) { }

    @Override
    public void enterMtx(ExpressionParser.MtxContext ctx) { }

    @Override
    public void enterVctr(ExpressionParser.VctrContext ctx) { }

    @Override
    public void enterVar(ExpressionParser.VarContext ctx) { }


    @Override
    public void visitTerminal(TerminalNode terminalNode) { }

    @Override
    public void visitErrorNode(ErrorNode errorNode) { }


    @Override
    public void enterEveryRule(ParserRuleContext parserRuleContext) { }

    @Override
    public void exitEveryRule(ParserRuleContext parserRuleContext) { }
}