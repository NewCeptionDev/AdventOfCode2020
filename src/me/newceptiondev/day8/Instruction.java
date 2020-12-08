package me.newceptiondev.day8;

public class Instruction {

    private final Operation operation;
    private final int argument;

    /**
     * Creates an Instruction by a Code describing it
     *
     * @param code String describing an Instruction
     */
    public Instruction(final String code) {
        String[] split = code.split(" ");

        switch (split[0]) {
            case "acc":
                this.operation = Operation.ACCUMULATOR;
                break;
            case "jmp":
                this.operation = Operation.JUMP;
                break;
            default:
                this.operation = Operation.NO_OPERATION;
        }

        this.argument = Integer.parseInt(split[1]);
    }

    public Instruction(final Operation operation, final int argument) {
        this.operation = operation;
        this.argument = argument;
    }

    /**
     * Getter for Operation
     *
     * @return Operation
     */
    public Operation getOperation() {
        return operation;
    }

    /**
     * Getter for Argument
     *
     * @return Integer
     */
    public int getArgument() {
        return argument;
    }
}
