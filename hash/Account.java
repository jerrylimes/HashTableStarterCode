package hash;

import java.util.Objects;

public class Account {
    /**
     * Account Object Class, created for DSA1 HashTable Homework Assignment
     *
     * @author Hunter Platt
     */


    private String username, password;

    public Account() {
        username = "";
        password = "password";
    }

    public Account(String username, String password) {
        if (password.length()>7){
            setPassword(password);
        }
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Account account = (Account) o;
        return Objects.equals(username, account.username) && Objects.equals(password, account.password);
    }

    @Override
    public int hashCode() {
        return Objects.hash(username, password);
    }
}
