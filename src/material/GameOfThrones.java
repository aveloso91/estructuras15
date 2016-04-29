package material;

import material.tree.LinkedTree;
import material.tree.Position;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Alejandro on 29/4/16.
 */
public class GameOfThrones {

    private class FamilyMember {
        private String id;
        private String name;
        private String surname;
        private boolean gender; //MALE = TRUE; FEMALE = FALSE
        private int age;

        public FamilyMember(String id, String name, String surname, boolean gender, int age) {
            this.id = id;
            this.surname = surname;
            this.name = name;
            this.gender = gender;
            this.age = age;
        }

        public FamilyMember() {
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getSurname() {
            return surname;
        }

        public void setSurname(String surname) {
            this.surname = surname;
        }

        public boolean isGender() {
            return gender;
        }

        public void setGender(boolean gender) {
            this.gender = gender;
        }

        public int getAge() {
            return age;
        }

        public void setAge(int age) {
            this.age = age;
        }
    }

    List<LinkedTree<FamilyMember>> gTrees = new ArrayList<LinkedTree<FamilyMember>>();

    public void loadFile(String pathToFile) throws IOException, NoSuchFieldException {
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
            System.out.println(str);
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
        throw new NoSuchFieldException("The specified id does not exist");
    }

    public LinkedTree<FamilyMember> getFamily(String surname){
        for(LinkedTree<FamilyMember> family : gTrees){
            if(family.root().getElement().getSurname().equals(surname))
                return family;
        }
        return null;
    }

    public String findHeir(String surname){
        return null;
    }

    public void changeFamily(String memberName, String newParent){

    }

    public boolean areFamily(String name1, String name2){
        return false;
    }


    public static void main(String[] args){
        String pathFile = "/Users/Alejandro/IdeaProjects/EDA2015/resources/got.txt";
        GameOfThrones g = new GameOfThrones();
        try {
            g.loadFile(pathFile);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }

    }
}
