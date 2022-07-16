package com.xodosan.cosole_authentication.menu;

import com.xodosan.cosole_authentication.Error;
import com.xodosan.cosole_authentication.pojo.User;

import java.util.Scanner;

public class AfterAccountEntryMenu {
  private boolean status;
  public Scanner in = new Scanner(System.in);

  public void showLoginMenu(User user) {
    status = true;

    while (status) {
      System.out.println("You in account: " + user.getNickName());
      System.out.println("Please enter a command");
      System.out.println("Change password - change");
      System.out.println("Sign out - out");
      System.out.println("Exit - exit");

      String command = in.nextLine();

      relocated(command);
    }
  }

  private void relocated(String command) {
    switch (command) {
      case ("change"):
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
