package Matrix;
import Parser.ExpressionLexer;
import Parser.ExpressionParser;
import com.sun.media.sound.InvalidDataException;
import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTreeWalker;
import java.util.Scanner;

public class Program
{
    //k * A + B * C^-1
    public static void main(String[] args)
    {
        System.out.println("Input your expression to work with matrix");
        System.out.println("Input \'exit\' to end the program.\n");
        MakeMatrixExpression exprMaker = new MakeMatrixExpression();

        String inputStr = new Scanner(System.in).nextLine();

        while (inputStr != null && !inputStr.equals("exit"))
        {
            try
            {
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
                switch (exprMaker.getCommand())
                {
                    case Matrix:
                        exprMaker.getMtxExpression().printToConsole();
                        break;
                    case Number:
                        System.out.println(exprMaker.getVarExpression());
                        break;
                    default:
                        throw new InvalidDataException("WTF");
                }
                inputStr = new Scanner(System.in).nextLine();
            }
            catch (Exception e)
            {
                System.err.println(e);
                inputStr = new Scanner(System.in).nextLine();
            }
        }
    }
}