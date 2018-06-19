package cn.khthink.easyapi.annotation.config.kit;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface FileUpload {
    /**
     * 上传路径
     *
     * @return
     */
    String uploadPath() default "upload";

    /**
     * 内存阀值
     *
     * @return
     */
    int memoryThreshold() default 1024 * 1024 * 3;

    /**
     * 文件最大限制
     *
     * @return
     */
    int maxFileSize() default 1024 * 1024 * 40;

    /**
     * 最大请求大小
     *
     * @return
     */
    int maxRequestSize() default 1024 * 1024 * 50;
}
