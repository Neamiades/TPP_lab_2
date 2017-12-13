package Matrix;
import Parser.ExpressionLexer;
import Parser.ExpressionParser;
import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTreeWalker;
import java.util.Scanner;

public class Program
{
    //C^-1 - k * B^T
    public static void main(String[] args)
    {
        System.out.println("Input your expression to work with matrix");
        System.out.println("Input \'exit\' to end the program.\n");
        MakeMatrixExpression exprMaker = new MakeMatrixExpression();

        String inputStr = new Scanner(System.in).nextLine();

        while (inputStr != null && !inputStr.equals("exit"))
        {
//            try
//            {
                new ParseTreeWalker().walk(
                        exprMaker,
                        new ExpressionParser(
                                new CommonTokenStream(
                                        new ExpressionLexer(
                                                new ANTLRInputStream(inputStr))))
                                .command());
                if (exprMaker.isCommandExecutionFailed())
                {
                    for (Exception ex:exprMaker.getExceptionsList())
                        System.err.println(ex.getMessage());
                    inputStr = new Scanner(System.in).nextLine();
                    continue;
                }
                if (exprMaker.MtxCommand)
                    Matrix.Print(exprMaker.getMtxExpression());
                else
                    System.out.println(exprMaker.getVarExpression());
                inputStr = new Scanner(System.in).nextLine();
//            }
//            catch (Exception e)
//            {
//                System.err.println(e);
//                inputStr = new Scanner(System.in).nextLine();
//            }
        }
    }
}