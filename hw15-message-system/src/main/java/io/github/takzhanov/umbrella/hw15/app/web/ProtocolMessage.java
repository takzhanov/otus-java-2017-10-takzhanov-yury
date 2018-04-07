package io.github.takzhanov.umbrella.hw15.app.web;

public class ProtocolMessage {
    private String command;
    private String text;

    public String getCommand() {
        return command;
    }

    public void setCommand(String command) {
        this.command = command;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("ProtocolMessage{");
        sb.append("command='").append(command).append('\'');
        sb.append(", text='").append(text).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
