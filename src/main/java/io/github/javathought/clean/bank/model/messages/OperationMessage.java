package io.github.javathought.clean.bank.model.messages;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

public class OperationMessage {
    private final String destinationBank;
    private final MessageStatus.OperationResponse response;
    private final RejectReason reason;
    private final OperationMessage originalMessage;

    public OperationMessage(String bank, MessageStatus.OperationResponse response, RejectReason reason, OperationMessage senderMessage) {
        this.destinationBank = bank;
        this.response = response;
        this.reason = reason;
        this.originalMessage = senderMessage;
    }

    public String getDestinationBank() {
        return destinationBank;
    }

    public MessageStatus.OperationResponse response() {
        return response;
    }

    public RejectReason reason() {
        return reason;
    }

    public OperationMessage originalMessage() {
        return originalMessage;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        OperationMessage that = (OperationMessage) o;

        return new EqualsBuilder()
                .append(destinationBank, that.destinationBank)
                .append(response, that.response)
                .append(reason, that.reason)
                .append(originalMessage, that.originalMessage)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(destinationBank)
                .append(response)
                .append(reason)
                .append(originalMessage)
                .toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("destinationBank", destinationBank)
                .append("response", response)
                .append("reason", reason)
                .append("originalMessage", originalMessage)
                .toString();
    }
}
