package kr.co.frankit_assignment.api.kernel.command;

public interface Command<T extends CommandModel> {
    void execute(T model);
}
