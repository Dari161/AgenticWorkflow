import java.io.BufferedReader;
import java.util.List;

import org.junit.jupiter.api.Test;

class AgentStructureTest {
    private final Class<?> agent = StructureAssertions.classNamed("agentic.workflow.Agent");
    private final Class<?> workflowStep = StructureAssertions.classNamed("agentic.workflow.WorkflowStep");
    private final Class<?> workflowFormatException = StructureAssertions.classNamed("agentic.workflow.WorkflowFormatException");

    @Test
    void classIsPublic() {
        StructureAssertions.assertPublicClass(agent);
    }

    @Test
    void fieldName() {
        StructureAssertions.assertPrivateField(agent, "name", String.class);
        StructureAssertions.assertPublicMethod(agent, "getName", String.class);
        StructureAssertions.assertPublicMethod(agent, "setName", void.class, String.class);
    }

    @Test
    void fieldSteps() {
        StructureAssertions.assertPrivateField(agent, "steps", List.class);
        StructureAssertions.assertPublicMethod(agent, "getSteps", List.class);
        StructureAssertions.assertNoPublicSetter(agent, "steps");
    }

    @Test
    void constructor() {
        StructureAssertions.assertPublicConstructor(agent, String.class);
    }

    @Test
    void addStepMethod() {
        StructureAssertions.assertPublicMethod(agent, "addStep", void.class, workflowStep);
    }

    @Test
    void getStepCountMethod() {
        StructureAssertions.assertPublicMethod(agent, "getStepCount", int.class);
    }

    @Test
    void findStepByNameMethod() {
        StructureAssertions.assertPublicMethod(agent, "findStepByName", workflowStep, String.class);
    }

    @Test
    void runMethod() {
        StructureAssertions.assertPublicMethod(agent, "run", void.class);
    }

    @Test
    void loadAgentMethod() {
        StructureAssertions.assertPublicMethod(agent, "loadAgent", agent, String.class);
    }

    @Test
    void parseStepMethod() {
        StructureAssertions.assertPrivateStaticMethod(agent, "parseStep", workflowStep, BufferedReader.class);
    }

    @Test
    void workflowFormatExceptionIsChecked() {
        StructureAssertions.assertCheckedException(workflowFormatException);
    }
}
