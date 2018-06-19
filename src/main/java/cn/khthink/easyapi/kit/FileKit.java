package cn.khthink.easyapi.kit;

import cn.khthink.easyapi.config.CoreConfig;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.util.List;
import java.util.UUID;

/**
 * 文件组件
 *
 * @author kh
 */
public interface FileKit {

    /**
     * 上传文件
     *
     * @param request
     * @return
     */
    default JSONObject uploadFile(HttpServletRequest request) {
        JSONObject jsonObject = new JSONObject();
        if (!ServletFileUpload.isMultipartContent(request)) {
            return null;
        }
        DiskFileItemFactory factory = new DiskFileItemFactory();
        factory.setSizeThreshold(CoreConfig.memoryThreshold);
        factory.setRepository(new File(System.getProperty("java.io.tmpdir")));
        ServletFileUpload upload = new ServletFileUpload(factory);
        upload.setFileSizeMax(CoreConfig.maxFileSize);
        upload.setSizeMax(CoreConfig.maxRequestSize);
        upload.setHeaderEncoding(CoreConfig.charset);
        String uploadPath = request.getServletContext().getRealPath("./") + CoreConfig.uploadPath;
        File uploadDir = new File(uploadPath);
        if (!uploadDir.exists()) {
            uploadDir.mkdir();
        }
        try {
            List<FileItem> formItems = upload.parseRequest(request);
            if (formItems != null && formItems.size() > 0) {
                for (FileItem item : formItems) {
                    if (!item.isFormField()) {
                        String fileRealName = item.getName();
                        String fileName = UUID.randomUUID().toString() + "." + fileRealName.substring(fileRealName.lastIndexOf(".") + 1);
                        String filePath = uploadPath + File.separator + fileName;
                        File storeFile = new File(filePath);
                        item.write(storeFile);
                        jsonObject.put(item.getFieldName(), CoreConfig.uploadPath + File.separator + fileName);
                    } else {
                        jsonObject.put(item.getFieldName(), item.getString(CoreConfig.charset));
                    }
                }
            }
        } catch (Exception ex) {
            EasyLogger.info("保存文件失败", ex);
        }
        return jsonObject;
    }

}
