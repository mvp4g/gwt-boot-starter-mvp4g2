/*
 * Copyright (c) 2018 - Frank Hossfeld
 *
 *  Licensed under the Apache License, Version 2.0 (the "License"); you may not
 *  use this file except in compliance with the License. You may obtain a copy of
 *  the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 *  WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 *  License for the specific language governing permissions and limitations under
 *  the License.
 *
 */

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
