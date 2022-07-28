package com.da;

import com.da.frame.core.AnnotationAppContext;
import com.da.frame.core.AppContext;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

/**
 * @author Da
 * @description xxx
 * @date 2022-07-28 12:01
 */
public class Show extends Application {

    @Override
    public void start(Stage primaryStage) {
        AppContext context = new AnnotationAppContext(App.class);
        Config config = context.getBean("config", Config.class);
        primaryStage.setTitle(config.getTitle());
        primaryStage.setWidth(config.getWidth());
        primaryStage.setHeight(config.getHeight());
        HBox root = new HBox();
        Button btn = new Button("点我看看");
        root.getChildren().add(btn);
        EventHandler<ActionEvent> fun = e -> System.out.println("hello");
        btn.setOnAction(fun);
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }
}
