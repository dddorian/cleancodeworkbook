package org.example.function;

final class TransactionRequest {
    private final double amount;
    private final String type;
    private final boolean urgent;

    TransactionRequest(double amount, String type, boolean urgent) {
        this.amount = amount;
        this.type = type;
        this.urgent = urgent;
    }

    public double getAmount() {
        return amount;
    }

    public String getType() {
        return type;
    }

    public boolean isUrgent() {
        return urgent;
    }

    static class Builder {
        private double amount;
        private String type;
        private boolean urgent;

        public Builder type(double amount) {
            this.amount = amount;
            return this;
        }

        public Builder type(String type) {
            this.type = type;
            return this;
        }

        public Builder urgent(boolean urgent) {
            this.urgent = urgent;
            return this;
        }

        public TransactionRequest build() {
            return new TransactionRequest(amount, type, urgent);
        }
    }
}
