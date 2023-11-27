package poo.scrabblejavafx;

public class Main {


    public static void main(String[] args) {
        Pila pilaTest = new Pila();

        System.out.println("Tamaño del vector en pila: " + pilaTest.getStackSize()); //debe dar 28
        for (int i = 0; i < 28; i++) {
            char letra = (char) ('a' + i);
            if(i == 26) { letra = 'ñ';}
            if (i == 27) { letra = '*';}
            System.out.println(letra + ": " + pilaTest.getStack().get(i));
        }
    }
    /*
    public static void main(String[] args) {
        // Create an instance of the Mesa class
        Mesa mesa = new Mesa();

        // Example: Get a random Ficha from the Pila and add it to the board
        Ficha ficha = new Ficha('a');
        Ficha ficha2 = new Ficha('b');
        Ficha ficha3 = new Ficha('a');

        mesa.getTablero()[0][0] = ficha;
        mesa.getTablero()[0][1] = ficha2;
        mesa.getTablero()[0][2] = ficha3;


        // Example: Print the state of the board
        mesa.imprimirmatriz();

        // Example: Check if the current board state is valid
        boolean isValid = mesa.matrizValida();
        System.out.println("Is the board valid? " + isValid);

        // Example: Get the number of Fichas on the board
        int numberOfFichas = mesa.getCantFichas();
        System.out.println("Number of Fichas on the board: " + numberOfFichas);

        // TODO: Add more operations or methods as needed for testing

        // Note: This is a basic example. You can expand and customize it based on your specific requirements.
    }
     */
}
