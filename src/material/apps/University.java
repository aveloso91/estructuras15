package material.apps;

import material.Student;
import material.tree.binarysearchtree.AVLTree;
import material.tree.binarysearchtree.LinkedBinarySearchTree;
import material.tree.binarysearchtree.RBTree;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.Scanner;

/**
 * Created by Alejandro on 14/6/16.
 */
public class University{
    private static Comparator<Student> cmpName = new Student.CompName();
    private static Comparator<Student> cmpAge = new Student.CompAge();
    private static Comparator<Student> cmpMark = new Student.CompMark();

    private static ArrayList<Student> studentList = new ArrayList<>();

    private static void addStudentsToList() {
        Student st = new Student("Alejandro Veloso", 25, 30, 6.1);
        Student st1 = new Student("Alberto Morales", 22, 70, 7.1);
        Student st2 = new Student("Enrique Gaitan", 29, 39, 8.2);
        Student st3 = new Student("Paco Lopez", 33, 88, 5.9);
        Student st4 = new Student("Miriam Font", 26, 33, 6.4);
        Student st5 = new Student("Laura Rodriguez", 23, 66, 5.3);
        Student st6 = new Student("Cristina Perales", 34, 25, 7.3);
        Student st7 = new Student("Leopoldo Corral", 35, 15, 5.3);
        Student st8 = new Student("Tolin Manantial", 27, 19, 5.9);
        studentList.add(st);
        studentList.add(st1);
        studentList.add(st2);
        studentList.add(st3);
        studentList.add(st4);
        studentList.add(st5);
        studentList.add(st6);
        studentList.add(st7);
        studentList.add(st8);
    }

    private static void addStudentBoard(Scanner board){
        System.out.println("Insert name and surname of the new student:");
        String name = board.nextLine();

        System.out.println("Insert age of the new student:");
        int age = Integer.parseInt(board.nextLine());

        System.out.println("Insert record number of the new student");
        int record = Integer.parseInt(board.nextLine());

        System.out.println("Insert mark of the new student");
        double mark = Double.parseDouble(board.nextLine());

        Student st = new Student(name, age, record, mark);
        studentList.add(st);
    }

    private void menuByName (String option){
        switch (option) {
            case "1":
                LinkedBinarySearchTree<Student> LBSTN = new LinkedBinarySearchTree<>(cmpName);
                for (Student st : studentList){
                    LBSTN.insert(st);
                }
                Iterator<material.tree.Position<Student>> it = LBSTN.iterator();
                while (it.hasNext()){
                    System.out.println(it.next().getElement().toString());
                }
                System.out.println();
                break;
            case "2":
                AVLTree<Student> AVLN = new AVLTree<>(cmpName);
                for (Student st : studentList) {
                    AVLN.insert(st);
                }
                Iterator<material.tree.Position<Student>> it2 = AVLN.iterator();
                while (it2.hasNext()){
                    System.out.println(it2.next().getElement().toString());
                }
                System.out.println();
                break;
            case "3":
                RBTree<Student> RBN = new RBTree<>(cmpName);
                for (Student st : studentList) {
                    RBN.insert(st);
                }
                Iterator<material.tree.Position<Student>> it3 = RBN.iterator();
                while (it3.hasNext()){
                    System.out.println(it3.next().getElement().toString());
                }
                System.out.println();
                break;
        }
    }

    private void menuByAge (String option){
        switch (option) {
            case "1":
                LinkedBinarySearchTree<Student> LBSTA = new LinkedBinarySearchTree<>(cmpAge);
                for (Student st : studentList) {
                    LBSTA.insert(st);
                }
                Iterator<material.tree.Position<Student>> it = LBSTA.iterator();
                while (it.hasNext()){
                    System.out.println(it.next().getElement().toString());
                }
                System.out.println();
                break;
            case "2":
                AVLTree<Student> AVLA = new AVLTree<>(cmpAge);
                for (Student st : studentList) {
                    AVLA.insert(st);
                }
                Iterator<material.tree.Position<Student>> it2 = AVLA.iterator();
                while (it2.hasNext()){
                    System.out.println(it2.next().getElement().toString());
                }
                System.out.println();
                break;
            case "3":
                RBTree<Student> RBA = new RBTree<>(cmpAge);
                for (Student st : studentList) {
                    RBA.insert(st);
                }
                Iterator<material.tree.Position<Student>> it3 = RBA.iterator();
                while (it3.hasNext()){
                    System.out.println(it3.next().getElement().toString());
                }
                System.out.println();
                break;
        }
    }

    private void menuByMark (String option){
        switch (option) {
            case "1":
                LinkedBinarySearchTree<Student> LBSTM = new LinkedBinarySearchTree<>(cmpMark);
                for (Student st : studentList) {
                    LBSTM.insert(st);
                }
                Iterator<material.tree.Position<Student>> it = LBSTM.iterator();
                while (it.hasNext()){
                    System.out.println(it.next().getElement().toString());
                }
                System.out.println();
                break;
            case "2":
                AVLTree<Student> AVLM = new AVLTree<>(cmpMark);
                for (Student st : studentList) {
                    AVLM.insert(st);
                }
                Iterator<material.tree.Position<Student>> it2 = AVLM.iterator();
                while (it2.hasNext()){
                    System.out.println(it2.next().getElement().toString());
                }
                System.out.println();
                break;
            case "3":
                RBTree<Student> RBM = new RBTree<>(cmpMark);
                for (Student st : studentList) {
                    RBM.insert(st);
                }
                Iterator<material.tree.Position<Student>> it3 = RBM.iterator();
                while (it3.hasNext()){
                    System.out.println(it3.next().getElement().toString());
                }
                System.out.println();
                break;
        }
    }



    public void menuOptions(){
        addStudentsToList();
        Scanner board = new Scanner(System.in);
        String option;
        String str1;
        String str2;
        do {
            System.out.println("---------------------------- || Please, choose an option || 5 Exit");
            System.out.println("---------------------------- || 1 Insert Student");
            System.out.println("-*-*-*-*_UNIVERSITY_*-*-*-*- || 2 Create trees ordered by name");
            System.out.println("---------------------------- || 3 Create trees ordered by age");
            System.out.println("---------------------------- || 4 Create trees ordered by mark");
            System.out.println();
            option = board.nextLine();
            switch (option){
                case "1":
                    addStudentBoard(board);
                    System.out.println("Student added correctly.");
                    break;
                case "2":
                    System.out.println("|| 1 Create LinkedBinarySearchTree ordered by name");
                    System.out.println("|| 2 Create AVLTree ordered by name");
                    System.out.println("|| 3 Create RBTree ordered by name");
                    option = board.nextLine();
                    menuByName(option);
                    System.out.println();
                    break;
                case "3":
                    System.out.println("|| 1 Create LinkedBinarySearchTree ordered by age");
                    System.out.println("|| 2 Create AVLTree ordered by age");
                    System.out.println("|| 3 Create RBTree ordered by age");
                    option = board.nextLine();
                    menuByAge(option);
                    break;
                case "4":
                    System.out.println("|| 1 Create LinkedBinarySearchTree ordered by mark");
                    System.out.println("|| 2 Create AVLTree ordered by mark");
                    System.out.println("|| 3 Create RBTree ordered by mark");
                    option = board.nextLine();
                    menuByMark(option);
                    break;
            }

        }while(!option.equals("5"));
    }

    public static void main(String[] args) {
        University uni = new University();
        uni.menuOptions();
    }
}
