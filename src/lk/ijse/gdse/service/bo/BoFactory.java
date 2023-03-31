package lk.ijse.gdse.service.bo;

import lk.ijse.gdse.service.bo.custom.imple.*;

public class BoFactory {
    private static BoFactory boFactory;
    private BoFactory(){}
    public enum BoTypes{
        ACCOUNT_DETAILS_BO,DEPOSIT_BO,DEPOSIT_ACCOUNT_BO,DEPOSIT_DETAILS_BO,INTER_ACCOUNT_TRANSACTION_BO,LOAN_DETAILS_BO,LOAN_INSTALLMENT_BO,LOANS_BO,PENDING_LOANS_BO,REJECTED_LOANS_BO,TRANSACTION_BO,WITHDRAWAL_BO
    }
    public static BoFactory getBoFactory(){
        return boFactory == null ? boFactory = new BoFactory() : boFactory;
    }

    public SuperBO getBO(BoTypes types){
        switch (types){
            case LOANS_BO:
                return new LoansBOImple();
            case DEPOSIT_BO:
                return new DepositBOImple();
            case WITHDRAWAL_BO:
                return new WithdrawalBOImple();
            case TRANSACTION_BO:
                return new TransactionBOImple();
            case LOAN_DETAILS_BO:
                return new LoanDetailsBOImple();
            case PENDING_LOANS_BO:
                return new PendingLoansBOImple();
            case REJECTED_LOANS_BO:
                return new RejectedLoansBOImple();
            case ACCOUNT_DETAILS_BO:
                return new AccountDetailsBOImple();
            case DEPOSIT_ACCOUNT_BO:
                return new DepositAccountBOImple();
            case DEPOSIT_DETAILS_BO:
                return new DepositDetailsBOImple();
            case LOAN_INSTALLMENT_BO:
                return new LoanInstallmentBOImple();
            case INTER_ACCOUNT_TRANSACTION_BO:
                return new InterAccountTransactionBOImple();
            default:
                return null;
        }
    }
}
