import org.junit.jupiter.api.Test;

class SchemaTypeStructureTest {
    @Test
    void elements() {
        Class<?> schemaType = StructureAssertions.classNamed("agentic.workflow.llm.SchemaType");

        StructureAssertions.assertPublicEnumElements(
                schemaType,
                "INT",
                "STRING",
                "BOOLEAN",
                "LIST_INT",
                "LIST_STRING",
                "MAP_STRING_STRING"
        );
    }
}
