package com.xodosan.cosole_authentication.service;

import com.xodosan.cosole_authentication.pojo.User;

import java.io.*;
import java.util.ArrayList;
import java.util.StringTokenizer;

public class FileService {
  private final String DBName = "db.txt";

  public void writeToFile(User user) throws IOException {
    BufferedWriter writer = new BufferedWriter(new FileWriter(DBName, true));
    writer.append(user.getNickName());
    writer.append(' ');
    writer.append(user.getPassword());
    writer.append("\n");

    writer.close();
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