package stat;

import java.util.*;

class Store {
    private int unchanged = 0;
    private int changed   = 0;
    private int deleted   = 0;
    private int added     = 0;

    private Info createInfo(List<User> previous, List<User> current) {
        Map<Integer, User> temp = new HashMap<>();
        for (User user : previous) {
            temp.put(user.getId(), user);
        }
        for (User user : current) {
            User found = temp.get(user.getId());
            if (found == null) {
                added++;
            } else if (found.equals(user)) {
                unchanged++;
            } else {
                changed++;
            }
        }
        deleted = previous.size() + added - current.size();
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
