/* Copyright 2015 ESRI
* 
* All rights reserved under the copyright laws of the United States
* and applicable international laws, treaties, and conventions.
* 
* You may freely redistribute and use this sample code, with or
* without modification, provided you include the original copyright
* notice and use restrictions.
* 
* See the use restrictions at <your ArcGIS install location>/DeveloperKit10.4/userestrictions.txt.
* 
*/
package popy.renderer;

import java.io.IOException;
import java.util.ArrayList;

import javax.swing.JFrame;
import com.esri.arcgis.cartoUI.BarChartPropertyPage;
import com.esri.arcgis.carto.IFeatureRenderer;
import com.esri.arcgis.interop.AutomationException;
import com.esri.arcgis.cartoUI.BaseCustomRendererPropertyPage;
import com.esri.arcgis.interop.extn.ArcGISCategories;
import com.esri.arcgis.interop.extn.ArcGISExtension;

@ArcGISExtension(categories={ArcGISCategories.ESRIRendererPropertyPages})
public class PopyRendererPropertyPage extends BaseCustomRendererPropertyPage{

  PopyRendererPropertyPageUI popyPropertyPageUI;
  public ArrayList<String> ArrayList = new ArrayList<String>();
  @Override
  // You can apply the properties from your UI to the renderer in this method
  public void apply(IFeatureRenderer fetRenderer) {
	  PopyRenderer popyRenderer = (PopyRenderer)fetRenderer;
	  /*	this.ArrayList.add("F0_10");
		this.ArrayList.add("F11_20");
		this.ArrayList.add("F21_30");
		this.ArrayList.add("F31_40");
		this.ArrayList.add("F41_50");
		this.ArrayList.add("F51_60");
		this.ArrayList.add("F61_70");
		this.ArrayList.add("F71_80");
		this.ArrayList.add("F81_90");
		this.ArrayList.add("F90_100");
	 */
      popyRenderer.setFields(this.ArrayList);
      
  }

  @Override
  // This returns the progID of the renderer for which you are associating the property page
  public Class getCustomRendererDef() {
    return popy.renderer.PopyRenderer.class;
  }

  // This is the custom description of your property page
  @Override
  public String getDescription() throws IOException, AutomationException {
    return "Population Pyramid property page";
  }

  // This is the Name of your custom renderer
  @Override
  public String getName() throws IOException, AutomationException {
    return "Population Pyramid";
  }

  // Preview bitmap for the renderer that appears on the page.
  @Override
  public int getPreviewImage() throws IOException, AutomationException {
    return 0;
  }

  // This is the Type of your custom renderer
  @Override
  public String getType() throws IOException, AutomationException {
    return "Charts";
  }

  // Every Property Page has a UI associated with it,so create an UI and return the object of that UI
  @Override
  public JFrame initGUI(IFeatureRenderer arg0) {
    try {
      this.popyPropertyPageUI = new PopyRendererPropertyPageUI();
      } catch (Exception e) {
      e.printStackTrace();
      }
      return this.popyPropertyPageUI;

  }

  // This is the page priority.The higher the priority,the sooner the page appears in the containing property sheet.
  @Override
  public int getPriority() throws IOException, AutomationException {
    return 450;
  }

}