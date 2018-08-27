package map;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class User {
    private String name;
    private int children;
    private Calendar birthday;

    public User() { };

    public User(String name, int children, Calendar birthday) {
        this.name = name;
        this.children = children;
        this.birthday = birthday;
    }

    @Override
    public String toString() {
        return "User{" + "name='" + name + '\'' + ", hashCode=" + hashCode() + '}';
    }

    public void mapUsers(User u1, User u2) {
        Object o = new Object() {
            @Override
            public String toString() {
                return " empty object";
            }
        };
        Map<Object, Object> map = new HashMap<>();
        map.put(u1, o);
        map.put(u2, o);
        System.out.println(map);
    }

    @Override
    public int hashCode() {
        int result = name != null ? name.hashCode() : 0;
        result = 31 * result + children;
        result = 31 * result + (birthday != null ? birthday.hashCode() : 0);
        return result;
    }
}
