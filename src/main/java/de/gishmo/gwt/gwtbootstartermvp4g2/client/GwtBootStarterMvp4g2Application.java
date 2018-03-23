package de.gishmo.gwt.gwtbootstartermvp4g2.client;

import com.github.mvp4g.mvp4g2.core.application.IsApplication;
import com.github.mvp4g.mvp4g2.core.application.annotation.Application;

@Application(eventBus = GwtBootStarterMvp4g2EventBus.class)
interface GwtBootStarterMvp4g2Application
  extends IsApplication {
}
