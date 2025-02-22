package kr.co.frankit_assignment.api.kernel.command;

public class CommandExecutor<T extends CommandModel> {
    private final Command<T> command;
    private final T model;

    public CommandExecutor(Command<T> command, T model) {
        this.command = command;
        this.model = model;
    }

    public void invoke() {
        this.command.execute(this.model);
    }
}
