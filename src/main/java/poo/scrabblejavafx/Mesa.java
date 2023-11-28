package poo.scrabblejavafx;

import java.util.Objects;
import java.util.Stack;
import java.util.Vector;


import java.util.ArrayList;
import java.util.concurrent.RecursiveTask;


/**
 * Clase que representa el tablero del juego.
 */
public class Mesa {
    private String[][] matrizFichas;

    private Ficha[][] tablero;

    private Diccionario diccionario;

    private Pila fichas;


    private int espaciolleno;

    /**
     * Constructor de la clase mesa el cual genera una pila de fichas y establece
     * la matriz que será el tablero.
     */
    public Mesa(Diccionario diccionario) {
        this.fichas = new Pila();
        tablero = new Ficha[15][15];
        llenarMatriz();
        this.diccionario = diccionario;
    }

    /**
     * Segundo constructor de la clase mesa el cual solamente genera una matriz
     * esta será usada para la creación de la mesa temporal.
     *
     * @param x utilizado para realizar una sobrecarga de constructores.
     */
    public Mesa(int x,Diccionario diccionario) {
        this.diccionario = diccionario;
        tablero = new Ficha[15][15];
        llenarMatriz();
    }

    /**
     * Metodo el cual llena la matriz guia con los potenciadores del tablero.
     */
    public void llenarMatriz() {
        this.matrizFichas = new String[][]{
                {"3W", "", "", "2L", "", "", "", "3W", "", "", "", "2L", "", "", "3W"},
                {"", "2W", "", "", "", "3L", "", "", "", "3L", "", "", "", "2W", ""},
                {"", "", "2W", "", "", "", "2L", "", "2L", "", "", "", "2W", "", ""},
                {"2L", "", "", "2W", "", "", "", "2L", "", "", "", "2W", "", "", "2L"},
                {"", "", "", "", "2W", "", "", "", "", "", "2W", "", "", "", ""},
                {"", "3L", "", "", "", "3L", "", "", "", "3L", "", "", "", "3L", ""},
                {"", "", "2L", "", "", "", "2L", "", "2L", "", "", "", "2L", "", ""},
                {"3W", "", "", "2L", "", "", "", "2W", "", "", "", "2L", "", "", "3W"},
                {"", "", "2L", "", "", "", "2L", "", "2L", "", "", "", "2L", "", ""},
                {"", "3L", "", "", "", "3L", "", "", "", "3L", "", "", "", "3L", ""},
                {"", "", "", "", "2W", "", "", "", "", "", "2W", "", "", "", ""},
                {"2L", "", "", "2W", "", "", "", "2L", "", "", "", "2W", "", "", "2L"},
                {"", "", "2W", "", "", "", "2L", "", "2L", "", "", "", "2W", "", ""},
                {"", "2W", "", "", "", "3L", "", "", "", "3L", "", "", "", "2W", ""},
                {"3W", "", "", "2L", "", "", "", "3W", "", "", "", "2L", "", "", "3W"}
        };
    }


    /**
     * Método que verifica si el estado de la matriz con sus fichas es válido.
     *
     * @return si las jugadas en la mesa son válidas retorna true falso de lo contrario.
     */

    public boolean matrizValida() {
        if (estaVacia()) {
            return true;
        }

        return validaHorizontal() && validaVertical()&& todasFichasConectadas();
    }

    /**
     * Metodo el cual revisa si todas las jugadas horizontales son validas
     * @return True si todas las jugadas son validas False si lo contrario
     */
    private boolean validaHorizontal() {
        boolean esvalid = false;
        Vector<Ficha> fichas = new Vector<>();

        for (int i = 0; i < 15; i++) {
            for (int j = 0; j < 15; j++) {

                if (tablero[i][j] != null) {
                    fichas.add(tablero[i][j]);

                } else {
                    if(!fichas.isEmpty()) {
                        if(fichas.size() == 1){
                            esvalid = true;
                        }
                        else {
                            Jugada posibleJugada = new Jugada(fichas, diccionario);

                            if (posibleJugada.jugadavalida()) {

                                esvalid = true;

                            } else {

                                return false;
                            }
                        }
                        fichas.clear();
                    }
                }
            }

            if (!fichas.isEmpty()) {

                if (fichas.size() == 1) {
                    esvalid = true;
                }
                else {
                    Jugada posibleJugada = new Jugada(fichas, diccionario);

                    if (posibleJugada.jugadavalida()) {

                        esvalid = true;

                    } else {

                        return false;
                    }

                    fichas.clear();
                }
            }
        }

        return esvalid;
    }


    /**
     * Metodo el cual valida las jugadas en el tablero en vertical
     * @return true si todas las jugadas horizontales son validas false si no
     */
    private boolean validaVertical() {
        boolean esvalid = false;
        Vector<Ficha> fichas = new Vector<>();

        for (int j = 0; j < 15; j++) {
            for (int i = 0; i < 15; i++) {
                if (tablero[i][j] != null) {
                    fichas.add(tablero[i][j]);
                }
                else {
                    if(!fichas.isEmpty()) {

                        if(fichas.size() == 1){

                            esvalid = true;
                        }
                        else {
                            Jugada posibleJugada = new Jugada(fichas, diccionario);

                            if (posibleJugada.jugadavalida()) {

                                esvalid = true;

                            } else {

                                return false;
                            }
                        }
                        fichas.clear();
                    }
                }
            }

            if (!fichas.isEmpty()) {
                if(fichas.size() == 1){

                    esvalid = true;
                }
                else {
                    Jugada posibleJugada = new Jugada(fichas, diccionario);

                    if (posibleJugada.jugadavalida()) {

                        esvalid = true;

                    } else {

                        return false;
                    }
                }
                fichas.clear();
            }
        }

        return esvalid;
    }


    private  Boolean esvalido(int x, int y, Vector<Vector<Boolean>> visi) {
        return x >= 0 && x < 15 && y >= 0 && y < 15 && !visi.get(x).get(y);
    }



    private   Pair<Ficha,Pair<Integer,Integer>> encontrarPrimeraFicha() {
        for (int i = 0; i < 15; i++) {
            for (int j = 0; j < 15; j++) {
                if (this.tablero[i][j] != null) {

                    Pair<Integer, Integer> coordenadas = new Pair<>(i, j);
                    Pair<Ficha,Pair<Integer,Integer>> primera = new Pair<>(this.tablero[i][j],coordenadas);
                    return primera;
                }
            }
        }
        return null;
    }

    private Vector<Pair<Integer, Integer>> vecinos(int x, int y) {
        Vector<Pair<Integer, Integer>> moves = new Vector<>();

        moves.add(new Pair<>(x + 1, y));
        moves.add(new Pair<>(x - 1, y));
        moves.add(new Pair<>(x, y + 1));
        moves.add(new Pair<>(x, y - 1));

        return moves;
    }


    private Vector<Pair<Integer,Integer>> obtenervecinos(int x, int j, Vector<Vector<Boolean>> visi) {
        Vector<Pair<Integer,Integer>> vecinas = new Vector<>();
        Vector<Pair<Integer,Integer>> posibles = vecinos(x,j);
        for ( Pair<Integer,Integer> move: posibles) {
            int i = move.getFirst(); j = move.getSecond();
            if (esvalido(i, j,visi)) {
                Pair<Integer,Integer> nueva = new Pair<>(i,j);
                vecinas.add(nueva);
            }
        }
        return vecinas;
    }


    /**
     * Metodo envargado de verificar que todas las fichas esten conectadas
     * @return true si todas las fichas estan conectadas false de lo contrario
     */
    public boolean todasFichasConectadas() {
        Vector<Vector<Boolean>> visitadas = new Vector<>();


        for (int i = 0; i < 15; i++) {
            Vector<Boolean> row = new Vector<>();


            for (int j = 0; j < 15; j++) {
                row.add(false);
            }

            visitadas.add(row);
        }


        Pair<Ficha,Pair<Integer,Integer>> primeraFicha = encontrarPrimeraFicha();

        if (primeraFicha == null) {
            return true;
        }

        visitadas.get(primeraFicha.getSecond().getFirst()).set(primeraFicha.getSecond().getSecond(), true);


        Stack<Pair<Ficha,Pair<Integer,Integer>>> stack = new Stack<>();
        stack.push(primeraFicha);

        while (!stack.isEmpty()) {
            Pair<Ficha,Pair<Integer,Integer>> fichaActual = stack.pop();
            int fila = fichaActual.getSecond().getFirst();
            int columna = fichaActual.getSecond().getSecond();


            for(Pair<Integer,Integer> move : obtenervecinos(fila,columna,visitadas)){
                if(tablero[move.getFirst()][move.getSecond()] != null) {
                    visitadas.get(move.getFirst()).set(move.getSecond(), true);
                    Ficha ficha = tablero[move.getFirst()][move.getSecond()];
                    Pair<Ficha,Pair<Integer,Integer>> nuevoMove = new Pair<>(ficha,move);
                    stack.push(nuevoMove);
                }
                
            }

        }

        for (int i = 0; i < 15; i++) {
            for (int j = 0; j < 15; j++) {
                if (!visitadas.get(i).get(j) && this.tablero[i][j] != null) {

                    return false;
                }
            }
        }

        return true;
    }


    /**
     * Metodo que obtiene todas las jugadas nuevas en horizontal en el tablero
     * @return un vector el cual contiene todas las jugadas horizontales
     */
    public Vector<Jugada> obtenerJugadas() {
        Vector<Pair<Integer, Integer>> duplas = new Vector<>();
        Vector<Jugada> jugadasNuevas = new Vector<>();
        Vector<Ficha> fichas = new Vector<>();

        for (int i = 0; i < 15; i++) {
            for (int j = 0; j < 15; j++) {
                if (tablero[i][j] != null) {
                    fichas.add(tablero[i][j]);
                    Pair<Integer, Integer> pos = new Pair<>(i, j);
                    duplas.add(pos);
                } else if (!fichas.isEmpty()) {
                    // Create a copy of the fichas vector
                    Vector<Ficha> fichasCopy = new Vector<>(fichas);
                    Vector<Pair<Integer,Integer>> copiaDuplas = new Vector<>(duplas);
                    Jugada posibleJugada = new Jugada(fichasCopy, diccionario);
                    if (posibleJugada.jugadavalida() && posibleJugada.encontrarNuevaletra()) {
                        posibleJugada.setPosiciones(copiaDuplas);
                        jugadasNuevas.add(posibleJugada);
                    }
                    fichas.clear();
                    duplas.clear();
                }
            }
            if (!fichas.isEmpty()) {
                // Create a copy of the fichas vector
                Vector<Ficha> fichasCopy2 = new Vector<>(fichas);
                Vector<Pair<Integer,Integer>> copiaDuplas2 = new Vector<>(duplas);
                Jugada posibleJugada = new Jugada(fichasCopy2, diccionario);
                if (posibleJugada.jugadavalida() && posibleJugada.encontrarNuevaletra()) {
                    posibleJugada.setPosiciones(copiaDuplas2);
                    jugadasNuevas.add(posibleJugada);
                }
                fichas.clear();
                duplas.clear();
            }
        }

        return jugadasNuevas;
    }

    /**
     * Metodo que obtiene todas las jugadas nuevas del tablero en vertical
     * @return Retorna un vector conteniendo las jugadas verticales nuevas.
     */
    public Vector<Jugada> obtenerJugadasVeticales(){
        Vector<Pair<Integer, Integer>> duplas = new Vector<>();
        Vector<Jugada> jugadasNuevas =  new Vector<>();
        Vector<Ficha> fichas = new Vector<>();
        for (int j = 0; j < 15; j++) {
            for (int i = 0; i < 15; i++) {

                if (tablero[i][j] != null) {
                    fichas.add(tablero[i][j]);
                    Pair<Integer, Integer> pos = new Pair<>(i, j);
                    duplas.add(pos);
                }
                else if (!fichas.isEmpty()) {
                    Vector<Ficha> copia = new Vector<>(fichas);
                    Vector<Pair<Integer,Integer>> copiaDuplas = new Vector<>(duplas);
                    Jugada posibleJugada = new Jugada(copia, diccionario);
                    if (posibleJugada.jugadavalida() && posibleJugada.encontrarNuevaletra()) {
                        posibleJugada.setPosiciones(copiaDuplas);
                        jugadasNuevas.add(posibleJugada);
                    }
                    fichas.clear();
                    duplas.clear();
                }



            }
            if (!fichas.isEmpty()&& !duplas.isEmpty()) {
                Vector<Ficha> copia2 = new Vector<>(fichas);
                Vector<Pair<Integer,Integer>> copiaDuplas2 = new Vector<>(duplas);
                Jugada posibleJugada = new Jugada(copia2, diccionario);
                if (posibleJugada.jugadavalida() && posibleJugada.encontrarNuevaletra()) {
                    posibleJugada.setPosiciones(copiaDuplas2);
                    jugadasNuevas.add(posibleJugada);
                }
                fichas.clear();
                duplas.clear();
            }

        }
        return jugadasNuevas;

    }

    /**
     * Metodo que calcula los valores de cada jugada realizada por el jugador en la partida nueva
     * @return
     */
    public int calcularValor(){
        int res = 0;
        int cont = 0;
        int multiplicador = 1;

        Vector<Jugada> horziontales = obtenerJugadas();
        Vector<Jugada> Verticales = obtenerJugadasVeticales();


        for(int i=0;i<horziontales.size();i++) {
            for(int j=0;j<horziontales.get(i).obtenerTamano();j++) {


                Pair<Integer, Integer> coordenadas = horziontales.get(i).obtenerPar(j);

                int x = coordenadas.getFirst();

                int y = coordenadas.getSecond();

                String casilla = matrizFichas[x][y];

                if (Objects.equals(casilla, "")) {
                    cont+=horziontales.get(i).getFichaPos(j).getPuntaje();
                };

                if (casilla.equals("3W")) {

                    cont+=horziontales.get(i).getFichaPos(j).getPuntaje();

                    multiplicador *= 3;

                    matrizFichas[x][y] = "";

                }
                if (casilla.equals("3L")) {
                    cont += horziontales.get(i).getFichaPos(j).getPuntaje() * 3;
                    matrizFichas[x][y] = "";

                }
                if (casilla.equals("2W")) {

                    cont+=horziontales.get(i).getFichaPos(j).getPuntaje();
                    multiplicador *= 2;
                    matrizFichas[x][y] = "";

                }
                if (casilla.equals("2L")) {
                    cont += horziontales.get(i).getFichaPos(j).getPuntaje() * 2;
                    matrizFichas[x][y] = "";
                }



            }
            cont*=multiplicador;
            res += cont;
            cont = 0;
            multiplicador = 1;
        }


        for(int i=0;i<Verticales.size();i++) {
            for(int j=0;j<Verticales.get(i).obtenerTamano();j++) {

                Pair<Integer, Integer> coordenadas = Verticales.get(i).obtenerPar(j);

                int x = coordenadas.getFirst();

                int y = coordenadas.getSecond();

                String casilla = matrizFichas[x][y];

                if (Objects.equals(casilla, ""))
                    cont+=Verticales.get(i).getFichaPos(j).getPuntaje();

                if (casilla.equals("3W")) {
                    cont += Verticales.get(i).getFichaPos(j).getPuntaje();

                    multiplicador *= 3;

                    matrizFichas[x][y] = "";

                }
                if (casilla.equals("3L")) {
                    cont += Verticales.get(i).getFichaPos(j).getPuntaje() * 3;
                    matrizFichas[x][y] = "";

                }
                if (casilla.equals("2W")) {

                    cont += Verticales.get(i).getFichaPos(j).getPuntaje();
                    multiplicador *= 2;
                    matrizFichas[x][y] = "";

                }
                if (casilla.equals("2L")) {
                    cont += Verticales.get(i).getFichaPos(j).getPuntaje() * 2;
                    matrizFichas[x][y] = "";
                }



            }
            cont*=multiplicador;
            res += cont;
            cont = 0;
            multiplicador = 1;
        }


        return res;
    }




    /**
     * Método para conocer si la matriz pertenece a la mesa está vacía.
     *
     * @return retorna falso si no esta vacía verdadero de lo contrario.
     */
    public boolean estaVacia() {
        for (int i = 0; i < 15; i++) {
            for (int j = 0; j < 15; j++) {

                if (tablero[i][j] != null) {
                    return false;
                }
            }
        }
        return true;
    }


    /**
     * Método para conocer la cantidad de fichas válidas dentro del tablero de 15x15.
     *
     * @return un valor entero que refleja cuántas fichas existen en la mesa.
     */
    public int getCantFichas() {
        int contador = 0;
        for (int i = 0; i < 15; i++) {
            for (int j = 0; j < 15; j++) {
                if (this.tablero[i][j] != null) {
                    contador++;
                }
            }

        }
        return contador;
    }

    public void imprimirmatriz() {
        for (Ficha[] row : this.tablero) {
            for (Ficha cell : row) {
                System.out.print((cell == null ? '_' : cell.getLetra()) + "\t");

            }
            System.out.println();
        }
    }

    /**
     * Método el cual obtiene una ficha en una posición deseada de la matriz.
     *
     * @param x fila de la matriz.
     * @param y columna de la matriz.
     * @return la ficha en la posicion deseada.
     */

    public Ficha getFichaEnXY(int x, int y) {
        return this.tablero[x][y];
    }



    /**
     * Método que restablece las fichas puestas en la mesa por el jugador a su soporte.
     *
     * @param jugador jugador el cual se le restablecerán sus fichas.
     */
    public void restaurarFichas(Jugador jugador) {
        for (int i = 0; i < 15; i++) {
            for (int j = 0; j < 15; j++) {
                if (this.tablero[i][j] != null) {
                    if (!this.tablero[i][j].isEsta()) {
                        jugador.getFichasEnMano().ingresarficha(this.tablero[i][j]);
                    }
                }
            }
        }
    }

    /**
     * Método el cual copia de los valores de una matriz perteneciente a una mesa.
     *
     * @param Mesaoriginal mesa que se desea copiar.
     */
    public void copiarMesa(Mesa Mesaoriginal) {
        for (int fila = 0; fila < tablero.length; fila++) {
            for (int col = 0; col < tablero[fila].length; col++) {
                tablero[fila][col] = Mesaoriginal.tablero[fila][col];
            }
        }
        this.matrizFichas = Mesaoriginal.getMatrizFichas();
        espaciolleno = Mesaoriginal.espaciolleno;
    }


    /**
     * Método para obtener una ficha de la pila.
     *
     * @return retorna la ficha que se obtuvo de la pila.
     */
    public Ficha agarrarpila() {
        return fichas.agarrarficha();
    }

    /**
     * Método el cual obtiene la pila de fichas de la mesa
     *
     * @return retorna la pila de la mesa.
     */
    public Pila getFichas() {
        return fichas;
    }

    /**
     * Método el cual obtiene la matriz de la mesa.
     *
     * @return retorna la matriz de la mesa.
     */
    public String[][] getMatrizFichas() {
        return this.matrizFichas;
    }

    /**
     * Método de acceso para la matriz de apoyo que contiene los indicadores de casillas potenciadas/multiplicadores
     * @return una matriz de strings donde los strings que no sean vacíos representan una casilla potenciada en su mismo (i,j)
     */

    public Ficha[][] getTablero() {
        return tablero;
    }




    /**
     * Método para poder ingresar una ficha del soporte del jugador en la matriz de la mesa.
     * @param ficha ficha que se desea colocar en la matriz.
     * @param x fila de la matriz donde se desea colocar.
     * @param y columna de la matriz donde se desea colocar.
     * @param jugador jugador el cual quiere realizar la jugada.
     */
    public void ingresarFicha(Ficha ficha, int x, int y, Jugador jugador) {
        if (this.getTablero()[x][y] == null) {
            this.getTablero()[x][y] = ficha;
            jugador.getFichasEnMano().usarficha(ficha);
        }
    }


    /**
     * Método el cual establece las fichas de la mesa como parte de las mismas.
     */
    public void sonParteDe() {
        for (int i = 0; i < 15; i++) {
            for (int j = 0; j < 15; j++) {
                if(this.tablero[i][j]!=null){
                    this.tablero[i][j].setEsta(true);
                }

            }
        }
    }



    /**
     * Metodo que verifica que la jugada principal se realizara en la posicion de la estrella.
     * @return verdadero si hay una ficha colocada en la casilla central, falso en caso contrario.
     */
    public boolean esta77(){
            if (tablero[7][7] != null) {
                return true;
            }
            return false;

    }



}