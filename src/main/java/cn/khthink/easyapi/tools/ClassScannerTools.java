package cn.khthink.easyapi.tools;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URL;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.jar.JarEntry;
import java.util.jar.JarInputStream;

/**
 * Created by KH on 2016/12/28.
 */

/**
 * 扫描class工具类
 *
 * @author kh
 */
public class ClassScannerTools {

    private ClassLoader classLoader;

    static class Instance {
        static ClassScannerTools classScannerTools = new ClassScannerTools();
    }

    private ClassScannerTools() {
        classLoader = Thread.currentThread().getContextClassLoader();
    }

    public static ClassScannerTools getInstance() {
        return Instance.classScannerTools;
    }

    /**
     * 获取Class列表
     *
     * @param packageName 包名(不指定为全部)
     * @param list        返回的list
     */
    public void getClazzs(String packageName, List<String> list) {
        List<String> temp;
        URL url;
        boolean isRoot = packageName == null || "".equals(packageName);
        if (isRoot) {
            url = classLoader.getResource(File.separator);
        } else {
            url = classLoader.getResource(packageName.replaceAll("\\.", "/"));
        }
        if (url != null) {
            String filePath = getRootPath(url);
            temp = readDirectory(filePath);
            if (temp != null) {
                for (String name : temp) {
                    if (isClassFile(name)) {
                        if (isRoot) {
                            list.add(trimExtension(name));
                        } else {
                            list.add(packageName + "." + trimExtension(name));
                        }
                    } else {
                        if (isRoot) {
                            getClazzs(name, list);
                        } else {
                            getClazzs(packageName + "." + name, list);
                        }
                    }
                }
            }
        }
    }

    /**
     * 获取根目录
     *
     * @param url 目录地址
     * @return String
     */
    private String getRootPath(URL url) {
        String fileUrl = url.getFile();
        int pos = fileUrl.indexOf('!');
        if (pos == -1) {
            return fileUrl;
        }
        return fileUrl.substring(5, pos);
    }

    /**
     * 去除文件后缀
     *
     * @param name 文件名
     * @return String
     */
    private String trimExtension(String name) {
        int pos = name.indexOf('.');
        if (-1 != pos) {
            return name.substring(0, pos);
        }
        return name;
    }

    /**
     * 判断是否是一个类文件
     *
     * @param name 文件名
     * @return boolean
     */
    private boolean isClassFile(String name) {
        return name.endsWith(".class");
    }

    /**
     * 判断是否是一个jar包文件
     *
     * @param name 文件名
     * @return boolean
     */
    private boolean isJarFile(String name) {
        return name.endsWith(".jar");
    }

    /**
     * 从一个jar包读取class
     *
     * @param listName            列表
     * @param jarPath             jar包路径
     * @param splashedPackageName 包名
     * @return List<String>
     * @throws IOException IO异常
     */
    private List<String> readJar(List<String> listName, String jarPath, String splashedPackageName) throws IOException {
        JarInputStream jarIn = new JarInputStream(new FileInputStream(jarPath));
        JarEntry entry = jarIn.getNextJarEntry();
        while (null != entry) {
            if (entry.getName().startsWith(splashedPackageName) && isClassFile(entry.getName())) {
                listName.add(entry.getName());
            }
            entry = jarIn.getNextJarEntry();
        }
        return listName;
    }

    /**
     * 从目录读取class
     *
     * @param path 路径
     * @return List<String>
     */
    private List<String> readDirectory(String path) {
        File file = new File(path.replace("%20", " "));
        if ((!file.isDirectory()) || file.list() == null) {
            return null;
        } else {
            return Arrays.asList(Objects.requireNonNull(file.list()));
        }
    }
}
