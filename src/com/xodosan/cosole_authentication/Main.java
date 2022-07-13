package com.xodosan.cosole_authentication;

import com.xodosan.cosole_authentication.menu.MainMenu;
import com.xodosan.cosole_authentication.service.AuthService;

public class Main {
  private static MainMenu mainMenu;

  public Main(MainMenu mainMenu) {
    this.mainMenu = mainMenu;
  }

  public static void main(String[] args) {
    MainMenu mainMenu = new MainMenu(new AuthService(), new Tools());

    mainMenu.showMenu();
  }
}