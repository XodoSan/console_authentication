package com.xodosan.cosole_authentication.service;

import com.xodosan.cosole_authentication.constant.Constants;
import com.xodosan.cosole_authentication.pojo.User;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

public class UserService {
  public File file = new File(Constants.DB_PATH);

  public void addUser(User user) {
    try (BufferedWriter writer = new BufferedWriter(new FileWriter(Constants.DB_PATH, true))) {
      writer.append(user.toString());
    } catch (IOException e) {
      // logger work
    }
  }

  public void updatePassword(User user) {
    List<User> users = getAllUsers();

    for (User value : users) {
      if (value.getnickname().equals(user.getnickname())) {
        value.setPassword(user.getPassword());
        break;
      }
    }
    rewriteFile(users);
  }

  public void updateBanStatus(User user) {
    List<User> users = getAllUsers();

    for (User value : users) {
      if (value.getnickname().equals(user.getnickname())) {
        value.setBanned(!user.isBanned());
        break;
      }
    }
    rewriteFile(users);
  }

  public User findUserByNickname(String nickname) {
    List<User> users = getAllUsers();

    for (User user : users) {
      if (user.getnickname().equals(nickname)) return user;
    }
    return null;
  }

  private void rewriteFile(List<User> users) {
    try (BufferedWriter writer = new BufferedWriter(new FileWriter(Constants.DB_PATH, false))) {
      for (User user : users) {
        writer.append(user.toString());
      }
    } catch (IOException e) {
      // logger work
    }
  }

  private List<User> getAllUsers() {
    List<User> users = new ArrayList<>();

    try (BufferedReader reader = new BufferedReader(new FileReader(Constants.DB_PATH))) {
      String currentLine;
      while ((currentLine = reader.readLine()) != null) {
        StringTokenizer tk = new StringTokenizer(currentLine);
        users.add(new User(tk.nextToken(), tk.nextToken(), Boolean.parseBoolean(tk.nextToken())));
      }
    } catch (IOException e) {
      // logger work
    }
    return users;
  }
}