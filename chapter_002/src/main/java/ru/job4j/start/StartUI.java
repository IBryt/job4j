package ru.job4j.start;

/**
 * @version $Id$
 * @since 0.1
 */
public class StartUI {
    private int[] range;
    private final Input input;

    /**
     * Хранилище заявок.
     */
    private final ITracker tracker;

    /**
     * Конструтор инициализирующий поля.
     * @param input ввод данных.
     * @param tracker хранилище заявок.
     */
    public StartUI(Input input, ITracker tracker) {
        this.input = input;
        this.tracker = tracker;
    }

    /**
     * Основой цикл программы.
     */
    public void init() {
        Tracker tracker = new Tracker();
        MenuTracker menu = new MenuTracker(this.input, tracker);
        menu.fillActions();
        range = menu.getRange();
        do {
            menu.show();
            //int key = Integer.valueOf(input.ask("Select "));
            menu.select(this.input.ask("Select ", range));
        } while (!"y".equals(this.input.ask("Exit?(y/n) ")));
    }

    /**
     * Запускт программы.
     * @param args
     */
    public static void main(String[] args) {
        new StartUI(new ValidateInput(new ConsoleInput()), new Tracker()).init();
    }
}