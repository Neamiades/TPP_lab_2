package Matrix.HamsterMatchers;

import Matrix.Matrix;
import com.sun.media.sound.InvalidDataException;
import org.hamcrest.Description;
import org.hamcrest.Factory;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;

public class IsInversable extends TypeSafeMatcher<Matrix> {

    public void describeTo(Description description) { description.appendText("True if matrix have inverse matrix"); }

    @Factory
    public static Matcher isInversable() { return new IsInversable(); }

    @Override
    protected boolean matchesSafely(Matrix mtx) {
        try {
            mtx.GetReverse();
        } catch (InvalidDataException e) {
            return false;
        }
        return true;
    }
}