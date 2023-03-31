package lk.ijse.gdse.dao;

import lk.ijse.gdse.dao.custom.imple.*;

import java.sql.Connection;

public class DaoFactory {
    private static DaoFactory daoFactory;

    private DaoFactory(){}

    public enum DaoTypes{
        ACCOUNT_DETAILS,DEPOSIT,DEPOSIT_ACCOUNT,DEPOSIT_DETAILS,INTER_ACCOUNT_TRANSACTION,LOAN_DETAILS,LOAN_INSTALLMENT,LOANS,PENDING_LOANS,REJECTED_LOANS,TRANSACTION,WITHDRAWAL
    }

    public static DaoFactory getDaoFactory(){
        return daoFactory==null?daoFactory = new DaoFactory():daoFactory;
    }
    public SuperDAO getDao(DaoTypes types, Connection connection){
        switch (types){
            case LOANS:
                return new LoansDAOImple(connection);
            case DEPOSIT:
                return new DepositDAOImple(connection);
            case WITHDRAWAL:
                return new WithdrawalDAOImple(connection);
            case TRANSACTION:
                return new TransactionDAOImple(connection);
            case LOAN_DETAILS:
                return new LoanDetailsDAOImple(connection);
            case PENDING_LOANS:
                return new PendingLoansDAOImple(connection);
            case REJECTED_LOANS:
                return new RejectedLoansDAOImple(connection);
            case ACCOUNT_DETAILS:
                return new AccountDetailsDAOImple(connection);
            case DEPOSIT_ACCOUNT:
                return new DepositAccountDAOImple(connection);
            case DEPOSIT_DETAILS:
                return new DepositDetailsDAOImple(connection);
            case LOAN_INSTALLMENT:
                return new LoanInstallmentDAOImple(connection);
            case INTER_ACCOUNT_TRANSACTION:
                return new InterAccountTransactionDAOImple(connection);
            default:
                return null;
        }
    }
}
