package scalatraining.objects;

import java.util.Objects;

public class JavaObject {

    public static void main(String[] args) {

        PersonJava person = new PersonJava(null, 25);
        PersonJava person2 = new PersonJava("someone", 20);

        System.out.println(person.getName() == person2.getName());
        System.out.println(Objects.equals(person.getName(), person2.getName()));

    }
}
