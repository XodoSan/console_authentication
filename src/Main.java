import Menu.MainMenu;

public class Main {
  private static MainMenu mainMenu;

  public Main(MainMenu mainMenu) {
    this.mainMenu = mainMenu;
  }

  public static void main(String[] args) {
    Main main = new Main(new MainMenu());

    mainMenu.showMenu();
  }
}