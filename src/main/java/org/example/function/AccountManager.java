package org.example.function;

import java.util.Date;

class AccountManager {
    private final TransactionLogger logger;
    private final EmailService emailService;

    public AccountManager(TransactionLogger logger, EmailService emailService) {
        this.logger = logger;
        this.emailService = emailService;
    }

    TransactionExecutionResult processAccountTransaction(Account account, TransactionRequest transactionRequest) {
        AccountValidationResult accountValidationResult = validateAccount(account);
        if(!accountValidationResult.isValid()){
            return TransactionExecutionResult.builder()
                    .isSuccessful(false)
                    .errorMessage("Invalid account")
                    .build();
        }
        TransactionExecutionResult transactionValidationResult = executeTransaction(account, transactionRequest);

        // Log transaction
        logTransaction(account, transactionRequest);

        // Send notification if urgent
        if (transactionRequest.isUrgent()) {
            notifyAccountOwner(account, transactionRequest);
        }

        // Update account status
        updateAccountStatus(account);

        return transactionValidationResult;
    }

    private void notifyAccountOwner(Account account, TransactionRequest transactionRequest) {
        emailService.sendEmail(account.getOwnerEmail(),
                "Transaction processed: " + transactionRequest.getType() + " of " + transactionRequest.getAmount());
    }

    private void updateAccountStatus(Account account) {
        account.setLastTransactionDate(new Date());
    }

    private void logTransaction(Account account, TransactionRequest transactionRequest) {
        logger.log(account.getId(), transactionRequest.getAmount(), transactionRequest.getType(), new Date());
    }

    private TransactionExecutionResult executeTransaction(Account account, TransactionRequest transactionRequest) {
        if (isWithdrawal(transactionRequest)) {
            return processWithdrawal(account, transactionRequest);
        } else if (isDeposit(transactionRequest)) {
            return processDeposit(account, transactionRequest);
        } else {
            return TransactionExecutionResult.builder()
                    .isSuccessful(false)
                    .errorMessage("Invalid transaction type")
                    .build();
        }
    }

    boolean isWithdrawal(TransactionRequest transactionRequest) {
        return transactionRequest.getType().equals("WITHDRAW");
    }

    boolean isDeposit(TransactionRequest transactionRequest) {
        return transactionRequest.getType().equals("DEPOSIT");
    }

    private TransactionExecutionResult processWithdrawal(Account account, TransactionRequest transactionRequest) {
        if (accountHasSufficientFunds(account, transactionRequest)) {
            return TransactionExecutionResult.builder()
                    .isSuccessful(false)
                    .errorMessage("Insufficient funds")
                    .build();
        } else {
            account.setBalance(account.getBalance() - transactionRequest.getAmount());
            return TransactionExecutionResult.builder()
                    .isSuccessful(true)
                    .build();
        }
    }

    private TransactionExecutionResult processDeposit(Account account, TransactionRequest transactionRequest) {
        account.setBalance(account.getBalance() + transactionRequest.getAmount());
        return TransactionExecutionResult.builder()
                .isSuccessful(true)
                .build();
    }

    private boolean accountHasSufficientFunds(Account account, TransactionRequest transactionRequest) {
        return account.getBalance() >= transactionRequest.getAmount();
    }

    private AccountValidationResult validateAccount(Account account) {
        // Validate account
        if (account == null || account.getId() == null) {
            throw new IllegalArgumentException("Invalid account.");
        }
        if (account.getStatus().equals("FROZEN")) {
            return AccountValidationResult.builder()
                    .valid(false)
                    .messages("Account frozen")
                    .build();
        }
        return AccountValidationResult.builder()
                .valid(true)
                .build();
    }
}
