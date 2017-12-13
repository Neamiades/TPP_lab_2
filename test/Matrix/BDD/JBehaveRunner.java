package Matrix.BDD;

import java.util.Arrays;
import java.util.List;

import Matrix.BDD.StoriesSteps.SaveMatrixSteps;
import org.jbehave.core.embedder.Embedder;

public class JBehaveRunner {
    private static Embedder embedder = new Embedder();
    private static List<String> storyPaths = Arrays.asList("Matrix/BDD/Stories/SaveMatrix.story");

    public static void main(String[] args) {
        embedder.candidateSteps().add(new SaveMatrixSteps());
        embedder.runStoriesAsPaths(storyPaths);
    }
}

