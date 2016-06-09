package material;

import material.tree.Position;
import material.tree.binarytree.LinkedBinaryTree;

import java.util.*;

/**
 * Created by Alejandro on 3/6/16.
 */
public class Huffman {

    private static class NodeHuffman {

        String character;
        int frequency;

        public NodeHuffman(String character, int frequency) {
            this.character = character;
            this.frequency = frequency;
        }

        public NodeHuffman() {

        }

        public String getCharacter() {
            return character;
        }

        public void setCharacter(String character) {
            this.character = character;
        }

        public int getFrequency() {
            return frequency;
        }

        public void setFrequency(int frequency) {
            this.frequency = frequency;
        }

        @Override
        public String toString() {
            return "NodeHuffman{" +
                    "character='" + character + '\'' +
                    ", frequency=" + frequency +
                    '}';
        }
    }

    private static ArrayList<LinkedBinaryTree<NodeHuffman>> hTrees = new ArrayList<>(); //Lista de trees
    private static ArrayList<NodeHuffman> charactersString = new ArrayList<>(); //Array donde guardamos los elementos de la frase con frc
    private static Position<NodeHuffman> nodoCaracter; //nodo que guardara el character de la funcion buscarNodoPorLetra

    /**
     * Recibimos un string.
     * Separamos la cadena carácter por carácter con split y lo metemos en un array de Strings.
     * Cambiamos los espacios por sp y los retornos de carro por lf. La frecuencia va a ser 1.
     * Después eliminamos los nodos repetidos y cambiamos la frecuencia de los que hagan falta.
     * Ordenamos los carácteres por frecuencia.
     * LLamada a análisis.
     * @param sentence
     */
    public static void constructor(String sentence) {
        String[] campos = sentence.split("");
        for(String c : campos){
            if(c.equals(" ")){
                charactersString.add(new NodeHuffman("sp", 1));
            }else if(c.equals("\r")){
                charactersString.add(new NodeHuffman("lf", 1));
            }else{
                charactersString.add(new NodeHuffman(c, 1));
            }
        }
        uniqueList(charactersString);
        orderChars(charactersString);
        analysis(charactersString);
    }

    /**
     * Eliminamos los nodos repetidos y cambiamos la frecuencia de los que hagan falta.
     * @param list
     */
    public static void uniqueList(List<NodeHuffman> list) {
        for (int i = 0; i < list.size() - 1; ++i) {
            for (int j = i + 1; j < list.size(); ++j) {
                if (list.get(i).getCharacter().equalsIgnoreCase(list.get(j).getCharacter())) {
                    list.remove(j);
                    list.get(i).setFrequency(list.get(i).getFrequency()+1);
                    --j;
                    --i;
                    if (i < 0) {
                        i = 0;
                    }
                }
            }
        }
    }

    /**
     * Ordenamos los carácteres por frecuencia.
     * @param listHuffman
     */
    private static void orderChars(List<NodeHuffman> listHuffman) {
        NodeHuffman aux;
        int tam = listHuffman.size() - 1;
        for (int i = 1; i < listHuffman.size(); i++) { //método de la burbuja
            for (int j = tam; j >= i; j--) {
                if (listHuffman.get(j).getFrequency() < listHuffman.get(j - 1).getFrequency()) {
                    aux = listHuffman.get(j);
                    listHuffman.set(j, listHuffman.get(j - 1));
                    listHuffman.set(j - 1, aux);
                }
            }
        }
        System.gc(); //Llamamos al recolector de basura para que el array este limpio.
    }

    /**
     * Primero creamos un árbol nuevo con cada nodo del array de caracteres.
     * Luego unimos los dos árboles con menos frecuencia en un árbol cuya root es un nodo sin char pero con
     * frecuencia, la cual es la suma de las frecuencias de los nodos del árbol. Recursivo hasta que sólo haya un tree.
     * @param listHuffman
     */
    private static void analysis(ArrayList<NodeHuffman> listHuffman) {
        for(NodeHuffman node : listHuffman){
            LinkedBinaryTree<NodeHuffman> newTree = new LinkedBinaryTree<>();
            newTree.addRoot(node);
            hTrees.add(newTree);
        }
        while(hTrees.size() > 1){
            LinkedBinaryTree<NodeHuffman> newTree = new LinkedBinaryTree<>();
            int frec1 = hTrees.get(0).root().getElement().getFrequency();//Como ya están ordenados cogemos los 2 primeros
            int frec2 = hTrees.get(1).root().getElement().getFrequency();
            newTree.addRoot(new NodeHuffman("", frec1+frec2)); //añadimos root del nuevo arbol con string vacío y suma de frecs

            newTree.moveSubTree(hTrees.get(0).root(), newTree.root()); //Insertamos los dos árboles
            newTree.moveSubTree(hTrees.get(1).root(), newTree.root()); //Gracias a moveSubTree uno sera izq y el otro der.

            hTrees.add(newTree);
            hTrees.remove(hTrees.get(1)); //Borramos los dos árboles. Primero el segundo para no equivocarnos.
            hTrees.remove(hTrees.get(0)); //En teoría podria valer borrando 2 veces htrees.get(0)
            orderTrees(hTrees); //Reordenamos árboles.
        }
    }

    /**
     * Ordenamos los árboles. Método de la burbuja.
     * @param listTrees
     */
    private static void orderTrees(ArrayList<LinkedBinaryTree<NodeHuffman>> listTrees) {  //Ordenamos el arraylist de arboles
        LinkedBinaryTree<NodeHuffman> aux;
        int tam = listTrees.size() - 1;
        for (int i = 1; i < listTrees.size(); i++) {
            for (int j = tam; j >= i; j--) {
                if (listTrees.get(j).root().getElement().getFrequency() <
                        listTrees.get(j - 1).root().getElement().getFrequency()) {
                    aux = listTrees.get(j);
                    listTrees.set(j, listTrees.get(j - 1));
                    listTrees.set(j - 1, aux);
                }
            }
        }
    }

    /**
     * Primero buscamos el caracter en el arbol y nos devuelve su position.
     * Recorremos el arbol hacia arriba obteniendo los padres.
     * @param character
     * @return
     */
    private static String encode(String character) throws IndexOutOfBoundsException{
        String result = "";
        LinkedBinaryTree<NodeHuffman> tree = hTrees.get(0);
        Position<NodeHuffman> pos = searchByChar(tree, character);
        Position<NodeHuffman> posParent = tree.parent(pos);
        if(pos.equals(tree.root())){
            throw new IndexOutOfBoundsException("Something was wrong.");
        }
        while(!tree.isRoot(posParent)){
            if (tree.left(posParent).equals(pos)) {
                result += "0";
                if (tree.isRoot(posParent)) {
                    return result;
                }
                pos = posParent;
                posParent = tree.parent(posParent);
            } else if (tree.right(posParent).equals(pos)) {
                result += "1";
                if (tree.isRoot(posParent)) {
                    return result;
                }
                pos = posParent;
                posParent = tree.parent(posParent);
            }
        }
        if (tree.left(posParent).equals(pos)) { //metemos último digito del código correspondiente a la raiz
            result += "0";
        }
        if (tree.right(posParent).equals(pos)) {
            result += "1";
        }
        return result;
    }

    /**
     * Buscamos el carcter por el árbol con un iterator. Si no no lo encuentra lanza excepción.
     * @param tree
     * @param c
     * @return position of found character
     * @throws IndexOutOfBoundsException
     */
    private static Position<NodeHuffman> searchByChar (LinkedBinaryTree<NodeHuffman> tree, String c)
        throws IndexOutOfBoundsException{
        if(hTrees.isEmpty()){
            throw new IndexOutOfBoundsException("Not elements in tree.");
        }
        Iterator<Position<NodeHuffman>> it = tree.iterator();
        while (it.hasNext()){
            Position<NodeHuffman> pos = it.next();
            if(pos.getElement().getCharacter().equalsIgnoreCase(c)){
                return pos;
            }
        }
        throw new IndexOutOfBoundsException("Character not found.");
    }

    /**
     * Método que pinta por pantalla el árbol.
     * @param trees
     */
    public void printLinkedTree(ArrayList<LinkedBinaryTree<NodeHuffman>> trees){
        System.out.println("*** Huffman Tree ***");
        System.out.println();
        Iterator<Position<NodeHuffman>> it = trees.get(0).iterator();
        Position <NodeHuffman> tabs = null;
        while(it.hasNext()){
            Position<NodeHuffman> member = it.next();
            tabs = member;
            while (tabs != trees.get(0).root()){
                tabs = trees.get(0).parent(tabs);
                System.out.print("      ");
            }
            String n = member.getElement().getCharacter();
            if(n.equals(""))
                n = "(empty)";
            else
                n = "node: " + n;

            String np =null;
            try {
                np = String.valueOf(trees.get(0).parent(member).getElement().getFrequency());
            }catch (Exception e){
                np = "";
            }

            System.out.println(" - (" + n + " frec: " + member.getElement().getFrequency() + ")  parentFrec: " + np);
        }
        System.out.println();
    }

    /**
     * Método que muestra el menú del programa Huffman.
     */
    public void menuOptions (){
        Scanner board = new Scanner(System.in);
        String option;
        do {
            System.out.println("--------------------- || 1 Analize String");
            System.out.println("-*-*-*_HUFFMAN_*-*-*- || 2 Encode Character");
            System.out.println("--------------------- || 3 Exit");
            System.out.println();
            option = board.nextLine();
            switch (option) {
                case "1":
                    System.out.println("--- Analize String ---");
                    System.out.println("Enter the phrase to analyze: ");
                    String sentence = board.nextLine();
                    sentence = sentence + "\r";  //Retorno de carro
                    hTrees.clear();
                    charactersString.clear();
                    constructor(sentence.toUpperCase());
                    printLinkedTree(hTrees);
                    break;
                case "2":
                    String character;
                    try {
                        System.out.println("--- Encode Character ---");
                        System.out.println("Enter a character: ");
                        character = board.nextLine();
                        System.out.println("Huffman code: " + encode(character) + "\n");
                    } catch (IndexOutOfBoundsException error) {
                        error.printStackTrace();
                    }
                    break;
            }
        } while (!option.equals("3"));
        System.out.println("Bye bye!");
    }

    public static void main(String[] args){

        Huffman huff = new Huffman();
        huff.menuOptions();

    }
}
