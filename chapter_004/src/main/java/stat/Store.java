package stat;

import java.util.*;

class Store {
    private int unchanged = 0;
    private int changed   = 0;
    private int deleted   = 0;
    private int added     = 0;

    private Info createInfo(List<User> previous, List<User> current) {
        List<User> temp = new ArrayList<>(current);
        boolean find;
        for (User user : new ArrayList<User>(previous)) {
            find = false;
            for (int i = 0; i != temp.size(); i++) {
                if (user.equals(temp.get(i))) {
                    unchanged++;
                    temp.remove(i);
                    find = true;
                    break;
                } else if (user.getId() == temp.get(i).getId()) {
                    temp.remove(i);
                    changed++;
                    find = true;
                    break;
                }
            }
            if (!find) {
                deleted++;
            }
        }
        added = temp.size() - deleted;
        return new Info();
    }

    public Info diff(List<User> previous, List<User> current) {
        return this.createInfo(previous, current);
    }

    public Map<String, Integer> mapDiff(List<User> previous, List<User> current) {
        return this.createInfo(previous, current).getDiff();
    }

    private class Info {

        public Map<String, Integer> getDiff() {
            Map<String, Integer> map = new HashMap<>(3, 1);
            map.put("unchanged", unchanged);
            map.put("changed",   changed);
            map.put("deleted",   deleted);
            map.put("added",     added);
            return map;
        }

        @Override
        public String toString() {
            return String.format("unchanged = %d, changed = %d, deleted = %d, added= %d.", unchanged, changed, deleted, added);
        }
    }
}
