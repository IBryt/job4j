package ru.job4j.array;

/**
 * Обертка над строкой.
 */
public class ArrayChar {
    private char[] data;

    public ArrayChar(String line) {
        this.data = line.toCharArray();
    }

    /**
     * Проверяет. что слово начинается с префикса.
     * @param prefix префикс.
     * @return если слово начинаеться с префикса
     */
    public boolean startWith(String prefix) {
        boolean result = true;
        char[] value = prefix.toCharArray();
        if (value.length > this.data.length) {
            result = false;
        } else {
            for (int i = 0; i != value.length; i++) {
                if (value[i] != this.data[i]) {
                    result = false;
                    break;
                }
            }
        }
        return result;
    }
}