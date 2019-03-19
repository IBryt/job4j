package ru.job4j.util;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.FileCleanerCleanup;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.io.FileCleaningTracker;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class MultiPartRequestHandler {

    public Map<String, FileItem> getItems(ServletConfig servletConfig, HttpServletRequest req) throws FileUploadException {
        DiskFileItemFactory factory = new DiskFileItemFactory();

        ServletContext servletContext = servletConfig.getServletContext();
        File repository = (File) servletContext.getAttribute("javax.servlet.context.tempdir");
        factory.setRepository(repository);

        ServletFileUpload upload = new ServletFileUpload(factory);
        final List<FileItem> fileItems = upload.parseRequest(req);
        final HashMap<String, FileItem> map = fileItems.stream().collect(Collectors.toMap(FileItem::getFieldName, item -> item, (a, b) -> b, HashMap::new));
        return map;
    }
}
