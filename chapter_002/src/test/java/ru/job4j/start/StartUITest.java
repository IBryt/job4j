package ru.job4j.start;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import ru.job4j.models.Item;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;

public class StartUITest {
    private Tracker tracker;
    private Item item;
    private String id;

    @Before
    public void init() {
        tracker = new Tracker();
        item = new Item("test1", "testDescription");
        tracker.add(item);
        id = item.getId();
    }

    @After
    public void after() {
        tracker.delete(id);
    }

    @Test
    public void whenAddNewItemThenTrackerHasSameItem() {
        assertThat(tracker.getAll().get(0), is(item));
    }

    @Test
    public void whenEditNameThenReturnNewName() {
        Item next = new Item("test2", "testDescription2");
        next.setId(item.getId());
        tracker.edit(next);
        assertThat(tracker.findById(item.getId()).getName(), is("test2"));
    }

    @Test
    public void whenUserAddItemThenTrackerHasNewItemWithSameName() {
        Input input = new StubInput(new String[]{"1", "y"});
        new StartUI(input, tracker).init();     //   создаём StartUI и вызываем метод init()
        assertThat(tracker.getAll().get(0).getName(), is("test1")); // проверяем, что нулевой элемент массива в трекере содержит имя, введённое при эмуляции.
    }

    @Test
    public void whenGetAllItemsThemReturnArrayWithItemsNotEqualsNull() {
        List<Item> expected = new ArrayList<>(Arrays.asList(item));
        assertThat(tracker.getAll(), is(expected));
    }

    @Test
    public void whenDeleteFromArrayItemsThenArrayIsOneLess() {
        Item delete = new Item("test2", "testDescription");
        tracker.add(delete);
        tracker.delete(delete.getId());
        List<Item> expected = new ArrayList<>(Arrays.asList(item));
        assertThat(tracker.getAll(), is(expected));
    }
    @Test
    public void whenFindExistItemByIdThemReturnFoundItem() {
        assertThat(tracker.findById(item.getId()), is(item));
    }

    @Test
    public void whenFindNoExistItemByIdThemReturnEmptyItem() {
        assertThat(tracker.findById("EmptyValue"), is(Item.EMPTY));
    }

    @Test
    public void whenFindExistItemByNameThemReturnFoundItem() {
         assertThat(tracker.findByName(item.getName()), is(item));
    }
    @Test
    public void whenFindNoExistItemByNameThemReturnEmptyItem() {
        assertThat(tracker.findByName("EmptyValue"), is(Item.EMPTY));
    }

    @Test
    public void whenUpdateThenTrackerHasUpdatedValue() {
        Input input = new StubInput(new String[]{"2", "0", "test1", "testD", "y"});
        StartUI startUI = new StartUI(input, tracker);
        startUI.init();
        assertThat(tracker.findById(item.getId()).getName(), is("test1"));
    }
}