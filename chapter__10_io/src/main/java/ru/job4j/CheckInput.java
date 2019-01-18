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


    /**
     * Search for abuse in the incoming stream and record to output stream writer without them.
     *
     * @param in A Reader.
     * @param out A Writer.
     * @param abuse Array strings which need remove of reader.
     * @throws IOException If an I/O error occurs.
     */
    public void dropAbuses(InputStreamReader in, OutputStreamWriter out, String[] abuse) throws IOException {
        try (BufferedReader reader = new BufferedReader(in);
             BufferedWriter writer = new BufferedWriter(out)) {
            int value = 0;
            while (value != -1) {
                value = reader.read();
                if (value == -1) {
                    break;
                }
                if (!skipAbuses(reader, value, abuse)) {
                    out.write(value);
                }
            }
            writer.flush();
        }
    }

    private boolean skipAbuses(BufferedReader reader, int value, String[] abuse) throws IOException {
        boolean skip = false;
        for (String tmp : abuse) {
            if (checkAbuse(reader, value, tmp)) {
                skip = true;
                break;
            }
        }
        return skip;
    }

    private boolean checkAbuse(BufferedReader reader, int value, String abuse) throws IOException {
        int index = 0;
        boolean isCoincidence = true;
        reader.mark(abuse.length());
        while (abuse.length() > index) {
            if (value == -1 || abuse.charAt(index++) != value) {
                reader.reset();
                isCoincidence = false;
                break;
            } else if (abuse.length() != index) {
                value = reader.read();
            }
        }
        return isCoincidence;
    }
}
