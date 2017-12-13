package Matrix.HamsterMatchers;

import Matrix.Matrix;
import org.hamcrest.Description;
import org.hamcrest.Factory;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;

/**
 * Created by Neami on 07.12.2017.
 */
public class HaveEqualsSize extends TypeSafeMatcher<Matrix> {
    private final Matrix mtx;

    public HaveEqualsSize(Matrix mtx) {
        this.mtx = mtx;
    }

    public void describeTo(Description description) { description.appendText("True if matrix have equals size"); }

    @Factory
    public static Matcher haveEqualsSize(Matrix mtx) { return new HaveEqualsSize(mtx); }

    @Override
    protected boolean matchesSafely(Matrix mtx) {
        return mtx.getHeight() == this.mtx.getHeight() && mtx.getWidth() == this.mtx.getWidth();
    }
}

