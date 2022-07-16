package com.xodosan.cosole_authentication;

import com.xodosan.cosole_authentication.menu.AfterAccountEntryMenu;
import com.xodosan.cosole_authentication.pojo.Result;
import com.xodosan.cosole_authentication.pojo.User;
import com.xodosan.cosole_authentication.service.AuthService;
import com.xodosan.cosole_authentication.service.FileService;

import java.io.IOException;
import java.util.Scanner;

public class Main {
  private static AuthService authService;
  private static Tools tools;
  private static AfterAccountEntryMenu afterAccountEntryMenu;

  public Main(AuthService authService, Tools tools, AfterAccountEntryMenu afterAccountEntryMenu) {
    this.authService = authService;
    this.tools = tools;
    this.afterAccountEntryMenu = afterAccountEntryMenu;
  }

  public Scanner in = new Scanner(System.in);

  public static void main(String[] args) throws IOException {
    Main mainMenu = new Main(new AuthService(new FileService(), new Tools()), new Tools(), new AfterAccountEntryMenu());

    mainMenu.showMainMenu();
  }

  public void showMainMenu() throws IOException {
    while (true) {
      System.out.println("Please enter a command");
      System.out.println("Registration - sign up");
      System.out.println("Login - sign in");
      System.out.println("Exit - exit");

      String command = in.nextLine();

      relocated(command);
    }
  }

  private void relocated(String command) throws IOException {
    switch (command) {
      case ("sign up"):
        System.out.print("Enter your nickname: ");
        String nickName = in.nextLine();
        System.out.print("Enter your password: ");
        String password = in.nextLine();
        System.out.print("Repeat your password: ");
        String repeatedPassword = in.nextLine();

        Result compareResult = tools.isPasswordsEqual(password, repeatedPassword);
        if (!compareResult.isResult()) {
          System.out.println("User not added, reason: " + compareResult.getError());
          return;
        }

        Result validateResult = tools.validatePassword(password);
        if (!validateResult.isResult()) {
          System.out.println(validateResult.getError());
          return;
        }

        Result regResult = authService.registration(new User(nickName, password));
        if (!regResult.isResult()) {
          System.out.println("User not added, reason: " + regResult.getError());
        }

        break;
      case ("sign in"):
        System.out.print("Enter your nickname: ");
        nickName = in.nextLine();
        System.out.print("Enter your password: ");
        password = in.nextLine();

        User user = new User(nickName, password);
        Result logResult = authService.login(user);

        if (logResult.isResult()) afterAccountEntryMenu.showLoginMenu(user);
        else System.out.println("Invalid login, reason: " + logResult.getError());

        break;
      case ("exit"):
        System.exit(1);
      default:
        System.out.println(Error.UNEXPECTED_COMMAND);
        break;
    }
  }
}