import pojo.Result;
import pojo.User;

public class AuthService {
  public Result registration(User user) {
    return new Result(true, "none");
  }

  public Result login(User user) {
    return new Result(true, "none");
  }

  private boolean isExist(String nickName) {
    //checking in db
    return true;
  }

  private boolean validatePassword(User user) {
    return true;
  }
}