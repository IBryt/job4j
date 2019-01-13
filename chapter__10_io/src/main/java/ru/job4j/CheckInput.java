package ru.job4j;

import java.io.*;

public class CheckInput {

    /**
     * The method can handle numbers greater than 4 bytes.
     * If you need to process numbers of shorter length then add dominant zeros.
     * For example, "0002".
     *
     * @param in input stream.
     * @return true if even number else false.
     * @throws IOException if input less 4 bytes.
     */
    public boolean isNumber(InputStream in) throws IOException {
        boolean res = false;
        try (DataInputStream stream = new DataInputStream(in)) {
            while (stream.readInt() % 2 == 0) {
                res = true;
                break;
            }
        }
        return res;
    }
}
