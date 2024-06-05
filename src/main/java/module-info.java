module org.example.javafx {
    requires javafx.controls;
    requires javafx.fxml;


    opens senai.projetointegrador.javafx to javafx.fxml;
    exports senai.projetointegrador.javafx;
}