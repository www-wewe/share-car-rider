package cz.muni.fi.pv168.seminar01.delta;

import cz.muni.fi.pv168.seminar01.delta.error.DatabaseError;
import cz.muni.fi.pv168.seminar01.delta.gui.mainwindow.MainWindow;
import cz.muni.fi.pv168.seminar01.delta.wiring.DependencyProvider;

import javax.swing.*;

/**
 * Entry point of the application.
 *
 * @author Jiri Zak
 */
public class Main {
    public static void main(String[] args) {
        try {
            DependencyProvider dependencyProvider = new DependencyProvider();
            JFrame frame = new MainWindow(dependencyProvider);
            frame.setSize(1450, 700);
            frame.setLocationRelativeTo(null);
            frame.setResizable(false);
            frame.setVisible(true);
        } catch (DatabaseError e) {
            System.err.println(e.getUserMessage());
        }
    }
}