package material.apps;

import material.FamilyMember;
import material.tree.LinkedTree;
import material.tree.Position;
import material.tree.iterator.PreorderIteratorFactory;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;

/**
 * Created by Alejandro on 29/4/16.
 */
public class GameOfThrones {

    List<LinkedTree<FamilyMember>> gTrees = new ArrayList<LinkedTree<FamilyMember>>();


    public void loadFile(String pathToFile) throws IOException, NoSuchFieldException, FileNotFoundException {
        boolean readM = false;
        List<FamilyMember> peopleList = new ArrayList<FamilyMember>();
        List<String> heads = new ArrayList<String>();
        List<String[]> relations = new ArrayList<String[]>();

        String str;
        FileReader f = new FileReader(pathToFile);
        BufferedReader b = new BufferedReader(f);
        while((str = b.readLine())!=null) {
            String[] splited = str.split(" ");
            if(splited.length == 6)
                loadPeople(splited,peopleList);
            else if(splited.length == 3)
                loadRelations(splited, relations);
            else {
                if(!splited[0].contains("----------------")){
                    if(!readM){
                        readM = true;
                    }else {
                        loadHeads(splited, heads);
                    }
                }
            }
        }
        loadFamiliesInTre(peopleList,heads,relations);
        b.close();
    }

    private void loadPeople(String[] splited, List<FamilyMember> peopleList){

        int age = Integer.parseInt(splited[5]);
        boolean gender;
        if(splited[4].equals("(M)"))
            gender = true;
        else
            gender = false;

        FamilyMember person = new FamilyMember(splited[0],splited[2],splited[3],gender,age);
        peopleList.add(person);
    }

    private void loadHeads(String[] splited, List<String> heads){
        heads.add(splited[0]);
    }

    private void loadRelations(String[] splited, List<String[]> relations){
        String[] rel = new String[2];
        rel[0] = splited[0];
        rel[1] = splited[2];
        relations.add(rel);
    }

    private void loadFamiliesInTre(List<FamilyMember> peopleList, List<String> heads, List<String[]> relations) throws NoSuchFieldException {
        for(int i = 0; i<heads.size(); i++){
            LinkedTree<FamilyMember> family = new LinkedTree<FamilyMember>();
            family.addRoot(getPerson(peopleList,heads.get(i)));
            gTrees.add(family);
        }
        for(String[] rel : relations){
            FamilyMember parent = getPerson(peopleList,rel[0]);
            FamilyMember son = getPerson(peopleList,rel[1]);
            for(LinkedTree<FamilyMember> tree : gTrees){
                for(Position<FamilyMember> member : tree){
                    if(member.getElement() == parent){
                        tree.add(son,member);
                    }
                }
            }
        }
    }

    private FamilyMember getPerson(List<FamilyMember> peopleList, String id) throws NoSuchFieldException {
        for(FamilyMember person : peopleList){
            if (person.getId().equals(id))
                return person;
        }
        throw new NoSuchFieldException("The specified \"id\" does not exist.");
    }

    public LinkedTree<FamilyMember> getFamilyBySurname(String surname) throws NoSuchFieldException{
        for(LinkedTree<FamilyMember> family : gTrees){
            if(family.root().getElement().getSurname().equals(surname))
                return family;
        }
        throw new NoSuchFieldException("Invalid Surname.");
    }

    public String findHeir(String surname) throws NoSuchFieldException {
        LinkedTree<FamilyMember> family = getFamilyBySurname(surname);
        Position<FamilyMember> root = family.root();
        Position<FamilyMember> solution = null;

        for (Position<FamilyMember> child : family.children(root)) {
            if (solution == null) {
                solution = child;
            } else {
                if (!solution.getElement().isGender() && !child.getElement().isGender()) {
                    if (child.getElement().getAge() > solution.getElement().getAge()) {
                        solution = child;
                    }
                } else {
                    if (!solution.getElement().isGender() && child.getElement().isGender()) {
                        solution = child;
                    } else {

                        if (solution.getElement().isGender() && child.getElement().isGender()) {
                            if (child.getElement().getAge() > solution.getElement().getAge()) {
                                solution = child;
                            }
                        }
                    }

                }
            }
        }
        return solution.getElement().getName() + " " + solution.getElement().getSurname();
    }

    public void changeFamily(String memberName, String newParent) throws NoSuchFieldException {
        LinkedTree<FamilyMember> treeMember = getFamilyByName(memberName);
        LinkedTree<FamilyMember> treeParent = getFamilyByName(newParent);
        Position<FamilyMember> posMember = positionByName(treeMember, memberName);
        Position<FamilyMember> posNewParent = positionByName(treeParent, newParent);

        treeMember.moveSubtree(posMember, posNewParent);
    }

    private LinkedTree<FamilyMember> getFamilyByName(String name) throws NoSuchFieldException{
        for(LinkedTree<FamilyMember> family : gTrees){
            Iterator<Position<FamilyMember>> it = family.iterator();
            while (it.hasNext()) {
                if(it.next().getElement().getName().equals(name))
                    return family;
            }
        }
        throw new NoSuchFieldException("Invalid Name.");
    }

    private static Position<FamilyMember> positionByName(LinkedTree<FamilyMember> tree, String name){
        Iterator<Position<FamilyMember>> it = tree.iterator();
        while(it.hasNext()){
            Position<FamilyMember> pos = it.next();
            if(pos.getElement().getName().equals(name)){
                return pos;
            }
        }
        throw new Error("Invalid Name.");

    }

    public boolean areFamily(String name1, String name2) throws NoSuchFieldException {
        LinkedTree<FamilyMember> arbol1 = getFamilyByName(name1);
        Position<FamilyMember> raiz1 = arbol1.root();
        LinkedTree<FamilyMember> arbol2 = getFamilyByName(name2);
        Position<FamilyMember> raiz2 = arbol2.root();
        return (raiz1 == raiz2);
    }

    public void printLinkedTree(LinkedTree<FamilyMember> family){
        System.out.println("*** Family \"" + family.root().getElement().getSurname() + "\" ***");
        System.out.println();
        family.setIterator(new PreorderIteratorFactory<>());
//        Iterator<Position<FamilyMember>> it = new PreorderIteratorFactory<FamilyMember>().createIterator(family);
        Iterator<Position<FamilyMember>> it = family.iterator();
        Position <FamilyMember> tabs = null;
        while(it.hasNext()){
            Position<FamilyMember> member = it.next();
            tabs = member;
            while (tabs != family.root()){
                tabs = family.parent(tabs);
                System.out.print("      ");
            }
            System.out.println(" - " + member.getElement().getName() + " " + member.getElement().getSurname());
        }
        System.out.println();
    }

    public void menuOptions (){
        Scanner board = new Scanner(System.in);
        String option;
        String str1;
        String str2;
        Boolean load = false;
        do {
            System.out.println("----------------------------- || 1 Load File     || 2 Get Family  || 3 Find Heir");
            System.out.println("-*-*-*_GAME OF THRONES_*-*-*- || 4 Change Family || 5 Are Family? || 6 Exit");
            System.out.println("----------------------------- || Please, choose an option (Write the option's number)");
            System.out.println();
            option = board.nextLine();
            switch (option){
                case "1":
                    System.out.println("--- Load File ---");
                    System.out.println("Write the path of the file:");
                    str1 = board.nextLine();
                    try {
                        loadFile(str1);
                        load = true;
                        System.out.println("File load correctly.");
                    }catch (FileNotFoundException e){
                        System.out.println("File not found. "+e.getMessage());
                    } catch (NoSuchFieldException e) {
                        System.out.println(e.getMessage());
                    } catch (IOException e) {
                        System.out.println(e.getMessage());
                    }
                    System.out.println();
                    break;
                case "2":
                    if(load) {
                        System.out.println("--- Get Family ---");
                        System.out.println("Write the surname of the family that you want find:");
                        str1 = board.nextLine();
                        try {
                            printLinkedTree(getFamilyBySurname(str1));
                        }catch (NoSuchFieldException e){
                            System.out.println(e.getMessage());
                        }
                    }else {
                        System.out.println("Load file again.");
                    }
                    System.out.println();
                    break;
                case "3":
                    if(load) {
                        System.out.println("--- Find Heir ---");
                        System.out.println("Write the surname of the family that you want find:");
                        str1 = board.nextLine();
                        try{
                            System.out.println("The "+ str1 +"'s heir is: " + findHeir(str1));
                        }catch (NoSuchFieldException e){
                            System.out.println(e.getMessage());
                        }
                    }else {
                        System.out.println("Load file again.");
                    }
                    break;
                case "4":
                    if(load) {
                        System.out.println("--- Change Family ---");
                        System.out.println("Write the name of the person that we want change:");
                        str1 =  board.nextLine();
                        System.out.println("Write the name of the new parent:");
                        str2 = board.nextLine();
                        try {
                            changeFamily(str1,str2);
                            System.out.println("The change was made ​​successfully.");
                        } catch (NoSuchFieldException e) {
                            System.out.println(e.getMessage());
                        }
                    }else{
                        System.out.println("Load file again.");
                    }
                    System.out.println();
                    break;
                case "5":
                    if(load) {
                        System.out.println("--- Are Family? ---");
                        System.out.println("Write the name of the first person:");
                        str1 =  board.nextLine();
                        System.out.println("Write the name of the second person:");
                        str2 = board.nextLine();
                        try {
                            System.out.print(str1 + " and " +str2 + " are family? " + areFamily(str1, str2));
                        } catch (NoSuchFieldException e) {
                            System.out.println(e.getMessage());
                        }
                    }else {
                        System.out.println("Load file again.");
                    }
                    System.out.println();
                    break;
            }

        }while(!option.equals("6"));
        System.out.println();
        System.out.println("Winter is coming!");

    }


    public static void main(String[] args){
//        String pathFile = "/Users/Alejandro/IdeaProjects/EDA2015/resources/got.txt";
        GameOfThrones g = new GameOfThrones();
        g.menuOptions();
    }
}
