package generic;

public class RoleStore<T extends Role> extends AbstractStore<T> {

    public RoleStore(SimpleArray<T> array) {
        super(array);
    }
}
