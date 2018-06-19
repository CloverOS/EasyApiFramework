package cn.khthink.easyapi.kit;

import cn.khthink.easyapi.config.CoreConfig;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
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
        sessionPath = CoreConfig.webPath + "Session";
        sessionDir = new File(sessionPath);
        if (!sessionDir.exists()) {
            sessionDir.mkdir();
        }
        EasyLogger.info("--->初始化Session组件");
        Observable
                .interval(0, 30, TimeUnit.MINUTES)
                .observeOn(Schedulers.io())
                .subscribe(getFileTask());
    }

    /**
     * 获取文件轮询任务
     *
     * @return
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
                    sessionFile.delete();
                }
            }
        }
    }

    /**
     * 是否为session文件
     *
     * @param name
     * @return
     */
    private boolean isSessionFile(String name) {
        String[] split = name.split(SESSION);
        if (split.length == TAG) {
            return true;
        }
        return false;
    }

    /**
     * session是否过期
     *
     * @param name
     * @return
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
     * @return
     */
    public synchronized String createSession(String data, long millseconds) {
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
     * 清空并重新设置数据
     *
     * @param session sessionID编号
     * @param data    数据
     * @return
     */
    public boolean setSession(String session, String data) {
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
     * @return
     */
    public String getSession(String session) {
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
     * @param session
     * @return
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
     * @param session
     * @return
     */
    private File getSessionFile(String session) {
        File[] files = sessionDir.listFiles();
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
        if (disposable != null && !disposable.isDisposed()) {
            disposable.dispose();
        }
        if (isClear) {
            if (sessionDir != null) {
                sessionDir.delete();
            }
        }
        EasyLogger.info("--->关闭EasySessionKit");
    }

}
