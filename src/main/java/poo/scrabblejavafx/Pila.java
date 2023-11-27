package poo.scrabblejavafx;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Vector;
import java.util.Random;

/**
 * Clase para representar la totalidad de fichas del tablero con sus respectivos atributos y métodos.
 */
public class Pila{

    private Vector<Integer> totalDeLetras;

    private Vector<Ficha> fichasBarajadas;
    private Random random;
    private char caracteres[];

    /**
     * Constructor de la clase pila el cual genera el vector
     * donde se almacenarán las fichas al igual que genera la pila.
     */
    public Pila() {
        this.totalDeLetras = new Vector<>(28);
        this.caracteres = new char[28];
        colocarCaracteres(); // se llama para que dentro de este arreglo existan las 27 letras que se van a utilizar
        random = new Random();
        iniciarcantidades();
        crearFichasBarajadas();
    }

    public void iniciarcantidades() {
        totalDeLetras.add(12);   // a
        totalDeLetras.add(2);    // b
        totalDeLetras.add(4);    // c
        totalDeLetras.add(5);    // d
        totalDeLetras.add(12);   // e
        totalDeLetras.add(1);    // f
        totalDeLetras.add(2);    // g
        totalDeLetras.add(2);    // h
        totalDeLetras.add(6);    // i
        totalDeLetras.add(1);    // j
        totalDeLetras.add(1);    // k
        totalDeLetras.add(4);    // l
        totalDeLetras.add(2);    // m
        totalDeLetras.add(5);    // n
        totalDeLetras.add(9);    // o
        totalDeLetras.add(2);    // p
        totalDeLetras.add(2);    // q
        totalDeLetras.add(5);    // r
        totalDeLetras.add(6);    // s
        totalDeLetras.add(4);    // t
        totalDeLetras.add(5);    // u
        totalDeLetras.add(1);    // v
        totalDeLetras.add(1);    // w
        totalDeLetras.add(1);    // x
        totalDeLetras.add(1);    // y
        totalDeLetras.add(1);    // z
        totalDeLetras.add(1);    // ñ
        totalDeLetras.add(2);    // blank
    }


    public Vector<Integer> getTotaldeletras() {
        return totalDeLetras;
    }

    public void setTotaldeletras(Vector<Integer> nuevoTotal) {
        totalDeLetras = nuevoTotal;
    }


    //TODO este método se podría eliminar porque no se usa. Revisar si eliminan y si no, hacerle javadoc
    private void colocarCaracteres() {
        String letras = "abcdefghijklmnopqrstuvwxyzñ*";
        for (int i = 0; i < 28; i++) {
            this.caracteres[i] = letras.charAt(i);
        }
    }

    /**
     * Método para crear el vector que va a contener las fichas en el juego. De este se van a extraer a la hora de barajar
     */
    private void crearFichasBarajadas() {
        String letras = "abcdefghijklmnopqrstuvwxyzñ*";
        this.fichasBarajadas = new Vector<>();
        for (int i = 0; i < 28; i++) {
            for (int j = 0; j < totalDeLetras.get(i); j++) {
                Ficha nuevaFicha = new Ficha(letras.charAt(i));
                fichasBarajadas.add(nuevaFicha);
            }
        }
        // esta parte es para barajar el cotenedor de las fichas (la bolsa)
        Collections.shuffle(fichasBarajadas);
    }

    /**
     * Método para agarrar una ficha random de la pila.
     * Se encarga de devolver el objeto y quitarlo del vector que contiene las fichas.
     * @return la ficha que se agarró.
     */
    public Ficha agarrarficha() {
        Ficha aAsignar = fichasBarajadas.get(0);
        fichasBarajadas.removeElementAt(0);
        return aAsignar;
    }

    public void ingresarFicha(Ficha ficha){
        fichasBarajadas.add(ficha);
    }

    /**
     * Método para obtener el tamaño de la pila.
     * @return la cantidad de fichas en la pila.
     */
    public int getStackSize(){
        return this.fichasBarajadas.size();
    }

    /**
     * Método de acceso el cual obtiene la pila.
     * @return un vector de fichas que representa la pila.
     */
    public Vector<Ficha> getStack(){
        return this.fichasBarajadas;
    }

}
