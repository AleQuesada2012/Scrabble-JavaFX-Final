package poo.scrabblejavafx;

public class Main {

    public static void main(String[] args) {
        // Crear un juego
        Juego juego = new Juego();

        // Crear un jugador (puedes repetir esta línea para más jugadores)
        Jugador jugador = new Jugador();
        juego.agregarjugador(jugador);

        // Crear algunas fichas para el jugador
        Ficha ficha1 = new Ficha('a');
        Ficha ficha2 = new Ficha('b');
        Ficha ficha3 = new Ficha('a');
        Ficha ficha4 = new Ficha('s');
        Ficha ficha5= new Ficha('o');
        Ficha ficha6 = new Ficha('o');
        Ficha ficha7 = new Ficha('v');

        // Crear una nueva tabla con el diccionario
        // Supongo que tu clase Mesa tiene un constructor que toma un Diccionario como parámetro
        Mesa tabla = new Mesa(juego.getDiccionario());

        tabla.imprimirmatriz();


        tabla.getTablero()[7][7] = ficha1;
        tabla.getTablero()[5][8] = ficha4;
        tabla.getTablero()[6][8] = ficha5;
        tabla.getTablero()[7][8] = ficha2;
        tabla.getTablero()[8][8] = ficha6;
        tabla.getTablero()[7][9] = ficha3;
        tabla.getTablero()[7][10] = ficha7;


        System.out.println("Estado del tablero después de ingresar la ficha:");
        tabla.imprimirmatriz();

        System.out.println(tabla.matrizValida()+"matriz valida");
        System.out.println(tabla.esta77()+"esta");
    }
}