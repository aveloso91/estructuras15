package material;

import java.util.Comparator;

/**
 * Created by Alejandro on 15/6/16.
 */

public class Student{
    String name;
    int age;
    int record;
    double mark;
    Comparator<Student> comp;

    public Student() {
    }

    public Student(String name, int age, int record, double mark) {
        this.name = name;
        this.age = age;
        this.record = record;
        this.mark = mark;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public int getRecord() {
        return record;
    }

    public void setRecord(int record) {
        this.record = record;
    }

    public double getMark() {
        return mark;
    }

    public void setMark(double mark) {
        this.mark = mark;
    }

    @Override
    public String toString() {
        return "Student{" +
                "name='" + name + '\'' +
                ", age=" + age +
                ", record=" + record +
                ", mark=" + mark +
                '}';
    }

    public static class CompName implements Comparator<Student> {

        @Override
        public int compare(Student st, Student st1) {
            String name1 = st.getName();
            String name2 = st1.getName();
            return name1.compareTo(name2);
        }

    }

    public static class CompAge implements Comparator<Student> {

        @Override
        public int compare(Student st, Student st1) {
            if (st.getAge() == st1.getAge()) {
                return 0;
            } else if (st.getAge() < st1.getAge()) {
                return -1;
            } else{
                return +1;
            }
        }

    }

    public static class CompMark implements Comparator<Student> {

        @Override
        public int compare(Student st, Student st1) {
            if (st.getMark() == st1.getMark()) {
                return 0;
            } else if (st.getMark() < st1.getMark()) {
                return -1;
            } else{
                return +1;
            }
        }

    }
}