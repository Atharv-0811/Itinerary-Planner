package org.vaadin.example.views;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;
import org.vaadin.example.layout.MainLayout;

import java.awt.*;


@Route(value = "my-trips", layout = MainLayout.class)
public class MyTripsView extends VerticalLayout {

    public MyTripsView(){
        H1 title = new H1("trips page");

        TextField textInput = new TextField("enter name");

        Button enterBtn = new Button("enter");
        enterBtn.addClickListener(e -> {
           textInput.getValue().trim();

            System.out.println("text entered: " + textInput);
        });


        add(title, textInput, enterBtn);
    }
}
