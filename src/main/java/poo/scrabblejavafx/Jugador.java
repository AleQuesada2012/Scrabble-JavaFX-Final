package poo.scrabblejavafx;

import java.util.*;

/**
 * Clase la cual representa al jugador del juego con sus respectivos atributos y métodos.
 */
public class Jugador {
    private String Nombre;
    private int puntos = 0;
    private Soporte fichasEnMano;
    private boolean puedoempezar;
    private boolean ganador;

    /**
     * Método el cual obtiene la información del nombre del jugador.
     * @return el nombre del jugador.
     */
    public String getNombre() {
        return Nombre;
    }

    /**
     * Método el cual establece el nombre del jugador.
     * @param nombre nombre el cual se le otorgara al jugador.
     */
    public void setNombre(String nombre) {
        Nombre = nombre;
    }



    /**
     * Método el cual obtiene los puntos totales del jugador.
     * @return el total de los puntos del jugador.
     */
    public int getPuntosTotales(){
        return this.puntos;
    }

    /**
     * Método el cual establece el puntaje total del jugador al ingresado
     * @param puntosTotales1 corresponde al puntaje acual del jugador.
     */
    public void setPuntosTotales(int puntosTotales1) {
        puntos+=puntosTotales1;
    }

    /**
     * Método para añadir el puntaje obtenido en la partida actual al vector de
     * puntajes para tener registro de las partidas anteriores.
     *
     * @param puntos corresponde al puntaje del jugador en la partida actual.
     */



    /**
     *Método el cual establece el soporte del jugador.
     */
    public void setFichasEnMano() {
        this.fichasEnMano = new Soporte();
    }

    /**
     * Método el cual obtiene el soporte del jugador
     * @return Soporte retorna el soporte del jugador.
     */
    public Soporte getFichasEnMano() {
        return fichasEnMano;
    }

    /**
     * Método el cual obtiene la información si el jugador es ganador.
     * @return el booleano que representa si el jugador es ganador o no.
     */
    public boolean isGanador() {
        return ganador;
    }

    /**
     * Método el cual establece si el jugador gano la partida.
     * @param ganador el booleano el cual representa si el jugador es ganador o no.
     */
    public void setGanador(boolean ganador) {
        this.ganador = ganador;
    }

    /**
     * Método el cual extrae la información si el jugador puede empezar.
     * @return el booleano que representa si el jugador puede empezar.
     */
    public boolean puedoEmpezar() {
        return puedoempezar; //TODO: definir si esto es necesario para este proyecto (Scrabble)
    }

    /**
     * Método para establecer que el jugador puede acceder al tablero posteriormente de
     * realizar la jugada de 30 o más puntos.
     * @param miTurno corresponde al booleano que representa si el jugador puede empezar o no.
     */
    public void setPuedoempezar(boolean miTurno) {
        puedoempezar = miTurno;
    }

    /**
     * Método para obtener la cantidad de fichas del soporte del jugador.
     * @return retorna el valor numérico de la cantidad de fichas del soporte.
     */
    public int cantFichas(){return fichasEnMano.getCantfichas();}

    /**
     * Método el cual selecciona una ficha en la posición deseada del soporte.
     * @param x recibe la posición en la cual la ficha deseada está colocada.
     * @return retorna la ficha deseada.
     */
    public Ficha escogerficha(int x){
        return fichasEnMano.getficha(x);
    }


    /**
     * Método para imprimir las fichas contenidas en el soporte del jugador
     */
    public void printFichas() {
        for (int i = 0; i < getFichasEnMano().getCantfichas(); i++) {
            Ficha fichaTemp = getFichasEnMano().getficha(i);
            System.out.println(i + ":" + " " + fichaTemp.getLetra() + fichaTemp.getPuntaje());
        }
    }

    /**
     * Método para ingresar una ficha en el soporte del jugador
     * @param ficha corresponde a la ficha que se quiere ingresar.
     */
    public void agregarFicha(Ficha ficha) {
        fichasEnMano.ingresarficha(ficha);
    }




    /**
     * Método para restablecer los atributos del jugador a un valor equivalente a cuando se creó por primera vez
     * a excepción de sus puntos
     */
    public void reestablecerJugador() {
        setGanador(false);
        setFichasEnMano(); // hace un soporte nuevo por lo que basta para restablecer
        setPuedoempezar(false);
    }


    @Override
    public String toString() {
        return "Jugador{" +
                "Nombre='" + Nombre + '\'' +
                ", puntos=" + puntos +
                ", fichasEnMano=" + fichasEnMano +
                ", puedoempezar=" + puedoempezar +
                ", ganador=" + ganador +
                '}';
    }
}