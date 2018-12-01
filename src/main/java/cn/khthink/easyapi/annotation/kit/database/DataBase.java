package cn.khthink.easyapi.annotation.kit.database;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface DataBase {
    boolean value() default true;
    String config() default "EasyApi.properties";
}
