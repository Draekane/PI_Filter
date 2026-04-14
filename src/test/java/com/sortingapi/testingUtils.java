package com.sortingapi;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.Map;

public class testingUtils {
    private static ArrayList<String> userRoles = new ArrayList<String>(Arrays.asList("Admin", "User", "Guest"));

    public static ArrayList<Map<String, String>> createTestUsers(int count) {
        ArrayList<Map<String, String>> users = new ArrayList<Map<String, String>>();
        for (int i = 0; i < count; i++) {
            users.add(createTestUser("Jimbob_" + i, "Litcherholt_" + i, userRoles.get(i % userRoles.size()),
                    String.valueOf(15 + (3 * i)),
                    "call_me_maybe_" + i + "@ghosted.com"));
        }
        return users;
    }

    public static Map<String, String> createTestUser(String firstName, String lastName, String role, String age,
            String email) {
        Map<String, String> user = new LinkedHashMap<String, String>();
        user.put("firstName", firstName);
        user.put("lastName", lastName);
        user.put("role", role);
        user.put("age", age);
        user.put("email", email);

        return user;
    }
}
