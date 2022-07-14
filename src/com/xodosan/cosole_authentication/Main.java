package com.xodosan.cosole_authentication;

import com.xodosan.cosole_authentication.pojo.Result;
import com.xodosan.cosole_authentication.pojo.User;
import com.xodosan.cosole_authentication.service.AuthService;
import com.xodosan.cosole_authentication.service.FileService;

import java.io.IOException;
import java.util.Scanner;

public class Main {
  private static AuthService authService;
  private static Tools tools;

  public Main(AuthService authService, Tools tools) {
    this.authService = authService;
    this.tools = tools;
  }

  Scanner in = new Scanner(System.in);

  public static void main(String[] args) throws IOException {
    Main mainMenu = new Main(new AuthService(new FileService(), new Tools()), new Tools());

    mainMenu.showMenu();
  }

  public void showMenu() throws IOException {
    String command;

    while (true) {
      System.out.println("Please enter a command");
      System.out.println("Registration - sign up");
      System.out.println("Login - sign in");
      System.out.println("Exit - exit");

      command = in.nextLine();

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
        break;
      case ("exit"):
        System.exit(1);
      default:
        System.out.println(Error.UNEXPECTED_COMMAND);
        break;
    }
  }
}