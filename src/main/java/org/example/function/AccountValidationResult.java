package org.example.function;

public class AccountValidationResult {
    private final boolean valid;
    private final String[] messages;

    private AccountValidationResult(Builder builder) {
        this.valid = builder.valid;
        this.messages = builder.messages;
    }

    public boolean isValid() {
        return valid;
    }

    public String[] getMessages() {
        return messages != null ? messages.clone() : null;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private boolean valid;
        private String[] messages;

        public Builder valid(boolean valid) {
            this.valid = valid;
            return this;
        }

        public Builder messages(String... messages) {
            this.messages = messages != null ? messages.clone() : null;
            return this;
        }

        public AccountValidationResult build() {
            return new AccountValidationResult(this);
        }
    }
}
