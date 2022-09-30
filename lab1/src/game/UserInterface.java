package game;

import javax.swing.*;

public class UserInterface {
    public static void printMessage(String msg) {
        JOptionPane.showMessageDialog(null, msg);
    }

    public static int askForInt(String msg) {
        try {
            var i = Integer.parseInt(
                    JOptionPane.showInputDialog(null, msg));
            return i;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return askForInt(msg);
        }
    }
}
