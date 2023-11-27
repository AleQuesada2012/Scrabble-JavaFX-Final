package poo.scrabblejavafx;

/**
 * La clase ficha que crea la lógica destinada a las características de la ficha.
 */
public class Ficha {

    private char letra;

    private int puntaje;

    private boolean esta;

    /**
     * Método de acceso para el atributo "está".
     * @return un boolean de si la ficha se encuentra en la mesa o no.
     */
    public boolean isEsta() {
        return esta;
    }

    /**
     *Metodo de cambio para el atributo "está".
     * @param esta un boolean que dice si la ficha se encuentra en la mesa o no.
     */
    public void setEsta(boolean esta) {
        this.esta = esta;
    }

    /**
     * Constructor de la clase Ficha que crea una ficha con la letra dada.
     * @param letra un char que representa que letra va a ser la nueva ficha.
     */
    public Ficha(char letra){
        setLetra(letra);
        establecerPuntaje();
    }

    /**
     * Método de acceso para el atributo letra.
     * @return un char que representa la letra que esta en esa ficha.
     */
    public char getLetra() {
        return letra;
    }

    /**
     * Método de cambio para el atributo letra.
     * @param letra un char que representa la letra que va a ser la ficha.
     */
    public void setLetra(char letra) {
        this.letra = letra;
    }


    public int getPuntaje() {
        return puntaje;
    }

    public void setPuntaje(int puntaje) {
        this.puntaje = puntaje;
    }


    public void establecerPuntaje(){
        if(this.letra=='a')this.puntaje = 1;
        if(this.letra=='b')this.puntaje = 3;
        if(this.letra=='c')this.puntaje = 3;
        if(this.letra=='d')this.puntaje = 2;
        if(this.letra=='e')this.puntaje = 1;
        if(this.letra=='f')this.puntaje = 4;
        if(this.letra=='g')this.puntaje = 2;
        if(this.letra=='h')this.puntaje = 4;
        if(this.letra=='j')this.puntaje = 8;
        if(this.letra=='k')this.puntaje = 8;
        if(this.letra=='l')this.puntaje = 1;
        if(this.letra=='m')this.puntaje = 3;
        if(this.letra=='n')this.puntaje = 1;
        if(this.letra=='o')this.puntaje = 1;
        if(this.letra=='p')this.puntaje = 3;
        if(this.letra=='q')this.puntaje = 5;
        if(this.letra=='r')this.puntaje = 1;
        if(this.letra=='s')this.puntaje = 1;
        if(this.letra=='t')this.puntaje = 1;
        if(this.letra=='u')this.puntaje = 1;
        if(this.letra=='v')this.puntaje = 4;
        if(this.letra=='x')this.puntaje = 8;
        if(this.letra=='y')this.puntaje = 4;
        if(this.letra=='z')this.puntaje = 10;
        if(this.letra=='ñ')this.puntaje = 8;
        if(this.letra=='*')this.puntaje = 0;
    }
}