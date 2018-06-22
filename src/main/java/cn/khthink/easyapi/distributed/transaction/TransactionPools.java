package cn.khthink.easyapi.distributed.transaction;

import java.util.HashMap;
import java.util.Map;

/**
 * 事务管理池
 *
 * @author kh
 */
public class TransactionPools {
    /**
     * 事务池
     */
    private static Map<Integer, Transaction> transactionHashMap = new HashMap<>();

    private static class Pools {
        private static TransactionPools pools = new TransactionPools();
    }

    public static TransactionPools getInstance() {
        return Pools.pools;
    }

    /**
     * 初始化处理池
     */
    public synchronized void initPools() {

    }

    /**
     * 从事务池里获取一个事务
     *
     * @return Transaction
     */
    public synchronized Transaction getTransaction() {
        if (transactionHashMap.size() > 0) {
            Transaction transaction = transactionHashMap.get(0);
            transactionHashMap.remove(0);
            return transaction;
        } else {
            return null;
        }
    }

}
