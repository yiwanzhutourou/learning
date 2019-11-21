package com.youdushufang.io;

import java.io.Console;
import java.util.Arrays;

public class Password {

    public static void main(String[] args) {
        Console c = System.console();
        if (c == null) {
            System.err.println("No console.");
            System.exit(1);
        }

        String login = c.readLine("Enter your login: ");
        // 密码作为 char 数组（String 不可变）范围，用完后及时从内存中清除
        char[] oldPassword = c.readPassword("Enter your old password: ");

        if (verify(login, oldPassword)) {
            boolean noMatch;
            do {
                char [] newPassword1 = c.readPassword("Enter your new password: ");
                char [] newPassword2 = c.readPassword("Enter new password again: ");
                noMatch = !Arrays.equals(newPassword1, newPassword2);
                if (noMatch) {
                    c.format("Passwords don't match. Try again.%n");
                } else {
                    change(login, newPassword1);
                    c.format("Password for %s changed.%n", login);
                }
                // 清除内存中的密码
                Arrays.fill(newPassword1, ' ');
                Arrays.fill(newPassword2, ' ');
            } while (noMatch);
        }

        // 清除内存中的密码
        Arrays.fill(oldPassword, ' ');
    }

    // Dummy change method.
    private static boolean verify(String login, char[] password) {
        return true;
    }

    // Dummy change method.
    private static void change(String login, char[] password) {
        // do nothing
    }
}
