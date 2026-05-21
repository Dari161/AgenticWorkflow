package agentic.workflow.llm;

public class StructuredOutput {
    private final SchemaType[] schemaTypes;

    public StructuredOutput(SchemaType[] schemaTypes) throws IllegalArgumentException, NullPointerException {
        if (schemaTypes == null) {
            throw new NullPointerException("The schemaTypes array cannot be null.");
        }
        if (schemaTypes.length == 0) {
            throw new IllegalArgumentException("At least one schema type must be provided.");
        }
        for (SchemaType schemaType : schemaTypes) {
            if (schemaType == null) {
                throw new NullPointerException("Schema types cannot contain null values.");
            }
        }
        this.schemaTypes = schemaTypes.clone();
    }

    public StructuredOutput(StructuredOutput other) {
        this.schemaTypes = other.getSchemaTypes();
    }

    public SchemaType[] getSchemaTypes() {
        return schemaTypes.clone();
    }

    public boolean contains(SchemaType schemaType) {
        if (schemaType == null) {
            return false;
        }
        for (SchemaType current : schemaTypes) {
            if (current == schemaType) {
                return true;
            }
        }
        return false;
    }

    public int size() {
        return schemaTypes.length;
    }
}
