package poo.scrabblejavafx;

import javafx.fxml.FXML;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Modality;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.stage.Stage;

import java.util.Vector;


public class ScrabbleGameBoard {



    private String nombre1;
    private String nombre2;
    private String nombre3;
    private String nombre4;

    private Juego partida; // instancia que regula los tableros, jugadores, y cumplimiento de las reglas del juego
    private Jugador jugadorActual;


    private Ficha fichaActual; // apunta a una ficha temporal que se podria mover

    private Vector<Jugador> jugadores;

    private Vector<Jugador> jugadoresEnTurnoFinal;

    private int turnoActual; // guarda el numero de turnos jugados

    private int pilaActual; // guarda la cantidad de fichas restantes en la pila

    private boolean vieneDelSoporte;

    private int indiceSoporte; // guarda la posicion en el soporte que tiene la ficha actual

    private int primerJugadorIndex;
    private Button botonPrevio;

    private Button[][] botonesTablero;

    private String[][] copiaPotenciadasPrevias;

    private int cantidadPreviaDeFichas;

    private boolean cambiandoFichas = false;

    @FXML
    private AnchorPane rootPane;

    @FXML
    private HBox topHBox;

    @FXML
    private Label currentPlayerLabel;

    @FXML
    private Label turnLabel;

    @FXML
    private Button validateTurnButton;

    @FXML
    private Button drawTileButton;

    @FXML
    private Button endTurnButton;

    @FXML
    private Button RestoreTurn;

    @FXML
    private VBox mainVBox;

    @FXML
    private GridPane gameGrid;

    @FXML
    private GridPane soporteGridPane;

    /**
     * método que se invoca automáticamente cuando el "form" que controla esta clase se carga en una escena de JavaFX.
     * Se encarga de instanciar los objetos de JavaFX existentes en la interfaz para que puedan ser manipulados o interactuar
     * con ellos por medio de métodos vinculados a eventos de cada objeto.
     */
    public void initialize() {
        crearBotonesTablero(); // Call a method to create buttons
        crearBotonesSoporte();
        this.jugadores = new Vector<>();
        this.jugadoresEnTurnoFinal = new Vector<>();
    }

    /**
     * Se utiliza para crear los botones en tiempo de ejecución. El contenedor gridPane del soporte se declara vacío
     * previo a la ejecución de la aplicación, así que al inicializarse esta pantalla, se llama este método que instancia
     * todos los botones respectivos, les asigna un índice, un estilo, y un método de manejo de acceso.
     */
    private void crearBotonesTablero() {
        botonesTablero = new Button[15][15];
        gameGrid.setHgap(2);
        gameGrid.setVgap(2);
        int rows = 15;
        int columns = 15;
        int contieneFicha = 0;
        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < columns; col++) {
                botonesTablero[row][col] = new Button("Button " + row + "-" + col);
                // se almacena información importante dentro del botón, como su fila, columna, y si este tiene o no una ficha.
                botonesTablero[row][col].setUserData(new int[]{row, col, contieneFicha});
                botonesTablero[row][col].setText("");
                botonesTablero[row][col].setFont(new Font(16));
                botonesTablero[row][col].setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
                // Define el método encargado de manejar el evento de haber dado click en el
                botonesTablero[row][col].setOnAction(e -> manejarClickFichaMatriz(e));

                gameGrid.add(botonesTablero[row][col], col, row); // Añade el botón al grid
                //GridPane.setRowIndex(button, row);
                //GridPane.setColumnIndex(button, col);
            }
        }
    }

    /**
     * Se utiliza para crear los botones en tiempo de ejecución. El contenedor gridPane del soporte se declara vacío
     * previo a la ejecución de la aplicación, así que al inicializarse esta pantalla, se llama este método que instancia
     * todos los botones respectivos, les asigna un índice, un estilo, y un método de manejo de acceso.
     */
    private void crearBotonesSoporte() {
        int filas = 3;
        int columnas = 15;
        int posSoporte = 0;
        int hayFichaDentro = 0;
        for (int i = 0; i < filas; i++) {
            for (int j = 0; j < columnas; j++) {
                Button button = new Button("SoporteBtn " + i + '-' + columnas);
                button.setUserData(new int[] {i, j, posSoporte, hayFichaDentro}); // almacena la fila y columna igual que en el tablero
                button.setText("");
                button.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
                //button.setFont(new Font(14));
                button.setOnAction(e -> manejarClickFichaSoporte(e));
                soporteGridPane.add(button, j, i);
                posSoporte ++;
            }
        }
    }

    /**
     * Método para controlar los clicks en los botones que representan el tablero. Se asigna durante tiempo de ejecución
     * a todos los botones del tablero, de modo que no se necesita crear un método único para cada botón.
     *  el click realizado por el jugador. Se utiliza para obtener la referencia al botón que fue presionado.
     */

    private void manejarClickFichaMatriz(ActionEvent event) {
        Button clickedButton = (Button) event.getSource();
        int[] rowIndex = (int[]) clickedButton.getUserData();
        int row = rowIndex[0];
        int col = rowIndex[1];
        int tieneFicha = rowIndex[2];
        if(tieneFicha == 0) {
            System.out.println("entró a que el botón no tiene ficha");
            if (fichaActual != null && vieneDelSoporte) {
                System.out.println("entró a que la ficha no es nula y viene del soporte");
                cambiandoFichas = false;
                //TODO setear el texto del botón de cambio de fichas al default (cambiar ficha)
                //quiere decir que vamos a sacar una ficha del soporte y ponerla en el boton que se hizo click
                partida.getTemporalMesa().ingresarFicha(fichaActual, row, col, jugadorActual); // ya coloque la ficha
                fichaActual = null;
                indiceSoporte = 0;
                vieneDelSoporte = false;
                int[] datosBoton = (int[]) clickedButton.getUserData();
                datosBoton[2] = 1; //ahora el botón del tablero si tiene ficha.

                // no hace falta a los botones previos (los del soporte) ponerles si contienen o no ficha porque al llamar este método se reconfigura.
                this.setSoporteInicial(jugadorActual);
                this.cargarTableroTemporal();
            }
        }
        else {
            if (fichaActual == null) {
                showErrorMessage("No puede manipular fichas del tablero. Si desea corregir un error debe presionar Restaurar Jugada.");
            }
        }

        System.out.println("Button clicked at Row " + row + ", Column " + col);
    }

    /**
     * Método para controlar los clicks en los botones que representan el soporte. Se asigna durante tiempo de ejecución
     * a todos los botones del soporte, de modo que no se necesita crear un método único para cada botón.
     *  el click realizado por el jugador. Se utiliza para obtener la referencia al botón que fue presionado.
     */

    private void manejarClickFichaSoporte(ActionEvent event) {
        Button clickedButton = (Button) event.getSource();

        int[] rowIndex = (int[]) clickedButton.getUserData();
        int row = rowIndex[0];
        int col = rowIndex[1];
        int indiceOriginal = rowIndex[2];
        int tieneFicha = rowIndex[3];

        if(fichaActual == null) {
            //System.out.println("entro a que ficha actual no es nula");
            if (tieneFicha == 1) {
                if (!cambiandoFichas){
                    System.out.println("entró a que el botón clicado sí tiene ficha");
                    this.fichaActual = jugadorActual.escogerficha(indiceOriginal);
                    System.out.println("ficha Actual: " + fichaActual.getLetra() + fichaActual.getPuntaje());
                    indiceSoporte = indiceOriginal;
                    vieneDelSoporte = true;
                    this.setSoporteInicial(jugadorActual);
                    this.cargarTableroTemporal();
                }
                else {
                    // aquí es donde se entra cuando se quiere cambiar ficha
                }
            }
        }
        else {
            //System.out.println("entra a que la ficha actual no era nula y va a cambiar la referencia");
            if (vieneDelSoporte && tieneFicha == 1) {
                if(!cambiandoFichas) {
                    // lo que quiere hacer aqui es escoger otra ficha del soporte
                    this.fichaActual = jugadorActual.escogerficha(indiceOriginal);
                    //System.out.println("ficha actual: " + fichaActual.getNum() + fichaActual.getColor());
                }
                else {
                    //TODO: agregar la lógica de cambiar fichas
                }
            } else if (vieneDelSoporte && tieneFicha == 0) {
                this.fichaActual = null;
            }
        }


        System.out.println("Button clicked at Row " + row + ", Column " + col);
        System.out.println("La ficha en el soporte esta en el indice: " + indiceOriginal);
    }


    /**
     * Método llamado por la pantalla inicial cuando el usuario empieza el juego, utilizado para pasarle los nombres a
     * esta clase y que pueda crear los objetos de tipo Jugador.
     * @param input1 la cadena de entrada en el campo para el jugador 1
     * @param input2 la cadena de entrada en el campo para el jugador 2
     * @param input3 la cadena de entrada en el campo para el jugador 3
     * @param input4 la cadena de entrada en el campo para el jugador 4
     */
    public void initNombres(String input1, String input2, String input3, String input4) {

        setNombre1(input1);
        setNombre2(input2);
        setNombre3(input3);
        setNombre4(input4);

        startGame();
    }

    /**
     * Método que se encarga de iniciar el juego, tanto en la lógica como en la interfaz. Define el valor de variables
     * importantes e inicializa otras como la partida.
     */
    private void startGame() {
        this.jugadores.clear(); // para asegurarse que antes de jugar por primera vez no exista referencia a otros jugadores

        this.partida = getJuego();

        primerJugadorIndex = partida.determinarOrden();
        Jugador primerJugador = partida.getJugadores().get(primerJugadorIndex);
        this.jugadorActual = primerJugador;
        System.out.println(primerJugador); // para depurar y ver si se crea el jugador correctamente
        partida.agarrarfichas();

        boolean gameOver = false;
        turnoActual = 1;
        pilaActual = partida.getTablero().getFichas().getStackSize();
        fichaActual = null;
        //coordenadasFicha = new int[]{-1, -1};
        botonPrevio = null;
        currentPlayerLabel.setText("Jugando: " + primerJugador.getNombre());
        setTableroInicial();
        this.copiaPotenciadasPrevias = partida.getTablero().getMatrizFichas();
        setSoporteInicial(primerJugador);
        // TODO cargarTableroTemporal(); se cambió por set tablero inicial ya que en la primera jugada no hay fichas.
        turnLabel.setText("Turno: " + turnoActual);
        drawTileButton.setText("Tomar ficha (" + pilaActual + ")");
        for (Jugador jugador : partida.getJugadores()) {
            System.out.println(jugador);
            System.out.println("cantidad de fichas: " + jugador.getFichasEnMano().getCantfichas());
            System.out.println("fichas: ");
            for (Ficha fichaDelJugador : jugador.getFichasEnMano().getFichas()) {
                System.out.println("letra: " + fichaDelJugador.getLetra() + ", puntaje: " + fichaDelJugador.getPuntaje());
            }
        }
    }


    /**
     * Método aislado del flujo principal del juego para evitar hacerlo un bloque muy grande y perder legibilidad.
     * Se encarga de crear la instancia del juego a usar durante la partida, y se crean juegos nuevos para cada partida.
     */
    private Juego getJuego() {
        Juego partida = new Juego();
        Jugador j1 = new Jugador();
        j1.setNombre(nombre1);
        j1.setFichasEnMano();
        partida.agregarjugador(j1);
        jugadores.add(j1);

        Jugador j2 = new Jugador();
        j2.setNombre(nombre2);
        j2.setFichasEnMano();
        partida.agregarjugador(j2);
        jugadores.add(j2);

        if (!nombre3.isEmpty()) {
            Jugador j3 = new Jugador();
            j3.setNombre(nombre3);
            j3.setFichasEnMano();
            partida.agregarjugador(j3);
            jugadores.add(j3);
        }

        if (!nombre4.isEmpty()) {
            Jugador j4 = new Jugador();
            j4.setNombre(nombre4);
            j4.setFichasEnMano();
            partida.agregarjugador(j4);
            jugadores.add(j4);
        }
        return partida;
    }

    /**
     * Método para colocar la apariencia en el tablero que se requiere para reconocer las casillas potenciadas.
     * Utiliza una matriz de apoyo que se encuentra almacenada en la lógica del juego, y al momento de ejecutarse, según los valores de la matriz, cambia el estilo de los botones.
     */
    private void setTableroInicial() {
        String[][] matCasillas = partida.getTablero().getMatrizFichas();
        Button botonTablero;
        String actual;
        for (int i = 0; i < 15; i++) {
            for (int j = 0; j < 15; j++) {
                botonTablero = getButtonByRowColumnIndex(i, j, gameGrid);
                actual = matCasillas[i][j];
                switch (actual) {
                    case "2L" -> botonTablero.setStyle("-fx-background-color: rgba(255, 155, 0); -fx-text-fill: black;");
                    case "2W" -> botonTablero.setStyle("-fx-background-color: rgba(0, 255, 255, 0.7); -fx-text-fill: black;");
                    case "3L" -> botonTablero.setStyle("-fx-background-color: rgba(0, 0, 255); -fx-text-fill: black;");
                    case "3W" -> botonTablero.setStyle("-fx-background-color: rgba(255, 0, 0); -fx-text-fill: black;");
                    case "" -> botonTablero.setStyle("-fx-background-color: rgba(0, 204, 102, 0.8); -fx-text-fill: black");
                }
            }
        }
        botonTablero = getButtonByRowColumnIndex(7, 7, gameGrid);
        botonTablero.setText("⭐");
    }


    /**
     * Método para actualizar el estado .
     * Recorre la matriz de botones y les cambia la apariencia (color y texto) para que representen correctamente la
     * matriz existente en la lógica del juego.
     */

    private void setSoporteInicial(Jugador jugadorActual) {
        indiceSoporte = 0;
        System.out.println("Sus fichas: ");
        jugadorActual.printFichas();
        int sizeSoporte = jugadorActual.cantFichas();
        for (Node boton : soporteGridPane.getChildren()) {
            Button button = (Button) boton; // convierte el objeto de tipo nodo a un objeto de tipo boton
            if (indiceSoporte < sizeSoporte){// deja de recorrer el arreglo de fichas cuando llegue a la ultima
            Ficha fichaActual = jugadorActual.getFichasEnMano().getficha(indiceSoporte);
                // como el comodín tiene un *, se hace una validación adicional para cambiar el texto
                if(fichaActual.getLetra() == '*') {
                    button.setText("");
                }
                else {
                    button.setText(String.valueOf(fichaActual.getLetra()));
                }
                button.setStyle("");

                int[] infoAlmacenada = (int[]) button.getUserData();
                infoAlmacenada[3] = 1; // se usa como un booleano para saber si el botón tiene ficha o no
                button.setUserData(infoAlmacenada);

                // Aquí había un switch para saber el color pero ahora las fichas no tienen color.
            }
            else {
                button.setText("");
                button.setStyle("-fx-background-color: rgba(200, 200, 200, 0.5); -fx-text-fill: black;");
                int[] infoAlmacenada = (int[]) button.getUserData();
                infoAlmacenada[3] = 0;
                button.setUserData(infoAlmacenada);
            }
            indiceSoporte++;
        }
    }

    /**
     * Método para cambiar el estado de la matriz del tablero al cargar el último estado del tablero temporal.
     * Recorre la matriz de botones y les cambia la apariencia (color y texto) para que representen correctamente la
     * matriz existente en la lógica del juego.
     */

    private void cargarTableroTemporal() {
        Mesa tableroTemporal = partida.getTemporalMesa(); // matriz de fichas
        String[][] powerUps = partida.getTablero().getMatrizFichas(); // matriz de casillas potenciadas
        System.out.println("Tablero temporal: ");
        tableroTemporal.imprimirmatriz();
        int filaMesa = 0;
        int columnaMesa = 0;
        Ficha fichaActual;
        for (int i = 0; i < 15; i++) {
            for (int j = 0; j < 15; j++) {
                Button botonActual = getButtonByRowColumnIndex(i, j, gameGrid);
                fichaActual = tableroTemporal.getTablero()[i][j];
                if (fichaActual != null) {
                    //Poner la lógica de que se carguen como si fueran fichas
                    if(fichaActual.getLetra() =='*') {
                        botonActual.setText("");
                    }
                    else {
                        botonActual.setText(String.valueOf(fichaActual.getLetra()));
                    }
                    botonActual.setStyle("");

                    // cambiar el valor del arreglo de user Data para que marque que si contiene ficha
                    int[] infoAlmacenada = (int[]) botonActual.getUserData();
                    infoAlmacenada[2] = 1;
                    botonActual.setUserData(infoAlmacenada);
                }
                else {
                    String casillaPotenciada = powerUps[i][j];
                    // se cambia la apariencia del boton para que refleje la ficha que representa
                    switch (casillaPotenciada) {
                        case "2L" -> botonActual.setStyle("-fx-background-color: rgba(255, 155, 0); -fx-text-fill: black;");
                        case "2W" -> botonActual.setStyle("-fx-background-color: rgba(0, 255, 255, 0.7); -fx-text-fill: black;");
                        case "3L" -> botonActual.setStyle("-fx-background-color: rgba(0, 0, 255); -fx-text-fill: black;");
                        case "3W" -> botonActual.setStyle("-fx-background-color: rgba(255, 0, 0); -fx-text-fill: black;");
                        case "" -> botonActual.setStyle("-fx-background-color: rgba(0, 204, 102, 0.8); -fx-text-fill: black");
                    }

                    botonActual.setText("");
                    int[] infoAlmacenada = (int[]) botonActual.getUserData();
                    infoAlmacenada[2] = 0;
                    botonActual.setUserData(infoAlmacenada);
                }
            }
        }
    }

    /**
     * Método para cambiar el estado de la matriz del tablero al cargar el último estado del tablero válido.
     * Recorre la matriz de botones y les cambia la apariencia (color y texto) para que representen correctamente la
     * matriz existente en la lógica del juego.
     */

    private void cargarTableroValido() {
        Mesa tableroValido = partida.getTablero(); // matriz de fichas
        String[][] powerUps = partida.getTablero().getMatrizFichas(); // matriz de casillas potenciadas
        System.out.println("Tablero temporal: ");
        tableroValido.imprimirmatriz();
        int filaMesa = 0;
        int columnaMesa = 0;
        Ficha fichaActual;
        for (int i = 0; i < 15; i++) {
            for (int j = 0; j < 15; j++) {
                Button botonActual = getButtonByRowColumnIndex(i, j, gameGrid);
                fichaActual = tableroValido.getTablero()[i][j];
                if (fichaActual != null) {
                    //Poner la lógica de que se carguen como si fueran fichas
                    if(fichaActual.getLetra() =='*') {
                        botonActual.setText("");
                    }
                    else {
                        botonActual.setText(String.valueOf(fichaActual.getLetra()));
                    }
                    botonActual.setStyle("");

                    // cambiar el valor del arreglo de user Data para que marque que si contiene ficha
                    int[] infoAlmacenada = (int[]) botonActual.getUserData();
                    infoAlmacenada[2] = 1;
                    botonActual.setUserData(infoAlmacenada);
                }
                else {
                    String casillaPotenciada = powerUps[i][j];
                    // se cambia la apariencia del boton para que refleje la ficha que representa
                    switch (casillaPotenciada) {
                        case "2L" -> botonActual.setStyle("-fx-background-color: rgba(255, 155, 0); -fx-text-fill: black;");
                        case "2W" -> botonActual.setStyle("-fx-background-color: rgba(0, 255, 255, 0.7); -fx-text-fill: black;");
                        case "3L" -> botonActual.setStyle("-fx-background-color: rgba(0, 0, 255); -fx-text-fill: black;");
                        case "3W" -> botonActual.setStyle("-fx-background-color: rgba(255, 0, 0); -fx-text-fill: black;");
                        case "" -> botonActual.setStyle("-fx-background-color: rgba(0, 204, 102, 0.8); -fx-text-fill: black");
                    }

                    botonActual.setText("");
                    int[] infoAlmacenada = (int[]) botonActual.getUserData();
                    infoAlmacenada[2] = 0;
                    botonActual.setUserData(infoAlmacenada);
                }
            }
        }
    }


    /**
     * Método para obtener un botón de un gridPane por medio de los índices i y j (fila y columna
     * @param row la posición en las filas del botón a obtener
     * @param column la posición en las columnas del botón a obtener
     * @param gridPane la referencia al objeto "gridPane" del que se quiere conseguir un botón
     * @return la referencia al botón en la posición deseada.
     */
    private Button getButtonByRowColumnIndex(int row, int column, GridPane gridPane) {
        for (Node node : gridPane.getChildren()) {
            if (GridPane.getRowIndex(node) == row && GridPane.getColumnIndex(node) == column && node instanceof Button) {
                return (Button) node;
            }
        }
        return null; // Botón no encontrado
    }

    /**
     * Método para el manejo de clicks sobre el botón "Validar Jugada". Es invocado automáticamente.
     */
    @FXML private void manejarBtnValidarJugada() {
        fichaActual = null;
        botonPrevio = null;

        if (partida.getTemporalMesa().estaVacia()) {
            showErrorMessage("El tablero de juego está vacío. Si desea pasar turno, presione el botón indicado.");
        }

        else if (partida.getTemporalMesa().matrizValida()) {
            if (!partida.seRealizo) {
                if (partida.getTemporalMesa().esta77()) {
                    jugadorActual.setPuntosTotales(partida.getTemporalMesa().calcularValor());
                    partida.seRealizo = true;

                    partida.getTablero().copiarMesa(partida.getTemporalMesa()); //TODO: revisar que las potenciadas se cambien bien
                    partida.getTablero().sonParteDe();

                    // se revisa si el jugador tiene menos de 7 fichas, en ese caso, se rellena su soporte hasta que tenga esa cantidad
                    partida.refillearFichas(jugadorActual);

                    int indiceSigJugador = (primerJugadorIndex + 1) % partida.getJugadores().size();
                    primerJugadorIndex = indiceSigJugador;
                    jugadorActual = partida.getJugadores().get(primerJugadorIndex);
                    cantidadPreviaDeFichas = partida.getTablero().getCantFichas();

                    turnoActual++;
                    turnLabel.setText("Turno: " + turnoActual);
                    currentPlayerLabel.setText("Jugando: " + jugadorActual.getNombre());

                    cargarTableroValido();
                    setSoporteInicial(jugadorActual);

                    if (!jugadoresEnTurnoFinal.isEmpty()) {
                        jugadoresEnTurnoFinal.clear();
                    }

                }
                else {
                    showErrorMessage("Por favor coloque la primera jugada en el centro del tablero. Si no tiene jugada presione pasar de turno.");
                }
            }
            else {
                partida.getTablero().copiarMesa(partida.getTemporalMesa()); //TODO: revisar que las potenciadas se cambien bien
                partida.getTablero().sonParteDe();

                // se revisa si el jugador tiene menos de 7 fichas, en ese caso, se rellena su soporte hasta que tenga esa cantidad
                partida.refillearFichas(jugadorActual);

                cantidadPreviaDeFichas = partida.getTablero().getCantFichas();

                int indiceSigJugador = (primerJugadorIndex + 1) % partida.getJugadores().size();
                primerJugadorIndex = indiceSigJugador;
                jugadorActual = partida.getJugadores().get(primerJugadorIndex);

                turnoActual++;
                turnLabel.setText("Turno: " + turnoActual);
                currentPlayerLabel.setText("Jugando: " + jugadorActual.getNombre());

                cargarTableroValido();
                setSoporteInicial(jugadorActual);
            }
        }
        else {
            showErrorMessage("La disposición del tablero no era válida. Se le devolvieron sus fichas y se pasó al siguiente jugador.");
            partida.getTemporalMesa().restaurarFichas(jugadorActual);
            partida.getTemporalMesa().copiarMesa(partida.getTablero());

            //TODO cambios respecto al rummikub: aquí sí se tiene que pasar al siguiente jugador
            int indiceSigJugador = (primerJugadorIndex + 1) % partida.getJugadores().size();
            primerJugadorIndex = indiceSigJugador;
            jugadorActual = partida.getJugadores().get(primerJugadorIndex);
            setSoporteInicial(jugadorActual);
            cargarTableroTemporal();

            turnoActual ++;
            turnLabel.setText("Turno: " + turnoActual);
            currentPlayerLabel.setText("Jugando: " + jugadorActual.getNombre());

        }
    }

    /**
     * Método para el manejo de clicks sobre el botón "Pasar Turno". Es invocado automáticamente.
     */
    //TODO renombrar el método para que sea pasar turno, el de validar cumple más funciones
    @FXML
    private void manejarBtnPasarTurno() {
        fichaActual = null;
        partida.getTemporalMesa().restaurarFichas(jugadorActual);
        //TODO: hacer que esto almacene los jugadores en un vector para que cuando las personas pasan turno se acabe el juego
        jugadoresEnTurnoFinal.add(jugadorActual);
        if (jugadoresEnTurnoFinal.size() >= jugadores.size()) {
            partida.sumarPuntos();
            terminarPartida();
        }
        else {
            //jugadoresEnTurnoFinal.add(jugadorActual);
            //después de agregar los que han saltado de turno, se pasa al siguiente jugador
            int indiceSigJugador = (primerJugadorIndex + 1) % partida.getJugadores().size();
            primerJugadorIndex = indiceSigJugador;
            jugadorActual = partida.getJugadores().get(primerJugadorIndex);

            turnoActual++;
            turnLabel.setText("Turno: " + turnoActual);
            currentPlayerLabel.setText("Jugando: " + jugadorActual.getNombre());
            cargarTableroTemporal();
            setSoporteInicial(jugadorActual);
        }

        /*
        fichaActual = null;
        botonPrevio = null;

        if (partida.getTemporalMesa().valorDeJugada() && partida.getTemporalMesa().matrizValida()) {
            jugadorActual.setPuedoempezar(true);
        }
        if (!jugadorActual.puedoEmpezar()) {
            showErrorMessage("Su última jugada fue inválida. Intente de nuevo");
            partida.getTemporalMesa().restaurarFichas(jugadorActual);
            partida.getTemporalMesa().copiarMesa(partida.getTablero());
            setSoporteInicial(jugadorActual);
            cargarTableroTemporal();
        }
        else {
            // entra cuando se puede cambiar de turno porque supera los 30 puntos y es valido
            if (partida.getTemporalMesa().matrizValida() && cantidadPreviaDeFichas < partida.getTemporalMesa().getCantFichas()) {

                if (jugadorActual.getFichasEnMano().getCantfichas() == 0) {
                    partida.sumarPuntos();
                    terminarPartida();
                }

                partida.getTablero().copiarMesa(partida.getTemporalMesa());
                partida.getTablero().sonParteDe();

                cantidadPreviaDeFichas = partida.getTablero().getCantFichas();

                int indiceSigJugador = (primerJugadorIndex + 1) % partida.getJugadores().size();
                primerJugadorIndex = indiceSigJugador;
                jugadorActual = partida.getJugadores().get(primerJugadorIndex);

                turnoActual ++;
                turnLabel.setText("Turno: " + turnoActual);
                currentPlayerLabel.setText("Jugando: " + jugadorActual.getNombre());

                cargarTableroValido();
                setSoporteInicial(jugadorActual);
            }
            else {
                showErrorMessage("Debe agregar una jugada válida para pasar turno, o tomar ficha.");
                partida.getTemporalMesa().restaurarFichas(jugadorActual);
                partida.getTemporalMesa().copiarMesa(partida.getTablero());
                setSoporteInicial(jugadorActual);
                cargarTableroTemporal();
            }
        }

         */
    }

    /**
     * Método para el manejo de clicks sobre el botón "Tomar Ficha". Es invocado automáticamente.
     */
    //TODO cambiar lógica porque no es de tomar ficha, es de cambiar fichas
    @FXML
    private void manejarBotonTomarFicha() {
        fichaActual = null;
        botonPrevio = null;
        if (partida.getTemporalMesa().matrizValida()) {
            Ficha tomada = partida.getTablero().agarrarpila();
            this.pilaActual--;
            partida.getTemporalMesa().restaurarFichas(jugadorActual);
            partida.getTemporalMesa().copiarMesa(partida.getTablero()); // copia al temporal el válido porque se salta lo que se haya modificado al tomar ficha

            jugadorActual.getFichasEnMano().ingresarficha(tomada);
            int indiceSigJugador = (primerJugadorIndex + 1) % partida.getJugadores().size();
            primerJugadorIndex = indiceSigJugador;
            jugadorActual = partida.getJugadores().get(primerJugadorIndex);
            cargarTableroValido();
            setSoporteInicial(jugadorActual);
            /*
            if (pilaActual > 0) { // si no es nulo es porque aun quedan fichas en la pila
                pilaActual--;
                drawTileButton.setText("Tomar ficha (" + pilaActual + ")");
                // en caso de que la persona coloca una jugada válida pero elige tomar ficha, se restauran
                partida.getTemporalMesa().restaurarFichas(jugadorActual);
                partida.getTemporalMesa().copiarMesa(partida.getTablero());

                jugadorActual.getFichasEnMano().ingresarficha(tomada);
                int indiceSigJugador = (primerJugadorIndex + 1) % partida.getJugadores().size();
                primerJugadorIndex = indiceSigJugador;
                jugadorActual = partida.getJugadores().get(primerJugadorIndex);
                cargarTableroValido();
                setSoporteInicial(jugadorActual);

                cantidadPreviaDeFichas = partida.getTablero().getCantFichas();

                turnoActual ++;
                turnLabel.setText("Turno: " + turnoActual);
                currentPlayerLabel.setText("Jugando: " + jugadorActual.getNombre());
            }
            else {
                drawTileButton.setText("Pasar turno");
                if (jugadoresEnTurnoFinal.size() == jugadores.size()) {
                    partida.sumarPuntosPilaEnCero();
                    terminarPartida();
                }
                else{
                    partida.getTemporalMesa().restaurarFichas(jugadorActual);
                    partida.getTemporalMesa().copiarMesa(partida.getTablero());

                    jugadoresEnTurnoFinal.add(jugadorActual);

                    int indiceSigJugador = (primerJugadorIndex + 1) % partida.getJugadores().size();
                    primerJugadorIndex = indiceSigJugador;
                    jugadorActual = partida.getJugadores().get(primerJugadorIndex);
                    cargarTableroValido();
                    setSoporteInicial(jugadorActual);

                    cantidadPreviaDeFichas = partida.getTablero().getCantFichas();

                    turnoActual ++;
                    turnLabel.setText("Turno: " + turnoActual);
                    currentPlayerLabel.setText("Jugando: " + jugadorActual.getNombre());
                }
            }

             */
        }
        else {
            System.out.println("botón de cambiar fichas presionado cuando la matriz no es válida");
        }
    }

    @FXML
    private void manejarBotonRestaurar() {
        fichaActual = null;
        System.out.println("click al botón para restaurar la jugada actual al inicio del turno.");
        partida.getTemporalMesa().restaurarFichas(jugadorActual);
        partida.getTemporalMesa().copiarMesa(partida.getTablero());

        setSoporteInicial(jugadorActual);
        cargarTableroTemporal();

    }

    /**
     * Método para cargar la ventana de puntajes cuando se termina la partida. No está vinculado a un botón directamente,
     * pero se invoca cuando alguno de los botones que cambian el estado de la pantalla es presionado y las condiciones
     * para terminar la partida se cumplen.
     */
    private void terminarPartida() {
        Stage currentStage = (Stage) gameGrid.getScene().getWindow();

        try {
            // Carga el archivo FXML para la pantalla de juego
            FXMLLoader loader = new FXMLLoader(getClass().getResource("ScrabbleGanador.fxml"));
            Parent puntajesRoot = loader.load();
            Scene puntajesScene = new Scene(puntajesRoot);


            // Crea un nuevo escenario para la pantalla de juego
            Stage gameBoardStage = new Stage();
            gameBoardStage.setScene(puntajesScene);
            gameBoardStage.setTitle("Scrabble - Puntajes");

            // obtener la referencia a la clase de control para volver a la pantalla de juego
            RummikubGanador control = loader.getController();
            control.recibirJugadores(this.jugadores);
            // cierra la ventana de juego y abre la de puntajes
            currentStage.close();
            gameBoardStage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Método de configuración/seteo para modificar el atributo ligado al nombre del primer jugador.
     * @param nombre1 la cadena de texto ingresada por el usuario al lanzar la aplicación.
     */
    public void setNombre1(String nombre1) {
        this.nombre1 = nombre1;
    }

    /**
     * Método de configuración/seteo para modificar el atributo ligado al nombre del segundo jugador.
     * @param nombre2 la cadena de texto ingresada por el usuario al lanzar la aplicación.
     */
    public void setNombre2(String nombre2) {
        this.nombre2 = nombre2;
    }

    /**
     * Método de configuración/seteo para modificar el atributo ligado al nombre del tercer jugador.
     * @param nombre3 la cadena de texto ingresada por el usuario al lanzar la aplicación.
     */
    public void setNombre3(String nombre3) {
        this.nombre3 = nombre3;
    }

    /**
     * Método de configuración/seteo para modificar el atributo ligado al nombre del cuarto jugador.
     * @param nombre4 la cadena de texto ingresada por el usuario al lanzar la aplicación.
     */
    public void setNombre4(String nombre4) {
        this.nombre4 = nombre4;
    }

    /**
     * Método apartado para mostrar mensajes como alerta al usuario, con el objetivo de reutilizar el código y no repetir
     * el bloque de instrucciones cada vez que se desea imprimir un mensaje.
     * @param errorMessage la cadena de texto que se desea mostrar al usuario.
     */
    private void showErrorMessage(String errorMessage) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText(errorMessage);

        // Set the dialog modality to APPLICATION_MODAL
        alert.initModality(Modality.APPLICATION_MODAL);

        // Create an "OK" button to close the dialog
        ButtonType buttonTypeOk = new ButtonType("OK", ButtonBar.ButtonData.OK_DONE);
        alert.getButtonTypes().setAll(buttonTypeOk);

        // Show the dialog and wait for the user's response
        alert.showAndWait();
    }

}
