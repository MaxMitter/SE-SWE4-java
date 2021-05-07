package swe4.gui.controller;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;

import java.io.*;

public class GameListEntryController {
    public static Scene scene = null;
    public static Parent rootElement = null;

    public static void SetFxml(Parent node) {
        rootElement = node;
    }

    public static Parent GetElement() {
        Parent newElem = rootElement;
        return newElem;
    }

    private void reloadElement() throws IOException {
        rootElement = FXMLLoader.load(getClass().getResource("../fxml/gameListEntry.fxml"));
    }
}
