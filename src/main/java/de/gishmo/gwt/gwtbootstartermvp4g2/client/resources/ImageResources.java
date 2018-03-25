package de.gishmo.gwt.gwtbootstartermvp4g2.client.resources;

import com.google.gwt.core.shared.GWT;
import com.google.gwt.resources.client.ClientBundle;
import com.google.gwt.resources.client.ImageResource;

public interface ImageResources
  extends ClientBundle {

  ImageResources IMAGES = GWT.create(ImageResources.class);

  @Source("iconCopy.png")
  ImageResource iconCopy();

  @Source("iconDelete.png")
  ImageResource iconDelete();

  @Source("iconEdit2.png")
  ImageResource iconEdit2();

  @Source("iconMinus.png")
  ImageResource iconMinus();

  @Source("iconNew.png")
  ImageResource iconNew();

}
