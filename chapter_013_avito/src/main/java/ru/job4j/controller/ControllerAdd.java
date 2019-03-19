package ru.job4j.controller;

import org.apache.commons.fileupload.FileItem;
import ru.job4j.models.impl.cars.*;
import ru.job4j.models.impl.users.Users;
import ru.job4j.service.CarService;
import ru.job4j.util.MultiPartRequestHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Map;
import java.util.UUID;

public class ControllerAdd extends HttpServlet {
    private final static String UPLOAD_PATH =  "/avitoimg/";
    private final static MultiPartRequestHandler HANDLER = new MultiPartRequestHandler();
    private final static CarService SERVICE = CarService.getInstance();

    @Override
    public void init() throws ServletException {
        final File file = new File(UPLOAD_PATH);
        if (!file.exists()) {
            file.mkdirs();
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.sendRedirect(String.format("%s/add.html", req.getContextPath()));
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html");
        try {
            final Map<String, FileItem> items = HANDLER.getItems(this.getServletConfig(), req);
            Car car = addCar(items);

            final String brand = items.get("brand").getString();
            car.setBrand(new Brand(brand));

            final String category = items.get("category").getString();
            car.setCategory(new Category(category));

            final String carcase = items.get("carcase").getString();
            car.setCarcase(new Carcase(carcase));

            final String saleStatus = items.get("saleStatus").getString();
            car.setSaleStatus(SaleStatus.valueOf(saleStatus));

            final String login = (String) req.getSession().getAttribute("login");
            car.getAuthors().add(new Users(login));

            final String path = uploadFile(items.get("uploadFile"));
            car.setUploadPath(path);

            SERVICE.add(car);
            doGet(req, resp);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private String uploadFile(FileItem fileItem) throws Exception {
        final String path = generatePath(fileItem.getName());
        File file = new File(path);
        fileItem.write(file);
        return file.getName();
    }

    private String generatePath(String itemName) throws UnsupportedEncodingException {
        final String fileName = new String(itemName.getBytes(), "UTF-8");
        boolean freeNameFile = false;
        String path = "";
        while (!freeNameFile) {
            final String uuid = UUID.randomUUID().toString();
            path = UPLOAD_PATH + uuid + "." + fileName;
            final File file  = new File(path);
            freeNameFile = !file.exists();
        }
        return path;
    }
    private Car addCar(Map<String, FileItem> items) {
        final Car car = new Car();
        return car;
    }
}
