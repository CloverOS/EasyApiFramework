package cn.khthink.easyapi.distributed.transaction;

/**
 * 事务中心
 *
 * @author kh
 */
public interface TransactionCenter {

    /**
     * 创建一个事务
     *
     * @return Transaction
     * @throws TransactionException 事务异常
     */
    Transaction createTransaction() throws TransactionException;

    /**
     * 关闭全部事务
     *
     * @return TransactionCenter
     * @throws TransactionException 事务异常
     */
    TransactionCenter close() throws TransactionException;

    /**
     * 提交全部事务
     *
     * @return TransactionCenter
     * @throws TransactionException 事务异常
     */
    TransactionCenter submitAll() throws TransactionException;

    /**
     * 提交单个事务
     *
     * @param transaction 事务
     * @return TransactionCenter
     * @throws TransactionException 事务异常
     */
    TransactionCenter submit(Transaction transaction) throws TransactionException;

    /**
     * 事务回滚
     *
     * @param transaction 事务
     * @return TransactionCenter
     * @throws TransactionException 事务异常
     */
    TransactionCenter rollBack(Transaction transaction) throws TransactionException;
}
