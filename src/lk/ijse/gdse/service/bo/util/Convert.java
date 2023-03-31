package lk.ijse.gdse.service.bo.util;

import lk.ijse.gdse.dao.custom.DepositAccountDAO;
import lk.ijse.gdse.dto.*;
import lk.ijse.gdse.entity.*;


public class Convert {
    public static AccountDetailsDTO fromAccountDetails(AccountDetails accountDetails){
        return new AccountDetailsDTO(
                accountDetails.getAccountId(),
                accountDetails.getNic(),
                accountDetails.getName(),
                accountDetails.getAddress(),
                accountDetails.getContact(),
                accountDetails.getDateOfBirth(),
                accountDetails.getEmail(),
                accountDetails.getGender(),
                accountDetails.getRegDate(),
                accountDetails.getRegTime(),
                accountDetails.getState()
        );
    }

    public static AccountDetails toAccountDetails(AccountDetailsDTO accountDetailsDTO){
        return new AccountDetails(
                accountDetailsDTO.getAccountId(),
                accountDetailsDTO.getNic(),
                accountDetailsDTO.getName(),
                accountDetailsDTO.getAddress(),
                accountDetailsDTO.getContact(),
                accountDetailsDTO.getDateOfBirth(),
                accountDetailsDTO.getEmail(),
                accountDetailsDTO.getGender(),
                accountDetailsDTO.getRegDate(),
                accountDetailsDTO.getRegTime(),
                accountDetailsDTO.getState()
        );
    }

    public static DepositDTO fromDeposit(Deposit deposit){
        return new DepositDTO(
                deposit.getDepositId(),
                deposit.getTransactionId(),
                deposit.getDepositTypeAccountId(),
                deposit.getAmount()
        );
    }

    public static Deposit toDeposit(DepositDTO depositDTO){
        return new Deposit(
                depositDTO.getDepositId(),
                depositDTO.getTransactionId(),
                depositDTO.getDepositTypeAccountId(),
                depositDTO.getAmount()
        );
    }

    public static DepositAccount toDepositAccount(DepositAccountDTO depositAccountDTO){
        return new DepositAccount(
                depositAccountDTO.getDepositTypeAccountId(),
                depositAccountDTO.getDepositTypeId(),
                depositAccountDTO.getAccountId(),
                depositAccountDTO.getCreatedDate(),
                depositAccountDTO.getBalance()
        );
    }
    public static DepositAccountDTO fromDepositAccount(DepositAccount depositAccount){
        return new DepositAccountDTO(
                depositAccount.getDepositTypeAccountId(),
                depositAccount.getDepositTypeId(),
                depositAccount.getAccountId(),
                depositAccount.getCreatedDate(),
                depositAccount.getBalance()
        );
    }

    public static DepositDetails toDepositDetails(DepositDetailsDTO depositDetailsDTO){
        return new DepositDetails(
                depositDetailsDTO.getDepositTypeId(),
                depositDetailsDTO.getDescription(),
                depositDetailsDTO.getInterest()
        );
    }
    public static DepositDetailsDTO fromDepositDetails(DepositDetails depositDetails){
        return new DepositDetailsDTO(
                depositDetails.getDepositTypeId(),
                depositDetails.getDescription(),
                depositDetails.getInterest()
        );
    }

    public static InterAccountTransaction toInterAccountTransaction(InterAccountTransactionDTO interAccountTransactionDTO){
        return new InterAccountTransaction(
                interAccountTransactionDTO.getInterAccountTransactionId(),
                interAccountTransactionDTO.getTransactionId(),
                interAccountTransactionDTO.getAccount01Id(),
                interAccountTransactionDTO.getAccount02Id(),
                interAccountTransactionDTO.getAmount()
        );
    }
    public static InterAccountTransactionDTO fromInterAccountTransaction(InterAccountTransaction interAccountTransaction){
        return new InterAccountTransactionDTO(
                interAccountTransaction.getInterAccountTransactionId(),
                interAccountTransaction.getTransactionId(),
                interAccountTransaction.getAccount01Id(),
                interAccountTransaction.getAccount02Id(),
                interAccountTransaction.getAmount()
        );
    }

    public static LoanDetails toLoanDetails(LoanDetailsDTO loanDetailsDTO){
        return new LoanDetails(
                loanDetailsDTO.getLoanTypeId(),
                loanDetailsDTO.getDescription(),
                loanDetailsDTO.getInterest(),
                loanDetailsDTO.getAmounts()
        );
    }
    public static LoanDetailsDTO fromLoanDetails(LoanDetails loanDetails){
        return new LoanDetailsDTO(
                loanDetails.getLoanTypeId(),
                loanDetails.getDescription(),
                loanDetails.getInterest(),
                loanDetails.getAmounts()
        );
    }

    public static LoanInstallment toLoanInstallment(LoanInstallmentDTO loanInstallmentDTO){
        return new LoanInstallment(
                loanInstallmentDTO.getLoanInstallmentId(),
                loanInstallmentDTO.getTransactionId(),
                loanInstallmentDTO.getLoanId()
        );
    }
    public static LoanInstallmentDTO fromLoanInstallment(LoanInstallment loanInstallment){
        return new LoanInstallmentDTO(
                loanInstallment.getLoanInstallmentId(),
                loanInstallment.getTransactionId(),
                loanInstallment.getLoanId()
        );
    }

    public static Loans toLoans(LoansDTO loansDTO){
        return new Loans(
                loansDTO.getLoanId(),
                loansDTO.getAmount(),
                loansDTO.getLoanTypeId(),
                loansDTO.getAccountId(),
                loansDTO.getInstallments(),
                loansDTO.getInstallmentAmount(),
                loansDTO.getIssuedDate(),
                loansDTO.getRemainingInstallments(),
                loansDTO.getRemainingLoanAmount(),
                loansDTO.getMonthlyInstallmentDate(),
                loansDTO.getSettledOrNot(),
                loansDTO.getInterestAmount()
        );
    }
    public static LoansDTO fromLoans(Loans loans){
        return new LoansDTO(
                loans.getLoanId(),
                loans.getAmount(),
                loans.getLoanTypeId(),
                loans.getAccountId(),
                loans.getInstallments(),
                loans.getInstallmentAmount(),
                loans.getIssuedDate(),
                loans.getRemainingInstallments(),
                loans.getRemainingLoanAmount(),
                loans.getMonthlyInstallmentDate(),
                loans.getSettledOrNot(),
                loans.getInterestAmount()
        );
    }

    public static PendingLoans toPendingLoans(PendingLoansDTO pendingLoansDTO){
        return new PendingLoans(
                pendingLoansDTO.getPendingLoanId(),
                pendingLoansDTO.getAmount(),
                pendingLoansDTO.getLoanTypeId(),
                pendingLoansDTO.getAccountId(),
                pendingLoansDTO.getInstallments(),
                pendingLoansDTO.getInstallmentAmount()
        );
    }
    public static PendingLoansDTO fromPendingLoans(PendingLoans pendingLoans){
        return new PendingLoansDTO(
                pendingLoans.getPendingLoanId(),
                pendingLoans.getAmount(),
                pendingLoans.getLoanTypeId(),
                pendingLoans.getAccountId(),
                pendingLoans.getInstallments(),
                pendingLoans.getInstallmentAmount()
        );
    }

    public static RejectedLoans toRejectedLoans(RejectedLoansDTO rejectedLoansDTO){
        return new RejectedLoans(
                rejectedLoansDTO.getRejLoanId(),
                rejectedLoansDTO.getAmount(),
                rejectedLoansDTO.getLoanTypeId(),
                rejectedLoansDTO.getAccountId(),
                rejectedLoansDTO.getReason()
        );
    }
    public static RejectedLoansDTO fromRejectedLoans(RejectedLoans rejectedLoans){
        return new RejectedLoansDTO(
                rejectedLoans.getRejLoanId(),
                rejectedLoans.getAmount(),
                rejectedLoans.getLoanTypeId(),
                rejectedLoans.getAccountId(),
                rejectedLoans.getReason()
        );
    }

    public static Transaction toTransaction(TransactionDTO transactionDTO){
        return new Transaction(
                transactionDTO.getTransactionId(),
                transactionDTO.getAccountId(),
                transactionDTO.getAmount(),
                transactionDTO.getDate(),
                transactionDTO.getTime(),
                transactionDTO.getType()
        );
    }
    public static TransactionDTO fromTransaction(Transaction transaction){
        return new TransactionDTO(
                transaction.getTransactionId(),
                transaction.getAccountId(),
                transaction.getAmount(),
                transaction.getDate(),
                transaction.getTime(),
                transaction.getType()
        );
    }

    public static Withdrawal toWithdrawal(WithdrawalDTO withdrawalDTO){
        return new Withdrawal(
                withdrawalDTO.getWithdrawalId(),
                withdrawalDTO.getTransactionId(),
                withdrawalDTO.getDepositTypeAccountId(),
                withdrawalDTO.getAmount()
        );
    }
    public static WithdrawalDTO fromWithdrawal(Withdrawal withdrawal){
        return new WithdrawalDTO(
                withdrawal.getWithdrawalId(),
                withdrawal.getTransactionId(),
                withdrawal.getDepositTypeAccountId(),
                withdrawal.getAmount()
        );
    }
}
