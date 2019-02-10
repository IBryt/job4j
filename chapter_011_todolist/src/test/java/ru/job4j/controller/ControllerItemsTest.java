package ru.job4j.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.powermock.core.classloader.annotations.PrepareForTest;
import ru.job4j.dao.impl.ImplDao;
import ru.job4j.models.impl.Item;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.WriteListener;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.Arrays;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.mock;
import static org.powermock.api.mockito.PowerMockito.when;

public class ControllerItemsTest {
    private ImplDao<Item> dao = ImplDao.getInstance();
    private final ControllerItems controller = new ControllerItems();

    @Test
    @PrepareForTest(HttpServletResponse.class)
    public void whetGetRequestReturnJson() throws ServletException, IOException {
        final Item add = dao.add(getItem());
        String expected = new ObjectMapper().writeValueAsString(Arrays.asList(add));
        HttpServletRequest req = mock(HttpServletRequest.class);
        HttpServletResponse resp = mock(HttpServletResponse.class);
        when(req.getParameter("done")).thenReturn(String.valueOf(true));
        StubServletOutputStream outputStream = new StubServletOutputStream();
        when(resp.getOutputStream()).thenReturn(outputStream);
        controller.doGet(req, resp);
        assertThat(String.valueOf(outputStream.baos), is(expected));
    }

    private Item getItem() {
        final Item item = new Item();
        item.setDone(true);
        item.setCreated(new Timestamp(System.currentTimeMillis()));
        item.setDesc("add");
        return item;
    }

    public class StubServletOutputStream extends ServletOutputStream {
        private ByteArrayOutputStream baos = new ByteArrayOutputStream();
        public void write(int i) {
            baos.write(i);
        }

        @Override
        public boolean isReady() {
            return false;
        }

        @Override
        public void setWriteListener(WriteListener writeListener) {

        }
    }
}