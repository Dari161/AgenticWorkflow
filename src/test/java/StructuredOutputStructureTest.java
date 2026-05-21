import java.lang.reflect.Array;

import org.junit.jupiter.api.Test;

class StructuredOutputStructureTest {
    private final Class<?> schemaType = StructureAssertions.classNamed("agentic.workflow.llm.SchemaType");
    private final Class<?> structuredOutput = StructureAssertions.classNamed("agentic.workflow.llm.StructuredOutput");

    @Test
    void classIsPublic() {
        StructureAssertions.assertPublicClass(structuredOutput);
    }

    @Test
    void fieldSchemaTypes() {
        Class<?> schemaTypeArray = Array.newInstance(schemaType, 0).getClass();

        StructureAssertions.assertPrivateField(structuredOutput, "schemaTypes", schemaTypeArray);
        StructureAssertions.assertPublicMethod(structuredOutput, "getSchemaTypes", schemaTypeArray);
        StructureAssertions.assertNoPublicSetter(structuredOutput, "schemaTypes");
    }

    @Test
    void constructor() {
        Class<?> schemaTypeArray = Array.newInstance(schemaType, 0).getClass();

        StructureAssertions.assertPublicConstructor(structuredOutput, schemaTypeArray);
    }

    @Test
    void containsMethod() {
        StructureAssertions.assertPublicMethod(structuredOutput, "contains", boolean.class, schemaType);
    }

    @Test
    void sizeMethod() {
        StructureAssertions.assertPublicMethod(structuredOutput, "size", int.class);
    }
}
