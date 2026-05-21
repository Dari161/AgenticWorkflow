# AgenticWorkflow

AgenticWorkflow is a small Java/Maven project built from a first-year university assignment. It models a simple agent workflow where an `Agent` contains ordered `WorkflowStep` objects. Each step has a prompt, a system prompt, and an expected structured output type.

The project is intentionally small, but it demonstrates core Java fundamentals: object-oriented design, enums, checked exceptions, file parsing, validation, defensive copying, Maven, and JUnit tests.

## Features

- Defines agents made from ordered workflow steps
- Loads workflows from a plain text file format
- Validates invalid input and malformed workflow files
- Uses a custom checked exception for workflow format errors
- Uses defensive copies to protect internal mutable state
- Includes unit tests and structure tests with JUnit
- Includes a runnable demo workflow

## Example Workflow File

```text
AGENT: math_explainer

STEP
name=derivalas magyarazas
prompt=Magyarazd el a derivalast!
systemPrompt=Erthetoen, de ne tul hosszasan fogalmazz!
output=STRING
ENDSTEP
```

The repository includes a longer demo file here:

```text
examples/math_explainer.txt
```

## Project Structure

```text
src/main/java
```

Main Java source code.

```text
src/test/java
```

JUnit tests.

```text
src/test/resources
```

Input files used by the functional tests.

```text
examples
```

Example workflow files for manually running the program.

## Requirements

- Java JDK 25
- Maven

Check that both are available:

```powershell
java -version
mvn -version
```

## Running the Demo

From IntelliJ, run:

```text
agentic.workflow.Main
```

By default, it loads:

```text
examples/math_explainer.txt
```

You can also pass another workflow file path as the first program argument.

Expected output from the included demo:

```text
derivalas magyarazas sample
integralas magyarazas sample
```

## Running Tests

Run all tests with Maven:

```powershell
mvn test
```

The project currently includes tests for:

- `Agent`
- `WorkflowStep`
- `StructuredOutput`
- `SchemaType`
- `WorkflowFormatException`
- workflow file loading behavior

## What I Learned

This project helped me practice:

- setting up a Java project with Maven
- writing JUnit tests
- designing small classes with clear responsibilities
- using enums and checked exceptions
- parsing text files
- validating invalid input
- protecting internal state with defensive copies
- documenting how to build and run a project

## Notes

This is a learning project, not a production framework. The goal was to turn a university assignment into a clean, runnable, tested Java project that can be read and executed easily from GitHub.
