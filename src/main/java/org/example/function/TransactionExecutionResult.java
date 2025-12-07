package org.example.function;

final class TransactionExecutionResult {
    private final boolean isSuccessful;
    private final String errorMessage;

    TransactionExecutionResult(boolean isValid, String errorMessage) {
        this.isSuccessful = isValid;
        this.errorMessage = errorMessage;
    }

    public boolean isSuccessful() {
        return isSuccessful;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public static Builder builder(){
        return new Builder();
    }

    static class Builder {
        private boolean isSuccessful;
        private String errorMessage;

        Builder isSuccessful(boolean isValid) {
            this.isSuccessful = isValid;
            return this;
        }

        public Builder errorMessage(String errorMessage) {
            this.errorMessage = errorMessage;
            return this;
        }

        public TransactionExecutionResult build() {
            return new TransactionExecutionResult(isSuccessful, errorMessage);
        }
    }
}
