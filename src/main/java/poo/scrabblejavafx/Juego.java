package poo.scrabblejavafx;

import java.util.Collection;
import java.util.Collections;
import java.util.Vector;

/**
 * Clase que maneja la creación de un una partida o un Juego.
 */
public class Juego {

    private Mesa tablero;

    private Mesa temporalMesa;

    private Diccionario diccionario;

    private Vector<Jugador> jugadores;


    boolean seRealizo;

    /**
     * Constructor de la clase Juego la cual crea un nuevo tablero, una mesa temporal y los jugadores.
     */
    public Juego() {
        this.diccionario = new Diccionario();
        tablero = new Mesa(diccionario);
        temporalMesa = new Mesa(1,diccionario);
        jugadores = new Vector<>();
    }

    /**
     * Método de cambio para el atributo "jugadores".
     * @param jugadores un Vector que representa a los jugadores de la partida.
     */
    public void setJugadores(Vector<Jugador> jugadores) {
        this.jugadores = jugadores;
    }


    /**
     * Método para agregar jugadores al vector para guardar sus nombres.
     * @param jugador un Jugador que representa a una persona nueva jugando.
     */
    public void agregarjugador(Jugador jugador){
        jugadores.add(jugador);
    }


    /**
     * Método el cual es usado para determinar el orden en el cual va a iniciar la partida.
     * @return un int con el índice de el jugador el cual tiene la ficha más grande.
     */
    public int determinarOrden() {
        char targetLetter = 'a';

        int masCercano = Integer.MAX_VALUE;
        int indicePrimerJugador = 0;

        for (int i = 0; i < this.jugadores.size(); i++) {
            Jugador jugador = this.jugadores.get(i);
            //jugador.agregarFicha(tablero.agarrarpila());
            jugador.agregarFicha(tablero.getFichas().agarrarficha()); // ya con esto no debería asignar nulo nunca

            for (Ficha ficha : jugador.getFichasEnMano().getFichas()) {
                char letra = ficha.getLetra();

                if (letra == '*') {
                    return i;
                }

                int diferencia = Math.abs(letra - targetLetter);

                if (diferencia < masCercano) {
                    masCercano = diferencia;
                    indicePrimerJugador = i;
                }
            }
        }

        return indicePrimerJugador;
    }




    /**
     * Método el cual es usado para sumar los puntos de los jugadores y meterlos en sus vectores respectivos, cuando la partida se acaba porque un jugador se quedo sin fichas.
     */
    public void sumarPuntos(){
        int cont = 0;
        Jugador ganador = null;
        for(Jugador jugador : getJugadores()){
            // valida si el caso de finalización de la partida es el 2do: cuando un jugador se quedó sin piezas/fichas en su mano.
            if(jugador.getFichasEnMano().getCantfichas()==0){
                jugador.setGanador(true);
                ganador = jugador;

            }
            else {
                // valida si el caso de finalización es el primero: cuando todos "saltan" de turno
                int puntos = jugador.getFichasEnMano().getsumadefichas();
                jugador.setPuntosTotales(jugador.getPuntosTotales()  + (jugador.getFichasEnMano().getsumadefichas() * -1));
                cont += puntos;

            }
        }

        if(ganador!=null){
            ganador.setPuntosTotales(ganador.getPuntosTotales() + cont);

        }
    }



    /**
     * Método el cual es usado para agarrar las fichas de los jugadores para el soporte.
     */
    public void agarrarfichas(){
        for(Jugador jugador :  jugadores) {
            for (int i = 0; i < 6; i++) {
                jugador.agregarFicha(tablero.getFichas().agarrarficha()); // nueavmente, le da las fichas correctamente
            }
        }
    }

    /**
     * Método que realiza una copia de el soporte de el jugador seleccionado.
     * @param jugador un Jugador que representa a la persona jugando en el momento.
     * @return un Vector de la copia de las fichas de el jugador.
     */
    public Vector<Ficha> copiarFichasEnMano(Jugador jugador) {
        Vector<Ficha> copiedTiles = new Vector<>();

        for (Ficha ficha : jugador.getFichasEnMano().getFichas()) {
            Ficha copiedFicha = new Ficha(ficha.getLetra());
            copiedTiles.add(copiedFicha);
        }

        return copiedTiles;
    }


    /**
     * Metodo que vuelve a llenar el atril del jugador posterior a realizar una jugada
     * @param jugador jugador el cual su atril se encuentra incompleto
     */
    public void refillearFichas(Jugador jugador){
        //modificado para que no intente hacer "refill" al jugador si ya se acabó la pila
        while (jugador.getFichasEnMano().getFichas().size() < 7 && this.getTablero().getFichas().getStackSize() > 0){
                jugador.agregarFicha(tablero.getFichas().agarrarficha());
        }
    }


    /**
     * Método de acceso a el atributo "temporalMesa".
     * @return la Mesa temporal que se lleva junto con la mesa original.
     */
    public Mesa getTemporalMesa() {
        return temporalMesa;
    }

    /**
     * Método que actualiza el tablero guardado, si es válido.
     */
    public void actualizarMesa(){
        if(temporalMesa.matrizValida()){
            tablero.copiarMesa(temporalMesa);
        }
    }

    /**
     * Método usado para terminar el turno de un jugador y actualiza la mesa.
     */
    public void terminarTurno(){
        actualizarMesa();
    }

    /**
     * Metodo el cual recibe la cantidad de fichas que el jugador desea cambiar y se le devolvera la misma cantidad
     * @param jugador jugador del turno actual que desea cambiar fichas
     * @param fichas vector de ints que contiene los indices de las fichas a cambiar.
     */

    public void cambiarFichas(Jugador jugador, Vector<Ficha> fichas) {
        int tam = fichas.size();

        for (Ficha ficha : fichas) {
            Ficha fichaRemovida = jugador.getFichasEnMano().sacarFicha(ficha);

            tablero.getFichas().ingresarFicha(fichaRemovida);
        }

        Collections.shuffle(tablero.getFichas().getStack());

        for (int i = 0; i < tam; i++) {
            // Add a tile from the board's tile stack to the player's hand
            jugador.agregarFicha(tablero.getFichas().agarrarficha());
        }
    }

    /**
     * Método de acceso a el atributo "tablero".
     * @return la Mesa actual de el juego.
     */
    public Mesa getTablero() {
        return tablero;
    }

    /**
     * Método de acceso a el atributo "jugadores".
     * @return el Vector que contiene los jugadores de la partida.
     */
    public Vector<Jugador> getJugadores() {
        return this.jugadores;
    }

    public Diccionario getDiccionario() {
        return diccionario;
    }
}