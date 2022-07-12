package Menu;

import java.util.Scanner;

public class MainMenu {

  public void showMenu() {
    String command;
    Scanner in = new Scanner(System.in);

    while (true) {
      System.out.println("Please enter a command");
      System.out.println("Registration - sign up");
      System.out.println("Login - sign in");
      System.out.println("Exit - exit");

      command = in.next();

      relocated(command);
    }
  }

  private void relocated(String command) { //system relocated in main menu
    switch (command) {
      case ("sign up"):
        break;
      case ("sign in"):
        break;
      case ("exit"):
        break;
      default:
        break;
    }
  }
}