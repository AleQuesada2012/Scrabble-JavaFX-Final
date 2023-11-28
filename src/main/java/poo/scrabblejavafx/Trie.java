package poo.scrabblejavafx;

import java.util.*;

/**
 *  Clase "vértice" para el árbol Trie. Cumple el propósito de abstraer las funciones del alfabeto, la existencia
 *  de los caracteres, y de seguir formando la estructura del árbol.
 *  Se utiliza dentro de la clase Trie para llevar 'cuenta' de los nodos que existen en el árbol.
 */
class vertex {
    public char alphabet;
    public Boolean exist;
    public ArrayList<vertex> child;

    public vertex(char a) {
        alphabet = a;
        exist = false;
        child = new ArrayList<>(Collections.nCopies(27, null));
    }
};

/**
 * clase principal del árbol Trie. Esta es la que se instancia a la hora de querer crear un
 */
class Trie {
    private vertex root;

    public Trie() { root = new vertex('!'); }


    /**
     * Metodo el cual ingresa un string en el arbol
     * @param word string que se desea introducir
     */
    public void insert(String word) {
        vertex cur = root;
        for (int i = 0; i < word.length(); ++i) {
            int alphaNum = word.charAt(i)-'a';
            if(word.charAt(i)=='ñ') alphaNum = 26; // si la letra es una ñ, se "obliga" al trie a colocarla al final.
            if (cur.child.get(alphaNum) == null)
                cur.child.set(alphaNum, new vertex(word.charAt(i)));
            cur = cur.child.get(alphaNum);
        }
        cur.exist = true;
    }

    /**
     * Metodo el cual busca en el arbol una palabra
     * @param word string el cual se desea buscar
     * @return True si se encuentra el string False de lo contrario
     */
    public Boolean search(String word) {
        vertex cur = root;
        for (int i = 0; i < word.length(); ++i) {
            int alphaNum = word.charAt(i)-'a';
            if(word.charAt(i)=='ñ')alphaNum = 26;
            if (cur.child.get(alphaNum) == null)
                return false;
            cur = cur.child.get(alphaNum);
        }
        return cur.exist;
    }




};
