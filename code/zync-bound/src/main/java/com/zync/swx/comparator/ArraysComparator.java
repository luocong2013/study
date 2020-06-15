package com.zync.swx.comparator;

import java.util.*;

/**
 * @author LC
 * @version V1.0
 * @project luoc-hanlp
 * @package com.luo.comparator
 * @description TODO
 * @date 2017-10-31 16:49
 */
public class ArraysComparator {
    private static final Random RANDOM = new Random();

    public static void main(String[] args) {
        List<User> users = new ArrayList<>();
        int count = 20;
        for (int i = 1; i <= count; i++) {
            users.add(new User(i, "name"+i, "passwd"+i, RANDOM.nextInt(2000)));
        }

        users.forEach(user -> System.out.println(user.getAge()));

        System.out.println("========================================");

        /*Collections.sort(users, new Comparator<User>() {
            @Override
            public int compare(User o1, User o2) {
                return o1.getAge().compareTo(o2.getAge());
            }
        });*/

        users.sort((User o1, User o2) -> o1.getAge().compareTo(o2.getAge()));

        Collections.sort(users, (o1, o2) -> o1.getAge().compareTo(o2.getAge()));

//        Collections.sort(users, Comparator.comparing(User::getAge));

//        users.sort(Comparator.comparingInt(User::getAge));

        users.forEach(user -> System.out.println(user.getAge()));
    }
}
