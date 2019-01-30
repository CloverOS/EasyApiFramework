package cn.khthink.easyapi.kit.database.mongodb;


/**
 * Create by kh at 2019/1/4
 * <p>
 * MongoDb工具
 *
 * @author kh
 */
public class EasyMongoDb {
    private static class Instance {
        private static EasyMongoDb easyMongoDb = new EasyMongoDb();
    }

    public static EasyMongoDb getInstance() {
        return Instance.easyMongoDb;
    }

    private EasyMongoDb() {
    }

    public void init() {
    }

    public void getDataBase(String dataBaseName) {

    }
}
