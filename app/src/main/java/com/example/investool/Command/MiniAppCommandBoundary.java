package com.example.investool.Command;

import java.util.Map;

public class MiniAppCommandBoundary {
    private CommandId commandId;
    private String command;
    private TargetObject targetObject;
    private String invocationTimestamp;
    private InvokedBy invokedBy;
    private Map<String, Object> commandAttributes;

    public CommandId getCommandId() {
        return commandId;
    }

    public MiniAppCommandBoundary setCommandId(CommandId commandId) {
        this.commandId = commandId;
        return this;
    }

    public String getCommand() {
        return command;
    }

    public MiniAppCommandBoundary setCommand(String command) {
        this.command = command;
        return this;
    }

    public TargetObject getTargetObject() {
        return targetObject;
    }

    public MiniAppCommandBoundary setTargetObject(TargetObject targetObject) {
        this.targetObject = targetObject;
        return this;
    }

    public String getInvocationTimestamp() {
        return invocationTimestamp;
    }

    public MiniAppCommandBoundary setInvocationTimestamp(String invocationTimestamp) {
        this.invocationTimestamp = invocationTimestamp;
        return this;
    }

    public InvokedBy getInvokedBy() {
        return invokedBy;
    }

    public MiniAppCommandBoundary setInvokedBy(InvokedBy invokedBy) {
        this.invokedBy = invokedBy;
        return this;
    }

    public Map<String, Object> getCommandAttributes() {
        return commandAttributes;
    }

    public MiniAppCommandBoundary setCommandAttributes(Map<String, Object> commandAttributes) {
        this.commandAttributes = commandAttributes;
        return this;
    }

    @Override
    public String toString() {
        return "MiniAppCommandBoundary{" +
                "commandId=" + commandId +
                ", command='" + command + '\'' +
                ", targetObject=" + targetObject +
                ", invocationTimestamp='" + invocationTimestamp + '\'' +
                ", invokedBy=" + invokedBy +
                ", commandAttributes=" + commandAttributes +
                '}';
    }
}
