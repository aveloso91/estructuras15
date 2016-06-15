package material.apps;

import material.Student;

import java.util.ArrayList;
import java.util.Comparator;

/**
 * Created by Alejandro on 14/6/16.
 */
public class University{
    private static Comparator<Student> cstName = new Student.CompName();
    private static Comparator<Student> cstAge = new Student.CompAge();
    private static Comparator<Student> cstMark = new Student.CompMark();

    private static ArrayList<Student> studentList = new ArrayList<>();

    public static void main(String[] args) {

    }
}
