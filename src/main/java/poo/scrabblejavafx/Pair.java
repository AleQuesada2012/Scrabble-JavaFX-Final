package poo.scrabblejavafx;

/**
 * Clase que permite la creacion de duplas de objetos y variables
 * @param <A>
 * @param <B>
 */
public class Pair<A,B>{
    private final A first;
    private final B second;

    public Pair(A first, B second) {
        this.first = first;
        this.second = second;
    }

    public A getFirst() {
        return first;
    }

    public B getSecond() {
        return second;
    }

}