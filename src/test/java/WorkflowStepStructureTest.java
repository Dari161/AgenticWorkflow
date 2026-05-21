import org.junit.jupiter.api.Test;

class WorkflowStepStructureTest {
    private final Class<?> schemaType = StructureAssertions.classNamed("agentic.workflow.llm.SchemaType");
    private final Class<?> structuredOutput = StructureAssertions.classNamed("agentic.workflow.llm.StructuredOutput");
    private final Class<?> workflowStep = StructureAssertions.classNamed("agentic.workflow.WorkflowStep");

    @Test
    void classIsPublic() {
        StructureAssertions.assertPublicClass(workflowStep);
    }

    @Test
    void fieldName() {
        StructureAssertions.assertPrivateField(workflowStep, "name", String.class);
        StructureAssertions.assertPublicMethod(workflowStep, "getName", String.class);
        StructureAssertions.assertPublicMethod(workflowStep, "setName", void.class, String.class);
    }

    @Test
    void fieldPrompt() {
        StructureAssertions.assertPrivateField(workflowStep, "prompt", String.class);
        StructureAssertions.assertPublicMethod(workflowStep, "getPrompt", String.class);
        StructureAssertions.assertPublicMethod(workflowStep, "setPrompt", void.class, String.class);
    }

    @Test
    void fieldSystemPrompt() {
        StructureAssertions.assertPrivateField(workflowStep, "systemPrompt", String.class);
        StructureAssertions.assertPublicMethod(workflowStep, "getSystemPrompt", String.class);
        StructureAssertions.assertPublicMethod(workflowStep, "setSystemPrompt", void.class, String.class);
    }

    @Test
    void fieldStructuredOutput() {
        StructureAssertions.assertPrivateField(workflowStep, "structuredOutput", structuredOutput);
        StructureAssertions.assertPublicMethod(workflowStep, "getStructuredOutput", structuredOutput);
        StructureAssertions.assertPublicMethod(workflowStep, "setStructuredOutput", void.class, structuredOutput);
    }

    @Test
    void constructor() {
        StructureAssertions.assertPublicConstructor(workflowStep, String.class, String.class, String.class, structuredOutput);
    }

    @Test
    void expectsStructuredOutputMethod() {
        StructureAssertions.assertPublicMethod(workflowStep, "expectsStructuredOutput", boolean.class);
    }

    @Test
    void simulateResponseMethod() {
        StructureAssertions.assertPublicMethod(workflowStep, "simulateResponse", String.class);
    }

    @Test
    void structuredOutputUsesSchemaType() {
        StructureAssertions.assertPublicMethod(structuredOutput, "contains", boolean.class, schemaType);
    }
}
