package com.dlsc.workbenchfx.custom;

import com.dlsc.workbenchfx.WorkbenchFx;
import com.dlsc.workbenchfx.custom.calendar.CalendarModule;
import com.dlsc.workbenchfx.custom.notes.NotesModule;
import com.dlsc.workbenchfx.custom.preferences.PreferencesModule;
import com.dlsc.workbenchfx.module.Module;
import com.dlsc.workbenchfx.view.module.TabControl;
import com.dlsc.workbenchfx.view.module.TileControl;
import java.util.function.BiFunction;
import javafx.scene.Node;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;

public class DemoPane extends StackPane {

  public WorkbenchFx workbenchFx;

  BiFunction<WorkbenchFx, Module, Node> tabFactory = (workbench, module) -> {
    TabControl tabControl = new TabControl(module);
    tabControl.setOnClose(e -> workbench.closeModule(module));
    tabControl.setOnActive(e -> workbench.openModule(module));
    return tabControl;
  };

  BiFunction<WorkbenchFx, Module, Node> tileFactory = (workbench, module) -> {
    TileControl tileControl = new TileControl(module);
    tileControl.setOnActive(e -> workbench.openModule(module));
    return tileControl;
  };

  BiFunction<WorkbenchFx, Integer, Node> pageFactory = (workbench, pageIndex) -> {
    final int COLUMNS_PER_ROW = 3;

    GridPane gridPane = new GridPane();
    gridPane.getStyleClass().add("tilePage");

    int position = pageIndex * workbench.MODULES_PER_PAGE;
    int count = 0;
    int column = 0;
    int row = 0;

    while (count < workbench.MODULES_PER_PAGE && position < workbench.getModules().size()) {
      Module module = workbench.getModules().get(position);
      Node tile = workbench.getTile(module);
      gridPane.add(tile, column, row);

      position++;
      count++;
      column++;

      if (column == COLUMNS_PER_ROW) {
        column = 0;
        row++;
      }
    }
    return gridPane;
  };

  public DemoPane() {

    workbenchFx = WorkbenchFx.builder(
        new CalendarModule(),
        new NotesModule(),
        new PreferencesModule()
    ).modulesPerPage(20)
     .tabFactory(tabFactory)
     .tileFactory(tileFactory)
     .pageFactory(pageFactory)
     .build();
    getChildren().add(workbenchFx);
  }

}
