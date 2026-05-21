package agentic.workflow;

import static org.junit.jupiter.api.Assertions.*;

import java.io.*;
import java.nio.file.Path;

import agentic.workflow.llm.*;
import org.junit.jupiter.api.Test;

public class AgentTest {
    private static String testResource(String filename) {
        return Path.of("src", "test", "resources", filename).toString();
    }

    @Test
    void testStepCount() {
        SchemaType[] schemaTypes = { SchemaType.INT, SchemaType.LIST_STRING };

        StructuredOutput so = new StructuredOutput(schemaTypes);

        WorkflowStep ws1 = new WorkflowStep("testName", "testPrompt", "testSystemPrompt", so);
        WorkflowStep ws2 = new WorkflowStep("testName2", "testPrompt2", "testSystemPrompt2", so);

        Agent a = new Agent("testName");

        assertEquals(0, a.getStepCount());
        a.addStep(ws1);
        assertEquals(1, a.getStepCount());
        a.addStep(ws2);
        assertEquals(2, a.getStepCount());
    }

    @Test
    void testAddDuplicateStepRejected() {
        SchemaType[] schemaTypes = { SchemaType.INT, SchemaType.LIST_STRING };
        StructuredOutput so = new StructuredOutput(schemaTypes);
        WorkflowStep ws1 = new WorkflowStep("duplicateName", "testPrompt", "testSystemPrompt", so);
        Agent a = new Agent("testName");

        a.addStep(ws1);

        WorkflowStep ws2 = new WorkflowStep("duplicateName", "testPrompt2", "testSystemPrompt2", so);

        try {
            a.addStep(ws2);
            fail();
        } catch (IllegalArgumentException e) {

        }
    }

    @Test
    void findStepByName() {
        SchemaType[] schemaTypes = { SchemaType.INT, SchemaType.LIST_STRING };

        StructuredOutput so = new StructuredOutput(schemaTypes);

        WorkflowStep ws1 = new WorkflowStep("testName", "testPrompt", "testSystemPrompt", so);
        WorkflowStep ws2 = new WorkflowStep("testName2", "testPrompt2", "testSystemPrompt2", so);

        Agent a = new Agent("testName");
        a.addStep(ws1);
        a.addStep(ws2);

        WorkflowStep step = a.findStepByName("testName2");

        assertEquals("testName2", step.getName());
    }

    @Test
    void findStepByNameMissing() {
        SchemaType[] schemaTypes = { SchemaType.INT, SchemaType.LIST_STRING };

        StructuredOutput so = new StructuredOutput(schemaTypes);

        WorkflowStep ws1 = new WorkflowStep("testName", "testPrompt", "testSystemPrompt", so);
        WorkflowStep ws2 = new WorkflowStep("testName2", "testPrompt2", "testSystemPrompt2", so);

        Agent a = new Agent("testName");
        a.addStep(ws1);
        a.addStep(ws2);

        WorkflowStep step = a.findStepByName("missingName");

        assertEquals(null, step);
    }

    @Test
    void testLoadAgentSuccess() throws WorkflowFormatException, IOException {
        Agent a = Agent.loadAgent(testResource("agent_math_explainer.txt"));

        assertEquals("math_explainer", a.getName());
        assertEquals(2, a.getStepCount());

        WorkflowStep s1 = a.getSteps().get(0);
        WorkflowStep s2 = a.getSteps().get(1);

        assertEquals("deriválás magyarázás", s1.getName());
        assertEquals("Magyarázd el a deriválást!", s1.getPrompt());
        assertEquals("Érthetően, de ne túl hosszasan fogalmazz!", s1.getSystemPrompt());
        assertEquals(SchemaType.STRING, s1.getStructuredOutput().getSchemaTypes()[0]);

        assertEquals("integrálás magyarázás", s2.getName());
        assertEquals("Magyarázd el az integrálást!", s2.getPrompt());
        assertEquals("Érthetően, de ne túl hosszasan fogalmazz!", s2.getSystemPrompt());
        assertEquals(SchemaType.STRING, s2.getStructuredOutput().getSchemaTypes()[0]);
    }

    @Test
    void testLoadAgentRejectsMissingHeader() throws IOException {
        try {
            Agent a = Agent.loadAgent(testResource("agent_missing_header.txt"));
            fail();
        } catch (WorkflowFormatException e) {

        }
    }

    @Test
    void testLoadAgentRejectsDuplicateStepNames() throws IOException, WorkflowFormatException {
        try {
            Agent a = Agent.loadAgent(testResource("agent_duplicate_stepnames.txt"));
            fail();
        } catch (IllegalArgumentException e) {

        }
    }
}
