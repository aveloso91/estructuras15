package material;

/**
 * Created by Alejandro on 15/6/16.
 */
public class NodeHuffman {
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
