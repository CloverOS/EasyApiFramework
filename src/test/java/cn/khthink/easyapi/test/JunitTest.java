package cn.khthink.easyapi.test;

/*
    Create by KH at 2017/10/19 13:31
	CopyRight © 2016-2018 鲨软科技, All Rights Reserved.
 */


import cn.khthink.easyapi.tools.ClassScannerTools;
import org.junit.Test;

import java.util.*;

/**
 * 测试类
 *
 * @author
 */
public class JunitTest {

    @Test
    public void testUrl() {
        String uri = "/v1/login";
        System.out.println(uri.split("/")[1]);
        Map<String, String[]> map = new HashMap<>();
        map.put("123", new String[]{"123", "123"});
        System.out.println(map.toString());
        String uri2 = "asdf";
        System.out.println(uri2.split("//.").length);
    }

    @Test
    public void testScanner() {
        List<String> classlist = new ArrayList<>();
        ClassScannerTools.getInstance().getClazzs(null, classlist);
        System.out.println(classlist.size());
        for (String s : classlist) {
            System.out.println(s);
        }
    }
    @Test
    public void testProperties(){
        Properties properties = new Properties();

    }
}
