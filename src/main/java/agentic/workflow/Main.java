package agentic.workflow;

public class Main {
    public static void main(String[] args) {
        String filename = args.length > 0
                ? args[0]
                : "examples/math_explainer.txt";

        try {
            Agent agent = Agent.loadAgent(filename);
            agent.run();
        } catch (Exception e) {
            System.err.println("Failed to load workflow: " + e.getMessage());
        }
    }
}
