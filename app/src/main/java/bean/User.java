package bean;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by chaoqunhuang on 12/13/17.
 */

public class User {
    private String username;
    private String password;
    private String email;
    private String phone;

    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public User(String username, String password, String email, String phone) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.phone = phone;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Map<String, String> toLoginMap() {
        Map<String, String> login = new HashMap<>();
        login.put("type", "login");
        login.put("username", username);
        login.put("password", password);
        return login;
    }

    public Map<String, String> toSignUpMap() {
        Map<String, String> signUp = new HashMap<>();
        signUp.put("type", "signup");
        signUp.put("username", username);
        signUp.put("password", password);
        signUp.put("email", email);
        signUp.put("phone", phone);

        return signUp;
    }
}
