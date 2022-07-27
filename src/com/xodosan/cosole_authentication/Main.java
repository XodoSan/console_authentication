package com.xodosan.cosole_authentication;

import com.xodosan.cosole_authentication.constant.Error;
import com.xodosan.cosole_authentication.page.AccountPage;
import com.xodosan.cosole_authentication.page.AdminPage;
import com.xodosan.cosole_authentication.pojo.User;
import com.xodosan.cosole_authentication.service.AuthService;
import com.xodosan.cosole_authentication.service.UserService;
import com.xodosan.cosole_authentication.util.Logger;
import com.xodosan.cosole_authentication.util.Tools;

import java.io.Console;
import java.io.IOException;
import java.util.Scanner;

public class Main {
  private static AdminPage adminPage = new AdminPage();
  private static AccountPage accountPage = new AccountPage();
  private static AuthService authService = new AuthService();
  private static UserService userService = new UserService();
  private static Logger logger = new Logger();
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
      System.out.println("File not found");
    }
  }

  private static void relocated(String command) {
    switch (command) {
      case "reg" -> {
        System.out.print("Enter your nickname: ");
        String nickname = in.nextLine();

        String password = String.valueOf(console.readPassword("Enter your password: "));
        Error validateResult = Tools.validatePassword(password);
        if (validateResult != Error.NONE) {
          Logger.DisplayMessageByError(validateResult);
          return;
        }

        String repeatedPassword = String.valueOf(console.readPassword("Repeat your password: "));
        if (!password.equals(repeatedPassword)) {
          Logger.DisplayMessageByError(Error.PASSWORDS_NOT_EQUAL);
          return;
        }

        User regUser = new User(nickname, password);
        if (!authService.registration(regUser)) {
          Logger.DisplayMessageByError(Error.USER_EXIST);
          return;
        }

        System.out.println("Successfully registration");
        accountPage.showLoginMenu(nickname);
      }
      case "log" -> {
        System.out.print("Enter your nickname: ");
        String nickname = in.nextLine();
        String password = String.valueOf(console.readPassword("Enter your password: "));

        User user = new User(nickname, password);
        Error logResult = authService.login(user);
        if (logResult != Error.NONE) {
          Logger.DisplayMessageByError(logResult);
          return;
        }

        if (user.getnickname().equals("Admin")) {
          adminPage.showAdminMenu();
          break;
        }

        System.out.println("Successfully login");
        accountPage.showLoginMenu(nickname);
      }
      case "exit" -> System.exit(1);
      default -> Logger.DisplayMessageByError(Error.UNEXPECTED_COMMAND);
    }
  }
}