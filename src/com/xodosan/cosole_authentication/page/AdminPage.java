package com.xodosan.cosole_authentication.page;

import com.xodosan.cosole_authentication.constant.Constants;
import com.xodosan.cosole_authentication.constant.Error;
import com.xodosan.cosole_authentication.pojo.Result;
import com.xodosan.cosole_authentication.pojo.User;
import com.xodosan.cosole_authentication.util.Tools;

import java.io.Console;
import java.io.IOException;
import java.util.Scanner;

public class AdminPage {
  private Scanner in = new Scanner(System.in);
  private Console console = System.console();
  private boolean status;

  public void showAdminMenu() throws IOException {
    status = true;

    while (status) {
      System.out.println("You in admin menu");
      System.out.println("Banned user - ban");
      System.out.println("Change your password - change");
      System.out.println("Sign out - out");
      System.out.println("Exit - exit");

      String command = in.nextLine();

      relocated(command);
    }
  }

  private void relocated(String command) {
    User thisUser = userService.searchUserByNickname(Constants.ADMIN_nickname);

    switch (command) {
      case ("ban"):
        System.out.println("Enter user nickname: ");
        String bannedUserNickname = in.nextLine();

        if (!authService.isExist(bannedUserNickname)) {
          System.out.println(Error.USER_NOT_EXIST);
          break;
        }

        if (bannedUserNickname.equals(Constants.ADMIN_nickname)) {
          System.out.println("Admin can't ban himself");
          break;
        }

        User bannedUser = userService.searchUserByNickname(bannedUserNickname);

        userService.replaceBanStatus(bannedUser);
        System.out.println("Successfully change ban status, user: " + bannedUserNickname);
        break;
      case ("change"):
        String oldPassword = String.valueOf(console.readPassword("Enter your old password: "));

        Result compareResult = Tools.isPasswordsEqual(Tools.stringHashing(oldPassword), thisUser.getPassword());
        if (!compareResult.isResult()) {
          System.out.println(compareResult.getError());
          return;
        }

        String newPassword = String.valueOf(console.readPassword("Enter your new password: "));

        Result validateResult = Tools.validatePassword(newPassword);
        if (!validateResult.isResult()) {
          System.out.println(validateResult.getError());
          return;
        }

        authService.changePassword(new User(thisUser.getnickname(), newPassword));
        break;
      case ("out"):
        status = false;
        break;
      case ("exit"):
        System.exit(1);
      default:
        System.out.println(Error.UNEXPECTED_COMMAND);
        break;
    }
  }
}
