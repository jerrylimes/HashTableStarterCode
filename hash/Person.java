package hash;

import java.util.Objects;

public class Person {
    /**
     * Person Object Class, created for DSA1 HashTable Homework Assignment
     *
     * @author Hunter Platt
     */

    private String name;
    private Account account;

    public Person(String name) {
        this.name = name;
    }

    public Person(Account account, String name) {
        this.account = account;
        this.name = name;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public String getName() {
        return name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Person person = (Person) o;
        return Objects.equals(name, person.name);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(name);
    }
}
