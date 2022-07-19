package com.xodosan.cosole_authentication;

import com.xodosan.cosole_authentication.menu.AccountMenu;
import com.xodosan.cosole_authentication.menu.AdminMenu;
import com.xodosan.cosole_authentication.pojo.Result;
import com.xodosan.cosole_authentication.pojo.User;
import com.xodosan.cosole_authentication.service.AuthService;
import com.xodosan.cosole_authentication.service.FileService;

import java.io.Console;
import java.io.IOException;
import java.util.Scanner;

public class Main {
  private static AuthService authService;
  private static Tools tools;
  private static AccountMenu accountMenu;
  private static FileService fileService;
  private static AdminMenu adminMenu;

  public Main(AuthService authService, Tools tools, AccountMenu accountMenu, FileService fileService, AdminMenu adminMenu) {
    this.authService = authService;
    this.tools = tools;
    this.accountMenu = accountMenu;
    this.fileService = fileService;
    this.adminMenu = adminMenu;
  }

  public Scanner in = new Scanner(System.in);
  public Console console = System.console();

  public static void main(String[] args) throws IOException {
    Main mainMenu = new Main(
      new AuthService(new FileService(new Constants()), new Tools()),
      new Tools(),
      new AccountMenu(new AuthService(new FileService(new Constants()), new Tools()), new Tools(), new FileService(new Constants())),
      new FileService(new Constants()),
      new AdminMenu(new AuthService(new FileService(new Constants()), new Tools()), new FileService(new Constants()), new Tools(), new Constants()));

    mainMenu.showMainMenu();
  }

  public void showMainMenu() throws IOException {
    if (!fileService.IsExists()) {
      fileService.file.createNewFile();
      fileService.writeToFile(new User("Admin", tools.stringHashing(""), false));
    }

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
        String password = String.valueOf(console.readPassword("Enter your password: "));
        String repeatedPassword = String.valueOf(console.readPassword("Repeat your password: "));

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

        User regUser = new User(nickName, password, false);

        Result regResult = authService.registration(regUser);
        if (!regResult.isResult()) {
          System.out.println("User not added, reason: " + regResult.getError());
          return;
        }

        accountMenu.showLoginMenu(nickName);
        break;
      case ("sign in"):
        System.out.print("Enter your nickname: ");
        nickName = in.nextLine();
        password = String.valueOf(console.readPassword("Enter your password: "));

        User user = new User(nickName, password, false);
        Result logResult = authService.login(user);

        if (!logResult.isResult()) {
          System.out.println("Invalid login, reason: " + logResult.getError());
          return;
        }

        if (user.getNickName().equals("Admin")) {
          adminMenu.showAdminMenu();
          break;
        }

        accountMenu.showLoginMenu(nickName);
        break;
      case ("exit"):
        System.exit(1);
      default:
        System.out.println(Error.UNEXPECTED_COMMAND);
        break;
    }
  }
}