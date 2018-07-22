package generic;

public class User extends Base {
    @Override
    public String toString() {
        return "User{" + this.getId() + "}";
    }

    protected User(String id) {
        super(id);
    }
}
