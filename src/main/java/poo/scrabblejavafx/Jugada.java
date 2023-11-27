package poo.scrabblejavafx;

import java.util.Vector;

/**
 * Clase utilizada para crear las jugadas con las fichas del tablero con sus respectivos métodos y atributos.
 */

public class Jugada {
    private Vector<Ficha> jugada;
    private Diccionario diccionario;
    //private Vector<myTuple> palabrasEnJugada; //TODO: Hay que definir como almacenar las fichas de la jugada

    private Vector<Pair<Integer,Integer>> posiciones;


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
                tieneComodin = true;
            } else {
                char letter = ficha.getLetra();
                wordBuilder.append(letter);
            }
        }

        String word = wordBuilder.toString();

        if (tieneComodin) {
            for (char letra = 'a'; letra <= 'z'; letra++) {
                String wordWithWildcard = word.replace('*', letra);
                if (diccionario.esvalida(wordWithWildcard)) {
                    return true;
                }
            }
            return false;
        } else if (word.length() >= 2 && word.length() <= 15) {
            return diccionario.esvalida(word);
        } else {
            return false;
        }
    }



    /**
     * Método para calcular el valor de una jugada según los puntos de las fichas.
     * @return un valor entero que representa los puntos de la jugada calculada.
     */

    public int valorJugada(){
        int cont = 0;
        if(this.jugadavalida()){
            for(int i=0;i<this.jugada.size();i++){
                Ficha ficha = this.jugada.get(i);
                cont+=ficha.getPuntaje();

            }
        }
        return cont;
    }

    /**
     * Método el cual obtiene la ficha en una posición deseada de la jugada.
     * @param x recibe el índice de la ficha en la jugada que se desea obtener.
     * @return retorna la ficha deseada.
     */
    public Ficha getFichaPos(int x){
        return this.jugada.get(x);

    }

    public boolean encontrarNuevaletra(){
        for(Ficha fichas : jugada){
            if(!fichas.isEsta())return true;
        }
        return false;
    }

    public void setPosiciones(Vector<Pair<Integer, Integer>> posiciones) {
        this.posiciones = posiciones;
    }

    public Vector<Pair<Integer, Integer>> getPosiciones() {
        return posiciones;
    }

    public Pair<Integer,Integer> obtenerPar(int pos){
        return posiciones.get(pos);
    }

    public int obtenerTamano(){
        return  jugada.size();
    }
}