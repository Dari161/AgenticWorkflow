package agentic.workflow;

import static org.junit.jupiter.api.Assertions.*;

import agentic.workflow.llm.*;
import org.junit.jupiter.api.Test;

public class WorkflowStepTest {
    @Test
    void testExpectsStructuredOutput() {
        SchemaType[] schemaTypes = {SchemaType.INT, SchemaType.LIST_STRING};
        StructuredOutput so = new StructuredOutput(schemaTypes);
        WorkflowStep ws = new WorkflowStep("testName", "testPrompt", "testSystemPrompt", so);
        assertTrue(ws.expectsStructuredOutput());
    }

    @Test
    void testSimulateResponseByPrimaryType() {
        SchemaType[] schemaTypes = {SchemaType.INT, SchemaType.LIST_STRING};
        StructuredOutput so = new StructuredOutput(schemaTypes);
        WorkflowStep ws = new WorkflowStep("testName", "testPrompt", "testSystemPrompt", so);
        assertEquals("0", ws.simulateResponse());
    }
}
