module startup {
   requires java.logging;

   requires javafx.controls;
   requires javafx.graphics;
   requires javafx.fxml;

   opens swe4.gui;
   opens swe4.gui.controller;
   opens swe4.gui.data.Entities;
}
