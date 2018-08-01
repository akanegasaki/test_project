package com.takumiCX.concurrency.atomics;

import java.util.Objects;
import java.util.concurrent.atomic.AtomicReference;

/**
 * @author: takumiCX
 * @create: 2018-07-31
 **/
public class AtomicReferenceTest {


    public static void main(String[] args) {

        User machao = new User(1, "machao");

        User zhaoyun=new User(1,"zhaoyun");

        User machao2=new User(1,"machao");

        AtomicReference<User> reference = new AtomicReference<>(machao);

        boolean success = reference.compareAndSet(machao2, zhaoyun);

        System.out.println(success);

        System.out.println(reference.get());

    }
}


class User{

    private int id;

    private String name;

    public User(int id, String name) {
        this.id = id;
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return id == user.id &&
                Objects.equals(name, user.name);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, name);
    }

}