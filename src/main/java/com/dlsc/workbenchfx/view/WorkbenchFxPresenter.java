package com.dlsc.workbenchfx.view;

import com.dlsc.workbenchfx.WorkbenchFx;
import com.dlsc.workbenchfx.model.WorkbenchFxModel;
import com.dlsc.workbenchfx.model.module.Module;
import java.util.Objects;
import javafx.scene.control.Button;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Contains presenter logic of the {@link WorkbenchFxView}.
 *
 * @author François Martin
 * @author Marco Sanfratello
 */
public class WorkbenchFxPresenter implements Presenter {
  private static final Logger LOGGER =
      LogManager.getLogger(WorkbenchFxPresenter.class.getName());

  private WorkbenchFxModel model;
  private WorkbenchFxView view;

  /**
   * Constructs a new presenter for the {@link WorkbenchFxView}.
   *
   * @param model the model of WorkbenchFX
   * @param view  corresponding view to this presenter
   */
  public WorkbenchFxPresenter(WorkbenchFxModel model, WorkbenchFxView view) {
    this.model = model;
    this.view = view;
    init();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void initializeViewParts() {
    view.centerView.setContentNode(view.homeView);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void setupEventHandlers() {
    model.activeModuleViewProperty().addListener((observable, oldValue, newValue) ->
      view.centerView.setContentNode(Objects.isNull(newValue) ? view.homeView : newValue)
    );


    view.toolBarView.homeBtn.setOnAction(event -> view.centerView.setContentNode(view.homeView));


    Button b = (Button) model.modules[0].getTile();
    b.setOnAction(event -> {
      view.centerView.setContentNode(modules[0].init(model));
      view.toolBarView.trigger(modules[0].getTab());
    });
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
