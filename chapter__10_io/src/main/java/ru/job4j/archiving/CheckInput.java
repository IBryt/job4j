package ru.job4j.archiving;

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
            String tmp = "";
            while (tmp != null) {
                tmp = reader.readLine();
                if (tmp == null) {
                    break;
                }
                for (String s : abuse) {
                    tmp = tmp.replaceAll(s, "");
                }
                out.write(tmp);
                out.write(System.lineSeparator());
            }
            writer.flush();
        }
    }
}
