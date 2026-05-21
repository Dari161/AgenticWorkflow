package agentic.workflow.llm;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

public class StructuredOutputTest {
    @Test
    void testContainsExistingType() {
        SchemaType[] schemaTypes = { SchemaType.INT, SchemaType.LIST_STRING };
        StructuredOutput so = new StructuredOutput(schemaTypes);
        assertTrue(so.contains(SchemaType.INT));
    }

    @Test
    void testContainsMissingType() {
        SchemaType[] schemaTypes = { SchemaType.INT, SchemaType.LIST_STRING };
        StructuredOutput so = new StructuredOutput(schemaTypes);
        assertFalse(so.contains(SchemaType.BOOLEAN));
    }

    @Test
    void testSize() {
        SchemaType[] schemaTypes = { SchemaType.INT, SchemaType.LIST_STRING };
        StructuredOutput so = new StructuredOutput(schemaTypes);
        assertEquals(2, so.size());
    }
}
