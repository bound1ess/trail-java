package trail.input;

class InputArgument {
    private final boolean required;
    private final String name;

    public InputArgument(final String name, boolean required) {
        this.name = name;
        this.required = required;
    }

    public String getName() {
        return this.name;
    }

    public boolean isRequired() {
        return this.required;
    }
}
