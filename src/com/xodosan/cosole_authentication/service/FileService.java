package com.xodosan.cosole_authentication.service;

import com.xodosan.cosole_authentication.pojo.User;

import java.io.*;
import java.util.ArrayList;
import java.util.StringTokenizer;

public class FileService {
  private final File file = new File("C:\\Users\\Андрей\\IdeaProjects\\console_authentication\\db.txt");
  private final String DBName = "db.txt";

  public void writeToFile(User user) throws IOException {
    BufferedWriter writer = new BufferedWriter(new FileWriter(DBName, true));
    writer.append(user.getNickName());
    writer.append(' ');
    writer.append(user.getPassword());
    writer.append("\n");

    writer.close();
  }

  public void replacePassword(User user) throws IOException {
    ArrayList<User> users = getAllUsers();
    file.delete();
    file.createNewFile();

    for (int i = 0; i < users.size(); i++) {
      if (users.get(i).getNickName().equals(user.getNickName())) {
        users.get(i).setPassword(user.getPassword());
      }

      writeToFile(users.get(i));
    }
  }

  public User searchUserByNickName(String nickName) throws IOException {
    ArrayList<User> users = getAllUsers();

    for (int i = 0; i < users.size(); i++) {
      if (users.get(i).getNickName().equals(nickName)) return users.get(i);
    }

    return null;
  }

  private ArrayList getAllUsers() throws IOException {
    ArrayList<User> users = new ArrayList();
    BufferedReader reader = new BufferedReader(new FileReader(DBName));

    while (true) {
      String currentLine = reader.readLine();
      if (currentLine == null) break;
      StringTokenizer tk = new StringTokenizer(currentLine);
      users.add(new User(tk.nextToken(), tk.nextToken()));
    }

    reader.close();
    return users;
  }
}