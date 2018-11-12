public class Switcher {
    private StringBuilder str;

    public Switcher() {
        this.str = new StringBuilder();
    }

    public String addValue(int value) {
        return str.append(String.valueOf(value)).toString();
    }

    public StringBuilder getValue() {
        return str;
    }

    public synchronized String addTenTimes(int value) {
        for (int i = 0; i < 10; i++) {
            str.append(String.valueOf(value));
        }
        return str.toString();
    }
}
