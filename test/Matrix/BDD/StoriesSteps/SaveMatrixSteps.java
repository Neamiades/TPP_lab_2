package Matrix.BDD.StoriesSteps;

import static org.junit.Assert.assertTrue;

import Matrix.MakeMatrixExpression;
import Matrix.Matrix;
import Matrix.CommandType;
import Parser.ExpressionLexer;
import Parser.ExpressionParser;
import com.sun.media.sound.InvalidDataException;
import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTreeWalker;
import org.jbehave.core.annotations.Given;
import org.jbehave.core.annotations.Then;
import org.jbehave.core.annotations.When;
import org.jbehave.core.steps.Steps;

import java.util.ArrayList;

public class SaveMatrixSteps extends Steps
{
    private MakeMatrixExpression exprMaker;
    private String exeptionMessage;
    private boolean wasExeption;

    private final void executeCommand(String str)
    {
        try
        {
            new ParseTreeWalker().walk(exprMaker,
                    new ExpressionParser(
                        new CommonTokenStream(
                            new ExpressionLexer(
                                new ANTLRInputStream(str))))
                            .command());
        }
        catch (Exception e) {
            wasExeption = true;
            exeptionMessage = e.getMessage();
        }
    }

    @Given("matrix expression in a string \"$str\"")
    public void initMtxExpr(String str) {
        wasExeption = false;
        exprMaker = new MakeMatrixExpression();
        executeCommand(str);
    }

    @When("next matrix expression in a string will be like \"$str\"")
    public void nextMtxExpr(String str) {
        wasExeption = false;
        executeCommand(str);
    }

    @Then("get the output string as \"$str\"")
    public void getOperationOutput(String str) {
        String output = "";

        if (!wasExeption)
        {
            if (exprMaker.isCommandExecutionFailed()) {
                wasExeption = true;
                output = "";
                ArrayList<Exception> exList = exprMaker.getExceptionsList();

                for (int i = 0; i < exList.size() - 1; i++)
                    output += exList.get(i).getMessage() + ";";

                output += exList.get(exList.size() - 1).getMessage();
            }
            else if(exprMaker.getCommand() == CommandType.Matrix)
                output = exprMaker.getMtxExpression().toString();
            else if (exprMaker.getCommand() == CommandType.Number)
                output = exprMaker.getMtxExpression().toString();
        }
        else
        {
            wasExeption = false;
            output = exeptionMessage;
        }
        assertTrue(output.equals(str));
    }
}
