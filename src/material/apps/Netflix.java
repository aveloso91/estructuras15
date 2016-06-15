package material.apps;

import material.Movie;
import material.maps.Entry;
import material.maps.HashTableMapLP;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.*;

/**
 * Created by Alejandro on 10/6/16.
 */
public class Netflix {

    private static HashTableMapLP<String, Movie> mapTitle = new HashTableMapLP<>();
    private static HashTableMapLP<Integer, Set<Movie>> mapYear = new HashTableMapLP<>();
    private static HashTableMapLP<String, Set<Movie>> mapType = new HashTableMapLP<>();

    /**
     * Devuelve la movie con titulo title
     * @param title
     * @return
     */
    public Movie findTitle(String title){
        return mapTitle.get(title);
    }

    /**
     * Devuelve un set de movies correspondientes al año que le pasamos
     * @param year
     * @return
     */
    public Set<Movie> findYear(int year){
        return mapYear.get(year);
    }

    /**
     * Devuelve un set de movies correspondientes con la score que le pasamos (igual o mayor)
     * Primero cogemos todas las entries del mapTitle en el que están todas las peliculas
     * Después comparamos el score que nos pasan con el de las propias peliculas de mapTitle
     * @param score
     * @return
     */
    public Set<Movie> findScore(float score){
        Set<Movie> result = new HashSet<>();
        Iterable<Entry<String,Movie>> ms = mapTitle.entries();
        for(Entry<String, Movie> m : ms){
            if(m.getValue().getScore()>=score){
                result.add(m.getValue());
            }
        }
        return result;
    }

    /**
     * Devuelve un set de movies que contengan los géneros que le pasamos
     * Añadimos a result todas las peliculas que tengan los type que le pasamos
     * @param type
     * @return
     */
    public Set<Movie> findType(Set<String> type){
        Set<Movie> result = new HashSet<>();
        for (String strType : type) {
            for (Movie m : mapType.get(strType)) {
                result.add(m);
            }
        }
        return result;
    }

    /**
     * Añadimos una score en el arraylist de scores de una movie dada
     * @param title
     * @param score
     */
    public void addScore (String title, float score){
        Movie movie = findTitle(title);
        movie.addScore(score);
        System.out.println("Score added.");
    }

    /**
     * Este método sirve para pintar por pantalla un set de películas que le pasemos
     * @param set
     */
    private static void showSetMovies(Set<Movie> set) {
        for (Movie m : set) {
            System.out.println(m.toString());
        }
        System.out.println();
    }

    /**
     * Método con el que cargamos el archivo netflix.txt
     * @param pathToFile
     * @throws IOException
     * @throws NoSuchFieldException
     * @throws FileNotFoundException
     */
    public void loadFile(String pathToFile) throws IOException, NoSuchFieldException, FileNotFoundException {
        File file = new File(pathToFile);
        Scanner lector = new Scanner(file);

        while (lector.hasNext()) {

            String line = lector.nextLine();
            String[] datesMovie = line.split(" - ");

            String title = datesMovie[0];
            Integer year = Integer.parseInt(datesMovie[1]);
            float score = Float.parseFloat(datesMovie[2]);
            java.util.List<Float> lScore = new ArrayList<>();
            lScore.add(score);
            String typesSeparated = datesMovie[3].substring(1, datesMovie[3].length() - 1); //corchetes
            java.util.List<String> type = new ArrayList<>();
            type = Arrays.asList(typesSeparated.split(", ")); //comas

            Movie movie = new Movie(title,year,lScore,type);

            mapTitle.put(title, movie);

            if (mapYear.get(year) == null) {
                mapYear.put(year, new HashSet<>());
                mapYear.get(year).add(movie);
            } else {
                mapYear.get(year).add(movie);
            }

            for (String t : type) { //para cada género spliteado
                if (mapType.get(t) == null) {
                    mapType.put(t, new HashSet<>());
                    mapType.get(t).add(movie);
                } else {
                    mapType.get(t).add(movie);
                }
            }
        }
    }

    /**
     * Método que muestra el menú de opciones de nuestro programa
     */
    public void menuOptions (){
        Scanner board = new Scanner(System.in);
        String option;
        String str1;
        String str2;
        Boolean load = false;
        do {
            System.out.println("----------------------------- || 1 Load File     || 2 Find Title  || 3 Find Year");
            System.out.println("-*-*-*_____NETFLIX_____*-*-*- || 4 Find Score    || 5 Find Type   || 6 Add Score");
            System.out.println("----------------------------- || 7 Exit          || Please, choose an option (number)");
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
                        System.out.println("--- Find Title ---");
                        System.out.println("Write the title that you want find:");
                        str1 = board.nextLine();
                        try {
                            Movie movie = findTitle(str1);
                            System.out.println(movie.toString());
                        }catch (Exception e){
                            System.out.println("Movie not found.");
                        }
                    }else {
                        System.out.println("Load file again.");
                    }
                    System.out.println();
                    break;
                case "3":
                    if(load) {
                        System.out.println("--- Find Year ---");
                        System.out.println("Write the year of the movies that you want find:");
                        str1 = board.nextLine();
                        try {
                            this.showSetMovies(findYear(Integer.parseInt(str1)));
                        } catch (Exception e){
                            System.out.println("Movies not found.");
                        }
                    }else {
                        System.out.println("Load file again.");
                    }
                    break;
                case "4":
                    if(load) {
                        System.out.println("--- Find Score ---");
                        System.out.println("Write the score of the movies that you want find:");
                        str1 =  board.nextLine();
                        try{
                            this.showSetMovies(findScore(Float.parseFloat(str1)));
                        }catch (Exception e){
                            System.out.println("Movies not found.");
                        }
                    }else{
                        System.out.println("Load file again.");
                    }
                    System.out.println();
                    break;
                case "5":
                    if(load) {
                        System.out.println("--- Find Type ---");
                        System.out.println("Write the type or types of the movies that you want find separated by spaces:");
                        str1 =  board.nextLine();
                        String[] typesMovie = str1.split(" ");
                        Set<String> typesSet = new HashSet<>();
                        for(String t : typesMovie){
                            typesSet.add(t);
                        }
                        try {
                            this.showSetMovies(findType(typesSet));
                        } catch (Exception e){
                            System.out.println("Movies not found.");
                        }
                    }else {
                        System.out.println("Load file again.");
                    }
                    System.out.println();
                    break;
                case "6":
                    if(load){
                        System.out.println("--- Add Score ---");
                        System.out.println("Write the title of movie that you want scores:");
                        str1 =  board.nextLine();
                        System.out.println("Write the score:");
                        str2 =  board.nextLine();
                        try{
                            addScore(str1, Float.parseFloat(str2));
                        }catch (Exception e){
                            System.out.println("Movie not found.");
                        }
                    }else {
                        System.out.println("Load file again.");
                    }
                    System.out.println();
                    break;
            }

        }while(!option.equals("7"));
        System.out.println();
        System.out.println("Bye Bye!");

    }

    public static void main(String[] args) {
        Netflix net = new Netflix();
        net.menuOptions();
    }
}
