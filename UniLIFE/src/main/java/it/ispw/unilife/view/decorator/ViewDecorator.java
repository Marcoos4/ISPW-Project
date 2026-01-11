package it.ispw.unilife.view.decorator;

import javafx.scene.layout.Pane;

public abstract class ViewDecorator extends ViewComponent {

    protected ViewComponent view; // Riferimento al componente avvolto

    public ViewDecorator(ViewComponent view) {
        this.view = view;
    }

    @Override
    public Pane draw() {
        return view.draw(); // Delegazione standard
    }
}