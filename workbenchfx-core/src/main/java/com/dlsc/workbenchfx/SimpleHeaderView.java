package com.dlsc.workbenchfx;

import com.dlsc.workbenchfx.model.WorkbenchModule;
import com.dlsc.workbenchfx.view.controls.ToolbarItem;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import javafx.scene.Node;
import javafx.scene.layout.Region;

public class SimpleHeaderView<T extends Region> extends Workbench {

    private static class ModuleImpl extends WorkbenchModule {

        private final Node content;

        protected ModuleImpl(Node content) {
            super("-", (FontAwesomeIcon) null);
            this.content = content;
        }

        @Override
        public Node activate() {
            return this.content;
        }
    }

    private T content;

    public SimpleHeaderView(String title, Node graphic) {
        this(title, graphic, null);
    }

    public SimpleHeaderView(String title, Node graphic, T content) {
        this.content = content;
        this.getToolbarControlsLeft().add(new ToolbarItem(title, graphic));
        this.getModules().add(new ModuleImpl(content));
    }

    public T getContent() {
        return content;
    }

    public void setContent(T content) {
        if (content != this.content) {
            this.content = content;
            this.getModules().clear();
            this.getModules().add(new ModuleImpl(content));
        }
    }
}