package generic;

public class Role extends Base {
    @Override
    public String toString() {
        return "Role{" + this.getId() + "}";
    }

    protected Role(String id) {
        super(id);
    }
}
