package org.example;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyHandler implements KeyListener {
    public boolean upPressed, downPressed, leftPressed, rightPressed, oPressed;
    public boolean spacePressed; // Dodajemy to, żeby śledzić stan spacji

    @Override
    public void keyPressed(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_W -> upPressed = true;
            case KeyEvent.VK_S -> downPressed = true;
            case KeyEvent.VK_A -> leftPressed = true;
            case KeyEvent.VK_D -> rightPressed = true;
            case KeyEvent.VK_O -> oPressed = true;
            case KeyEvent.VK_SPACE -> spacePressed = true; // Ustawienie na true, kiedy spacja jest wciśnięta
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_W -> upPressed = false;
            case KeyEvent.VK_S -> downPressed = false;
            case KeyEvent.VK_A -> leftPressed = false;
            case KeyEvent.VK_D -> rightPressed = false;
            case KeyEvent.VK_O -> oPressed = false;
            case KeyEvent.VK_SPACE -> spacePressed = false; // Ustawienie na false, kiedy spacja jest zwolniona
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {
        // Nie musimy obsługiwać tej metody
    }
}
