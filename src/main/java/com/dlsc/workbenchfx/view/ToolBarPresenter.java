package com.dlsc.workbenchfx.view;

import com.dlsc.workbenchfx.model.WorkbenchFxModel;
import com.dlsc.workbenchfx.model.module.Module;
import com.dlsc.workbenchfx.view.module.TabControl;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import javafx.collections.ListChangeListener;

public class ToolBarPresenter implements Presenter {
  private final WorkbenchFxModel model;
  private final ToolBarView view;
  private List<Module> indexOfModuleLst;

  public ToolBarPresenter(WorkbenchFxModel model, ToolBarView view) {
    this.model = model;
    this.view = view;
    indexOfModuleLst = new ArrayList<>();
    init();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void initializeViewParts() {
    model.getOpenModules().forEach(module -> view.getChildren().add(module.getTab()));
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void setupEventHandlers() {
    // When the home button is clicked, the view changes
    view.homeBtn.setOnAction(event -> model.openHomeScreen());

    model.getOpenModules().addListener((ListChangeListener<? super Module>) c -> {
      while (c.next()) {
        if (c.wasPermutated()) {
          for (int i = c.getFrom(); i < c.getTo(); ++i) {
            System.out.println("Permutate");
          }
        } else if (c.wasUpdated()) {
          System.out.println("Update Item");
        } else {
          for (Module module : c.getRemoved()) {
            System.out.println("Remove");
            // +1 because of home
            view.getChildren().remove(indexOfModuleLst.indexOf(module) + 1);
            indexOfModuleLst.remove(module);
          }
          for (Module module : c.getAddedSubList()) {
            System.out.println("Add");
            if (!Objects.isNull(module)) {
              TabControl tabControl = (TabControl) module.getTab();
              view.getChildren().add(tabControl);
              tabControl.requestFocus();
              indexOfModuleLst.add(module);
            }
          }
        }
      }
    });

    // Control -> closebutton -> module.

  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void setupValueChangedListeners() {

  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void setupBindings() {

  }

}
