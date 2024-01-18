module org.bytelib.bytelib {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires net.synedra.validatorfx;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;
    requires org.apache.commons.lang3;
    requires org.apache.commons.text;
    requires java.sql;
    requires jbcrypt;

    opens bytelib to javafx.fxml;
    opens bytelib.items to javafx.base;
    opens bytelib.items.periodical to javafx.base;
    opens bytelib.items.books to javafx.base;
    exports bytelib;
    exports bytelib.scenes;
    opens bytelib.scenes to javafx.fxml;
}