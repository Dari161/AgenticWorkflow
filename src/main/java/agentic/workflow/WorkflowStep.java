package agentic.workflow;

import agentic.workflow.llm.SchemaType;
import agentic.workflow.llm.StructuredOutput;

public class WorkflowStep {
    private String name;
    private String prompt;
    private String systemPrompt;
    private StructuredOutput structuredOutput;

    public WorkflowStep(String name, String prompt, String systemPrompt, StructuredOutput structuredOutput)
            throws IllegalArgumentException {
        if (name == null || name.trim().equals("")
                || prompt == null || prompt.trim().equals("")
                || systemPrompt == null || systemPrompt.trim().equals("")
                || structuredOutput == null) {
            throw new IllegalArgumentException(
                    "The name, prompt, and systemPrompt cannot be null, empty, or blank, and structuredOutput cannot be null.");
        }

        this.name = name;
        this.prompt = prompt;
        this.systemPrompt = systemPrompt;
        this.structuredOutput = new StructuredOutput(structuredOutput);
    }

    public WorkflowStep(WorkflowStep other) {
        this.name = other.getName();
        this.prompt = other.getPrompt();
        this.systemPrompt = other.getSystemPrompt();
        this.structuredOutput = new StructuredOutput(other.getStructuredOutput());
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPrompt() {
        return prompt;
    }

    public void setPrompt(String prompt) {
        this.prompt = prompt;
    }

    public String getSystemPrompt() {
        return systemPrompt;
    }

    public void setSystemPrompt(String systemPrompt) {
        this.systemPrompt = systemPrompt;
    }

    public StructuredOutput getStructuredOutput() {
        return new StructuredOutput(structuredOutput);
    }

    public void setStructuredOutput(StructuredOutput structuredOutput) {
        this.structuredOutput = new StructuredOutput(structuredOutput);
    }

    public boolean expectsStructuredOutput() {
        return structuredOutput.size() != 0;
    }

    public String simulateResponse() {
        switch (structuredOutput.getSchemaTypes()[0]) {
            case SchemaType.INT:
                return "0";
            case SchemaType.STRING:
                return "sample";
            case SchemaType.BOOLEAN:
                return "true";
            case SchemaType.LIST_INT:
                return "[1,2,3]";
            case SchemaType.LIST_STRING:
                return "[\"a\",\"b\"]";
            case SchemaType.MAP_STRING_STRING:
                return "{\"key\":\"value\"}";
        }
        return "UNREACHABLE";
    }
}
