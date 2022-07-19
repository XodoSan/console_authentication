package com.xodosan.cosole_authentication.service;

import com.xodosan.cosole_authentication.Constants;
import com.xodosan.cosole_authentication.pojo.User;

import java.io.*;
import java.util.ArrayList;
import java.util.StringTokenizer;

public class FileService {
  private Constants constants;

  public FileService(Constants constants) {
    this.constants = constants;
  }

  public final File file = new File(constants.DBPath);

  public void writeToFile(User user) throws IOException {
    BufferedWriter writer = new BufferedWriter(new FileWriter(constants.DBPath, true));
    writer.append(user.getNickName());
    writer.append(' ');
    writer.append(user.getPassword());
    writer.append(' ');
    writer.append(String.valueOf(user.isBanned()));
    writer.append("\n");

    writer.close();
  }

  public void replacePassword(User user) throws IOException {
    ArrayList<User> users = getAllUsers();

    for (int i = 0; i < users.size(); i++) {
      if (users.get(i).getNickName().equals(user.getNickName())) {
        users.get(i).setPassword(user.getPassword());
      }
    }

    rewriteFile(users);
  }

  public void replaceBanStatus(User user) throws IOException {
    ArrayList<User> users = getAllUsers();

    for (int i = 0; i < users.size(); i++) {
      if (users.get(i).getNickName().equals(user.getNickName())) {
        users.get(i).setBanned(!user.isBanned());
      }
    }

    rewriteFile(users);
  }

  public User searchUserByNickName(String nickName) throws IOException {
    ArrayList<User> users = getAllUsers();

    for (int i = 0; i < users.size(); i++) {
      if (users.get(i).getNickName().equals(nickName)) return users.get(i);
    }

    return null;
  }

  public boolean IsExists() {
    return file.exists();
  }

  private void rewriteFile(ArrayList<User> users) throws IOException {
    file.delete();
    file.createNewFile();

    for (int i = 0; i < users.size(); i++) {
      writeToFile(users.get(i));
    }
  }

  private ArrayList getAllUsers() throws IOException {
    ArrayList<User> users = new ArrayList();
    BufferedReader reader = new BufferedReader(new FileReader(constants.DBPath));

    while (true) {
      String currentLine = reader.readLine();
      if (currentLine == null) break;
      StringTokenizer tk = new StringTokenizer(currentLine);
      users.add(new User(tk.nextToken(), tk.nextToken(), Boolean.valueOf(tk.nextToken())));
    }

    reader.close();
    return users;
  }
}