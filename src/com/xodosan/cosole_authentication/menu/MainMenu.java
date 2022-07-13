package com.xodosan.cosole_authentication.menu;

import com.xodosan.cosole_authentication.Tools;
import com.xodosan.cosole_authentication.pojo.Result;
import com.xodosan.cosole_authentication.pojo.User;
import com.xodosan.cosole_authentication.service.AuthService;

import java.util.Scanner;

public class MainMenu {

  private static AuthService authService;
  private static Tools tools;

  public MainMenu(AuthService authService, Tools tools) {
    this.authService = authService;
    this.tools = tools;
  }

  Scanner in = new Scanner(System.in);

  public void showMenu() {
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

  private void relocated(String command) { //system relocated in main menu
    switch (command) {
      case ("sign up"):
        System.out.print("Enter your nickname: ");
        String nickName = in.nextLine();
        System.out.println("Enter your password: ");
        String password = in.nextLine();

        Result result = tools.validatePassword(password);
        if (!result.isResult()) {
          System.out.println(result.getError());
          return;
        }

        authService.registration(new User(nickName, password));
        break;
      case ("sign in"):
        break;
      case ("exit"):
        System.exit(1);
      default:
        break;
    }
  }
}