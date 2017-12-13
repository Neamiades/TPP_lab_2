package Matrix.BDD;

import java.util.Arrays;
import java.util.List;

import Matrix.BDD.StoriesSteps.SaveMatrixSteps;
import org.jbehave.core.configuration.Configuration;
import org.jbehave.core.configuration.MostUsefulConfiguration;
import org.jbehave.core.junit.JUnitStories;
import org.jbehave.core.steps.InjectableStepsFactory;
import org.jbehave.core.steps.InstanceStepsFactory;
import org.junit.runner.RunWith;

import com.github.valfirst.jbehave.junit.monitoring.JUnitReportingRunner;

@RunWith(JUnitReportingRunner.class)
public class SaveMatrixStory extends JUnitStories {
    @Override
    public Configuration configuration() {
        return new MostUsefulConfiguration();
    }

    @Override
    public InjectableStepsFactory stepsFactory() {
        return new InstanceStepsFactory(configuration(), new SaveMatrixSteps());
    }

    @Override
    protected List<String> storyPaths() {
        return Arrays.asList("Matrix/BDD/Stories/SaveMatrix.story");
    }
}

