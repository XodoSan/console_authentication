package com.xodosan.cosole_authentication;

import com.xodosan.cosole_authentication.constant.Error;
import com.xodosan.cosole_authentication.page.AccountPage;
import com.xodosan.cosole_authentication.page.AdminPage;
import com.xodosan.cosole_authentication.pojo.Result;
import com.xodosan.cosole_authentication.pojo.User;
import com.xodosan.cosole_authentication.service.AuthService;
import com.xodosan.cosole_authentication.service.UserService;
import com.xodosan.cosole_authentication.util.Tools;

import java.io.Console;
import java.io.IOException;
import java.util.Scanner;

public class Main {
  private static AdminPage adminPage = new AdminPage();
  private static AccountPage accountPage = new AccountPage();
  private static AuthService authService = new AuthService();
  private static UserService userService = new UserService();
  private static Scanner in = new Scanner(System.in);
  private static Console console = System.console();

  public static void main(String[] args) {
    showMainMenu();
  }

  public static void showMainMenu() {
    try {
      if (!userService.file.exists()) {
        userService.file.createNewFile();
        userService.addUser(new User("Admin", Tools.stringHashing("")));
      }

      while (true) {
        System.out.println("Registration - reg");
        System.out.println("Login - log");
        System.out.println("Exit - exit");
        System.out.print("Please enter a command: ");
        String command = in.nextLine();

        relocated(command);
      }
    } catch (IOException e) {
      // logger work
    }
  }

  private static void relocated(String command) {
    switch (command) {
      case "reg" -> {
        System.out.print("Enter your nickname: ");
        String nickname = in.nextLine();

        String password = String.valueOf(console.readPassword("Enter your password: "));
        Result validateResult = Tools.validatePassword(password);
        if (!validateResult.isResult()) {
          // System.out.println(validateResult.getError()); logger work
          return;
        }

        String repeatedPassword = String.valueOf(console.readPassword("Repeat your password: "));
        if (!password.equals(repeatedPassword)) {
          // System.out.println("User not added, reason: "); logger work
          return;
        }

        User regUser = new User(nickname, password);
        Result regResult = authService.registration(regUser);
        if (!regResult.isResult()) {
          // System.out.println("User not added, reason: " + regResult.getError()); logger work
          return;
        }

        accountPage.showLoginMenu(nickname);
      }
      case "log" -> {
        System.out.print("Enter your nickname: ");
        String nickname = in.nextLine();
        String password = String.valueOf(console.readPassword("Enter your password: "));

        User user = new User(nickname, password, false);
        Result logResult = authService.login(user);
        if (!logResult.isResult()) {
          // System.out.println("Invalid login, reason: " + logResult.getError()); logger work
          return;
        }

        if (user.getnickname().equals("Admin")) {
          adminPage.showAdminMenu();
          break;
        }

        accountPage.showLoginMenu(nickname);
      }
      case "exit" -> System.exit(1);
      default -> System.out.println(Error.UNEXPECTED_COMMAND);
    }
  }
}