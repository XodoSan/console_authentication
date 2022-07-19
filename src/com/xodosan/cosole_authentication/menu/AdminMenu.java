package com.xodosan.cosole_authentication.menu;

import com.xodosan.cosole_authentication.Constants;
import com.xodosan.cosole_authentication.Error;
import com.xodosan.cosole_authentication.Tools;
import com.xodosan.cosole_authentication.pojo.Result;
import com.xodosan.cosole_authentication.pojo.User;
import com.xodosan.cosole_authentication.service.AuthService;
import com.xodosan.cosole_authentication.service.FileService;

import java.io.Console;
import java.io.IOException;
import java.util.Scanner;

public class AdminMenu {
  private final AuthService authService;
  private final FileService fileService;
  private final Tools tools;
  private final Constants constants;

  public AdminMenu(AuthService authService, FileService fileService, Tools tools, Constants constants) {
    this.authService = authService;
    this.fileService = fileService;
    this.tools = tools;
    this.constants = constants;
  }

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

  private void relocated(String command) throws IOException {
    User thisUser = fileService.searchUserByNickName(constants.adminNickName);

    switch (command) {
      case ("ban"):
        System.out.println("Enter user nickname: ");
        String bannedUserNickName = in.nextLine();

        if (!authService.isExist(bannedUserNickName)) {
          System.out.println(Error.USER_NOT_EXIST);
          break;
        }

        if (bannedUserNickName.equals(constants.adminNickName)) {
          System.out.println("Admin can't ban himself");
          break;
        }

        User bannedUser = fileService.searchUserByNickName(bannedUserNickName);

        fileService.replaceBanStatus(bannedUser);
        System.out.println("Successfully change ban status, user: " + bannedUserNickName);
        break;
      case ("change"):
        String oldPassword = String.valueOf(console.readPassword("Enter your old password: "));

        Result compareResult = tools.isPasswordsEqual(tools.stringHashing(oldPassword), thisUser.getPassword());
        if (!compareResult.isResult()) {
          System.out.println(compareResult.getError());
          return;
        }

        String newPassword = String.valueOf(console.readPassword("Enter your new password: "));

        Result validateResult = tools.validatePassword(newPassword);
        if (!validateResult.isResult()) {
          System.out.println(validateResult.getError());
          return;
        }

        authService.changePassword(new User(thisUser.getNickName(), newPassword, thisUser.isBanned()));
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
