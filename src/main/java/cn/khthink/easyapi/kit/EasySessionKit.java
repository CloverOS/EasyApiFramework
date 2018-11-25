package cn.khthink.easyapi.kit;

import cn.khthink.easyapi.config.Constant;
import cn.khthink.easyapi.config.CoreConfig;
import cn.khthink.easyapi.redis.EasyRedis;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import redis.clients.jedis.Jedis;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * Session组件
 *
 * @author kh
 */
public class EasySessionKit {
    private final static String SESSION = "EasySession";
    private final static int TAG = 2;
    private final static SimpleDateFormat SESSIONFORMAT = new SimpleDateFormat("yyyyMMddHHmmss");
    private static String sessionPath;
    private static Disposable disposable;
    /**
     * session文件夹
     */
    private File sessionDir;

    private static class Session {
        private static EasySessionKit instance = new EasySessionKit();
    }


    public static EasySessionKit getInstance() {
        return Session.instance;
    }

    private EasySessionKit() {
    }

    /**
     * 初始化Session组件
     */
    public void initSessionKit() {
        EasyLogger.info("--->初始化Session组件");
        if (CoreConfig.enableRedis) {
            initSessionRedisKit();
        } else {
            initSessionFileKit();
        }
    }

    /**
     * 使用默认文件构建
     */
    private void initSessionFileKit() {
        sessionPath = CoreConfig.webPath + "Session";
        sessionDir = new File(sessionPath);
        if (!sessionDir.exists()) {
            if (!sessionDir.mkdir()) {
                EasyLogger.info("--->初始化Session组件失败:无法创建文件夹");
            }
        }
        Observable
                .interval(0, 30, TimeUnit.MINUTES)
                .observeOn(Schedulers.io())
                .subscribe(getFileTask());
    }

    /**
     * 使用Redis构建
     */
    private void initSessionRedisKit() {
        EasyRedis.getInstance().init(Constant.SESSION_REDIS);
    }

    /**
     * 获取文件轮询任务
     *
     * @return Observer
     */
    private Observer<Long> getFileTask() {
        return new Observer<Long>() {
            @Override
            public void onSubscribe(Disposable d) {
                disposable = d;
            }

            @Override
            public void onNext(Long aLong) {
                checkSession();
            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        };
    }

    /**
     * 检查session
     */
    private void checkSession() {
        File[] sessionFiles = sessionDir.listFiles();
        assert sessionFiles != null;
        for (File sessionFile : sessionFiles) {
            if (isSessionFile(sessionFile.getName())) {
                if (isExpired(sessionFile.getName())) {
                    if (sessionFile.delete()) {
                        EasyLogger.info("--->删除过期Session:" + sessionFile.getName());
                    }
                }
            }
        }
    }

    /**
     * 是否为session文件
     *
     * @param name 文件名字
     * @return 是否为文件
     */
    private boolean isSessionFile(String name) {
        String[] split = name.split(SESSION);
        return split.length == TAG;
    }

    /**
     * session是否过期
     *
     * @param name session密钥
     * @return 是否为密钥
     */
    private boolean isExpired(String name) {
        String[] split = name.split(SESSION);
        String date = split[1];
        try {
            Date sessiondate = SESSIONFORMAT.parse(date);
            Date now = new Date();
            if (now.after(sessiondate)) {
                return true;
            }
        } catch (ParseException e) {
            return false;
        }
        return false;
    }

    /**
     * 创建session
     *
     * @param data        存入的数据
     * @param millseconds 过期时间(单位:毫秒)
     * @return session密钥
     */
    private synchronized String createSessionByFile(String data, long millseconds) {
        String session = UUID.randomUUID().toString();
        String fileName;
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.add(Calendar.MILLISECOND, (int) millseconds);
        Date time = calendar.getTime();
        fileName = session + SESSION + SESSIONFORMAT.format(time);
        File sessionFile = new File(sessionPath + File.separator + fileName);
        try {
            FileOutputStream fileOutputStream = new FileOutputStream(sessionFile, true);
            fileOutputStream.write(data.getBytes());
            fileOutputStream.flush();
            fileOutputStream.close();
            return session;
        } catch (IOException e) {
            EasyLogger.trace(e);
            return null;
        }
    }

    /**
     * 创建session
     *
     * @param data        存入的数据
     * @param millseconds 过期时间(单位:毫秒)
     * @return session密钥
     */
    public synchronized String createSession(String data, long millseconds) {
        if (CoreConfig.enableRedis) {
            String session = UUID.randomUUID().toString();
            Jedis jedis = EasyRedis.getInstance().getJedis(Constant.SESSION_REDIS, true);
            EasyRedis.getInstance().set(jedis, Constant.EASY_SESSION + session, data, EasyRedis.NX, EasyRedis.PX, millseconds);
            return session;
        } else {
            return createSessionByFile(data, millseconds);
        }
    }

    /**
     * 清空并重新设置数据
     *
     * @param session     sessionID编号
     * @param data        数据
     * @param millseconds 过期时间(单位:毫秒)
     * @return boolean
     */
    public boolean setSession(String session, String data, long millseconds) {
        if (CoreConfig.enableRedis) {
            Jedis jedis = EasyRedis.getInstance().getJedis(Constant.SESSION_REDIS, true);
            EasyRedis.getInstance().set(jedis, session, data, EasyRedis.NX, EasyRedis.PX, millseconds);
            return true;
        } else {
            return setSessionByFile(session, data);
        }
    }

    /**
     * 清空并重新设置数据
     *
     * @param session sessionID编号
     * @param data    数据
     * @return boolean
     */
    private boolean setSessionByFile(String session, String data) {
        File sessionFile = getSessionFile(session);
        try {
            if (sessionFile == null) {
                return false;
            } else {
                if (sessionFile.exists()) {
                    if (sessionFile.delete()) {
                        if (!sessionFile.createNewFile()) {
                            return false;
                        }
                    }
                }
            }
            FileOutputStream fileOutputStream = new FileOutputStream(sessionFile, true);
            fileOutputStream.write(data.getBytes());
            fileOutputStream.flush();
            fileOutputStream.close();
            return true;
        } catch (IOException e) {
            EasyLogger.trace(e);
        }
        return false;
    }

    /**
     * 获取session数据
     *
     * @param session sessionID编号
     * @return String session数据
     */
    public String getSession(String session) {
        if (CoreConfig.enableRedis) {
            Jedis jedis = EasyRedis.getInstance().getJedis(Constant.SESSION_REDIS, true);
            return EasyRedis.getInstance().getValue(jedis, Constant.EASY_SESSION + session);
        } else {
            return getSessionByFile(session);
        }
    }

    /**
     * 获取session数据
     *
     * @param session sessionID编号
     * @return String session数据
     */
    private String getSessionByFile(String session) {
        File sessionFile = getSessionFile(session);
        if (sessionFile != null) {
            try {
                FileInputStream fileInputStream = new FileInputStream(sessionFile);
                byte[] b = new byte[fileInputStream.available()];
                fileInputStream.read(b);
                fileInputStream.close();
                return new String(b);
            } catch (IOException e) {
                EasyLogger.trace(e);
                return null;
            }
        }
        return null;
    }

    /**
     * 销毁session
     *
     * @param session session密钥
     * @return 是否成功删除
     */
    public boolean delSession(String session) {
        File sessionFile = getSessionFile(session);
        if (sessionFile != null) {
            if (sessionFile.exists()) {
                return sessionFile.delete();
            } else {
                return true;
            }
        } else {
            return true;
        }
    }

    /**
     * 获取session文件
     *
     * @param session session密钥
     * @return 文件
     */
    private File getSessionFile(String session) {
        File[] files = sessionDir.listFiles();
        assert files != null;
        for (File file : files) {
            if (file.getName().startsWith(session)) {
                return file;
            }
        }
        return null;
    }


    /**
     * 销毁
     */
    public void destory() {
        destory(false);
    }

    /**
     * 销毁
     *
     * @param isClear 是否清空session
     */
    public void destory(boolean isClear) {
        if (CoreConfig.enableRedis) {
            if (isClear) {
                Jedis jedis = EasyRedis.getInstance().getJedis(Constant.SESSION_REDIS, true);
                Set<String> keys = jedis.keys(Constant.EASY_SESSION + "*");
                for (String key : keys) {
                    jedis.del(key);
                }
                jedis.close();
            }
        } else {
            if (disposable != null && !disposable.isDisposed()) {
                disposable.dispose();
            }
            if (isClear) {
                if (sessionDir != null) {
                    if (sessionDir.delete()) {
                        EasyLogger.info("--->清空EasySession文件夹");
                    } else {
                        EasyLogger.info("--->清空EasySession文件夹失败");
                    }
                }
            }
        }
        EasyLogger.info("--->关闭EasySessionKit");
    }

}
