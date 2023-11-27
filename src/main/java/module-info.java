module poo.scrabblejavafx {
    requires javafx.controls;
    requires javafx.fxml;


    opens poo.scrabblejavafx to javafx.fxml;
    exports poo.scrabblejavafx;
}