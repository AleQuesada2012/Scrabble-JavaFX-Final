package poo.scrabblejavafx;

import java.util.Vector;

/**
 * Clase utilizada para crear las jugadas con las fichas del tablero con sus respectivos métodos y atributos.
 */

public class Jugada {

    private Vector<Ficha> jugada;

    private Diccionario diccionario;


    private Vector<Pair<Integer,Integer>> posiciones;

    /**
     * Constructor de la clase Jugada
     * @param lista lista de las fichas que consiste la jugada
     * @param diccionario diccionario que almacena todas las fichas
     */
    public Jugada(Vector<Ficha> lista, Diccionario diccionario) {
        this.jugada = lista;
        this.diccionario = diccionario;
    }


    /**
     * Método para verificar que la secuencia de fichas es una jugada valida.
     * @return un booleano que representa el valor de verdad de la escalera.
     */

    public boolean jugadavalida() {
        StringBuilder wordBuilder = new StringBuilder();
        boolean tieneComodin = false;

        for (Ficha ficha : jugada) {
            if (ficha.getLetra() == '*') {
                char letter = ficha.getLetra();
                wordBuilder.append(letter);
                tieneComodin = true;
            } else {
                char letter = ficha.getLetra();
                wordBuilder.append(letter);
            }
        }

        String word = wordBuilder.toString();
        if (word.length() < 2 || word.length() > 15) {
            return false;
        }


        if (tieneComodin) {
            boolean posibleRespuesta = false;
            for (char letra = 'a'; letra <= 'z'; letra++) {
                String wordWithWildcard = word.replace('*', letra);
                if (diccionario.esvalida(wordWithWildcard)) {
                    posibleRespuesta = true;
                }
            }
            String wordWithWildCard = word.replace('*', 'ñ');
            if (diccionario.esvalida(wordWithWildCard)) {
                posibleRespuesta = true;
            }
            return posibleRespuesta;
        }
        else {
            return diccionario.esvalida(word);
        }

    }

    /**
     * Método el cual obtiene la ficha en una posición deseada de la jugada.
     * @param x recibe el índice de la ficha en la jugada que se desea obtener.
     * @return retorna la ficha deseada.
     */
    public Ficha getFichaPos(int x){
        return this.jugada.get(x);

    }

    /**
     * Metodo el cual encuentra jugadas que contegan fichas nuevas
     * @return un true si la jugada contiene una ficha nueva de lo contrario false
     */

    public boolean encontrarNuevaletra(){
        for(Ficha fichas : jugada){
            if(!fichas.isEsta())return true;
        }
        return false;
    }

    /**
     * Metodo que setea el atributo de posiciones a un vector de duplas de ints
     * @param posiciones  duplas de ints refiriendose a los x y y de una matriz
     */

    public void setPosiciones(Vector<Pair<Integer, Integer>> posiciones) {
        this.posiciones = posiciones;
    }

    /**
     * Metodo el cual retorna el atributo de posiciones
     * @return un vector de duplas que se refieren a las posiciones de las fichas
     */
    public Vector<Pair<Integer, Integer>> getPosiciones() {
        return posiciones;
    }

    /**
     * Metodo el cual devuelve la posicion de una ficha en especfico
     * @param pos indice de la ficha deseada
     * @return posicion de dicha ficha
     */

    public Pair<Integer,Integer> obtenerPar(int pos){
        return posiciones.get(pos);
    }

    /**
     * Metodo el cual retorna el tamano de la jugada
     * @return un numero refiriendose al tamano del vector de jugada
     */
    public int obtenerTamano(){
        return  jugada.size();
    }
}