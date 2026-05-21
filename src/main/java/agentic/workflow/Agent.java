package agentic.workflow;

import agentic.workflow.llm.SchemaType;
import agentic.workflow.llm.StructuredOutput;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Agent {
    private String name;
    private final List<WorkflowStep> steps = new ArrayList<>();

    public Agent(String name) throws IllegalArgumentException {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("The agent name cannot be null, empty, or blank.");
        }
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<WorkflowStep> getSteps() {
        return new ArrayList<>(steps);
    }

    private boolean contains(WorkflowStep search) {
        for (WorkflowStep step : steps) {
            if (step.getName().equals(search.getName())) {
                return true;
            }
        }
        return false;
    }

    public void addStep(WorkflowStep step) throws IllegalArgumentException {
        if (step == null || contains(step)) {
            throw new IllegalArgumentException(
                    "The step cannot be null, and another step with the same name cannot already exist.");
        }
        steps.add(step);
    }

    public int getStepCount() {
        return steps.size();
    }

    public WorkflowStep findStepByName(String stepName) throws IllegalArgumentException {
        if (stepName == null || stepName.trim().isEmpty()) {
            throw new IllegalArgumentException("The step name cannot be null, empty, or blank.");
        }

        String trimmedStepName = stepName.trim();
        for (WorkflowStep step : steps) {
            if (step.getName().equals(trimmedStepName)) {
                return step;
            }
        }
        return null;
    }

    public void run() {
        for (WorkflowStep step : steps) {
            System.out.println(step.getName() + " " + step.simulateResponse());
        }
    }

    public static Agent loadAgent(String filename)
            throws IllegalArgumentException, WorkflowFormatException, IOException {
        if (filename == null || filename.trim().isEmpty()) {
            throw new IllegalArgumentException("The filename cannot be null, empty, or blank.");
        }

        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            String line = readFirstNonEmptyLine(br);
            if (line == null) {
                throw new WorkflowFormatException("The file is empty. It must start with \"AGENT: <agent name>\".");
            }

            String[] tokens = line.split(":");
            if (tokens.length < 2) {
                throw new WorkflowFormatException(
                        "The first non-empty line does not contain ':'. The file must start with \"AGENT: <agent name>\".");
            }

            if (!tokens[0].trim().equals("AGENT")) {
                throw new WorkflowFormatException(
                        "The first non-empty line does not start with \"AGENT:\". The file must start with \"AGENT: <agent name>\".");
            }

            Agent agent = new Agent(tokens[1].trim());
            while ((line = br.readLine()) != null) {
                line = line.trim();
                if (line.equals("STEP")) {
                    agent.addStep(parseStep(br));
                } else if (!line.isEmpty()) {
                    throw new WorkflowFormatException("Unexpected content between steps: " + line
                            + ". Only empty lines are allowed between steps.");
                }
            }
            return agent;
        }
    }

    private static String readFirstNonEmptyLine(BufferedReader reader) throws IOException {
        String line;
        while ((line = reader.readLine()) != null) {
            if (!line.trim().isEmpty()) {
                return line;
            }
        }
        return null;
    }

    private static WorkflowStep parseStep(BufferedReader reader) throws IOException, WorkflowFormatException {
        String line;
        String name = null;
        String prompt = null;
        String systemPrompt = null;
        StructuredOutput output = null;

        while ((line = reader.readLine()) != null && !line.trim().equals("ENDSTEP")) {
            String[] tokens = line.split("=", 2);
            if (tokens.length < 2) {
                throw new WorkflowFormatException("Invalid line: " + line
                        + ". Each line between STEP and ENDSTEP must use one of these formats:\n"
                        + "name=<step name>\n"
                        + "prompt=<prompt text>\n"
                        + "systemPrompt=<system prompt text>\n"
                        + "output=<schema type>");
            }

            switch (tokens[0].trim()) {
                case "name":
                    if (name != null) {
                        throw new WorkflowFormatException(
                                "The name property was already specified. Each property must appear exactly once.");
                    }
                    name = tokens[1].trim();
                    break;
                case "prompt":
                    if (prompt != null) {
                        throw new WorkflowFormatException(
                                "The prompt property was already specified. Each property must appear exactly once.");
                    }
                    prompt = tokens[1].trim();
                    break;
                case "systemPrompt":
                    if (systemPrompt != null) {
                        throw new WorkflowFormatException(
                                "The systemPrompt property was already specified. Each property must appear exactly once.");
                    }
                    systemPrompt = tokens[1].trim();
                    break;
                case "output":
                    if (output != null) {
                        throw new WorkflowFormatException(
                                "The output property was already specified. Each property must appear exactly once.");
                    }
                    output = parseOutput(tokens[1].trim());
                    break;
                default:
                    throw new WorkflowFormatException("Unknown property: " + tokens[0].trim()
                            + ". Allowed required properties are: name, prompt, systemPrompt, output.");
            }
        }

        if (line == null) {
            throw new WorkflowFormatException("The file ended without ENDSTEP. Each step must end with ENDSTEP.");
        }
        if (name == null) {
            throw new WorkflowFormatException("The name property is missing. Each property must appear exactly once.");
        }
        if (prompt == null) {
            throw new WorkflowFormatException("The prompt property is missing. Each property must appear exactly once.");
        }
        if (systemPrompt == null) {
            throw new WorkflowFormatException(
                    "The systemPrompt property is missing. Each property must appear exactly once.");
        }
        if (output == null) {
            throw new WorkflowFormatException("The output property is missing. Each property must appear exactly once.");
        }

        return new WorkflowStep(name, prompt, systemPrompt, output);
    }

    private static StructuredOutput parseOutput(String value) throws WorkflowFormatException {
        try {
            return new StructuredOutput(new SchemaType[] {SchemaType.valueOf(value)});
        } catch (IllegalArgumentException e) {
            throw new WorkflowFormatException("Unknown output value: " + value
                    + ". The output value must be one of: INT, STRING, BOOLEAN, LIST_INT, LIST_STRING, MAP_STRING_STRING");
        }
    }
}
