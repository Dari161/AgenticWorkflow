import org.junit.jupiter.api.Test;

class WorkflowFormatExceptionStructureTest {
    private final Class<?> exceptionType = StructureAssertions.classNamed("agentic.workflow.WorkflowFormatException");

    @Test
    void isCheckedException() {
        StructureAssertions.assertPublicClass(exceptionType);
        StructureAssertions.assertCheckedException(exceptionType);
    }

    @Test
    void messageConstructor() {
        StructureAssertions.assertPublicConstructor(exceptionType, String.class);
    }

    @Test
    void messageAndCauseConstructor() {
        StructureAssertions.assertPublicConstructor(exceptionType, String.class, Throwable.class);
    }
}
