package Matrix.HamsterMatchers;

import Matrix.Matrix;
import org.hamcrest.Description;
import org.hamcrest.Factory;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;

/**
 * Created by Neami on 07.12.2017.
 */
public class IsEquals extends TypeSafeMatcher<Matrix> {
    private final Matrix mtx;

    public IsEquals(Matrix mtx) {
        this.mtx = mtx;
    }

    public void describeTo(Description description) { description.appendText("True if matrix are equals"); }

    @Factory
    public static Matcher isEquals(Matrix mtx) { return new IsEquals(mtx); }

    @Override
    protected boolean matchesSafely(Matrix mtx) {
        if (mtx.getHeight() == this.mtx.getHeight() && mtx.getWidth() == this.mtx.getWidth())
        {
            for (int i = 0; i < mtx.getHeight(); i++)
            {
                for (int j = 0; j < mtx.getWidth(); j++)
                {

                    if (Math.abs(Math.abs(mtx.getItem(i, j)) - Math.abs(this.mtx.getItem(i, j))) > 0.000001)
                        return false;
                }
            }
            return true;
        }
        return false;
    }
}
