package generic;

public class UserStore<T extends User> extends AbstractStore<T> {
    public UserStore(SimpleArray<T> array) {
        super(array);
    }
}
