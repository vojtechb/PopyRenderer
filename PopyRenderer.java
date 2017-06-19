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

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.List;
import java.awt.Shape;
import java.awt.geom.AffineTransform;
import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.util.*;
import java.lang.Object.*;

import com.esri.arcgis.carto.ChartRenderer;
import com.esri.arcgis.carto.IChartRenderer;
import com.esri.arcgis.carto.IFeatureIDSet;
import com.esri.arcgis.carto.IFeatureRenderer;
import com.esri.arcgis.carto.IRendererFields;
import com.esri.arcgis.carto.IRotationRenderer;
import com.esri.arcgis.carto.SimpleRenderer;
import com.esri.arcgis.carto.esriViewDrawPhase;
import com.esri.arcgis.display.BarChartSymbol;
import com.esri.arcgis.display.IChartSymbol;
import com.esri.arcgis.display.IColor;
import com.esri.arcgis.display.IDisplay;
import com.esri.arcgis.display.IMarkerSymbol;
import com.esri.arcgis.display.IRgbColor;
import com.esri.arcgis.display.ISimpleFillSymbol;
import com.esri.arcgis.display.ISimpleLineSymbol;
import com.esri.arcgis.display.ISimpleMarkerSymbol;
import com.esri.arcgis.display.ISymbol;
import com.esri.arcgis.display.ISymbolArray;
import com.esri.arcgis.display.ISymbolRotation;
import com.esri.arcgis.display.ITextSymbol;
import com.esri.arcgis.display.PictureMarkerSymbol;
import com.esri.arcgis.display.RgbColor;
import com.esri.arcgis.display.SimpleFillSymbol;
import com.esri.arcgis.display.SimpleLineSymbol;
import com.esri.arcgis.display.SimpleMarkerSymbol;
import com.esri.arcgis.display.esriSimpleFillStyle;
import com.esri.arcgis.display.esriSimpleLineStyle;
import com.esri.arcgis.display.esriSimpleMarkerStyle;
import com.esri.arcgis.geodatabase.DataStatistics;
import com.esri.arcgis.geodatabase.ICursor;
import com.esri.arcgis.geodatabase.IFeature;
import com.esri.arcgis.geodatabase.IFeatureClass;
import com.esri.arcgis.geodatabase.IFeatureCursor;
import com.esri.arcgis.geodatabase.IFeatureDraw;
import com.esri.arcgis.geodatabase.IQueryFilter;
import com.esri.arcgis.geodatabase.IRowBuffer;
import com.esri.arcgis.geodatabase.ITable;
import com.esri.arcgis.geodatabase.QueryFilter;
import com.esri.arcgis.geodatabase.esriDrawStyle;
import com.esri.arcgis.geometry.IGeometry;
import com.esri.arcgis.geometry.IPoint;
import com.esri.arcgis.geometry.ITransformation;
import com.esri.arcgis.geometry.esriGeometryType;
import com.esri.arcgis.geoprocessing.tools.coveragetools.Thiessen;
import com.esri.arcgis.system.IStatisticsResults;
import com.esri.arcgis.system.ITrackCancel;
import com.esri.arcgis.system.esriDrawPhase;
import com.esri.arcgis.interop.AutomationException;
import com.esri.arcgis.interop.extn.ArcGISExtension;

@ArcGISExtension
public class PopyRenderer implements IFeatureRenderer, Externalizable, IChartRenderer, IRendererFields, IRotationRenderer, IMarkerSymbol, ISymbolRotation{
	
	ChartRenderer populationRenderer;
	ChartRenderer populationRenderer2;
	IRendererFields rendererFields = populationRenderer;
	IRendererFields rendererFields2 = populationRenderer2;
	BarChartSymbol barChartSymbol;
	BarChartSymbol barChartSymbol2;
	ISymbol baseSym;
	ISymbol baseSym2;
	ArrayList<String> fields = new ArrayList<String>();
	ArrayList<String> fields2 = new ArrayList<String>();
	ISimpleLineSymbol simpleLineSymbol;
	
  public PopyRenderer() {
	  try {
		  this.fields2.add("F0_10");
		  this.fields2.add("F11_20");
		  this.fields2.add("F21_30");
		  this.fields2.add("F31_40");
		  this.fields2.add("F41_50");
		  this.fields2.add("F51_60");
		  this.fields2.add("F61_70");
		  this.fields2.add("F71_80");
		  this.fields2.add("F81_90");
		  this.fields2.add("M91_100");
		  Collections.reverse(fields2);
		  
		  this.fields.add("M0_10");
		  this.fields.add("M11_20");
		  this.fields.add("M21_30");
		  this.fields.add("M31_40");
		  this.fields.add("M41_50");
		  this.fields.add("M51_60");
		  this.fields.add("M61_70");
		  this.fields.add("M71_80");
		  this.fields.add("M81_90");
		  this.fields.add("M91_100");
		  Collections.reverse(fields);
		  initialize();
		  initialize2();
	
	      
	  } catch (java.lang.Exception e) {
		  }
}
  
  
  public void setFields(ArrayList<String> ArrayList){
		
		this.fields = ArrayList;
			}

  public void initialize() throws Exception {
	  this.populationRenderer = new ChartRenderer();
      IRendererFields rendererFields = this.populationRenderer;
      
      int i = 0;
      int fieldsSize = fields.size();
      
      while (i < fieldsSize){
    	  String fieldName = fields.get(i);
    	  rendererFields.addField(fieldName, null);
	      rendererFields.setFieldAlias(i, rendererFields.getField(i));
	      i++ ;
      }
      
 
      RgbColor color = new RgbColor();
      color.setRGB(0x000000);
      this.simpleLineSymbol = new SimpleLineSymbol();
      this.simpleLineSymbol.setWidth(1.0);
      this.simpleLineSymbol.setColor(color);
      this.simpleLineSymbol.setStyle(esriSimpleLineStyle.esriSLSSolid);		     
      
      double maxValue = 70;	      
 //     ITransformation trans = null;
      BarChartSymbol barChartSymbol = new BarChartSymbol();
      IChartSymbol chartSymbol = barChartSymbol;
      barChartSymbol.setWidth(5);
      IMarkerSymbol markerSymbol = barChartSymbol;
      chartSymbol.setMaxValue(maxValue);
      markerSymbol.setSize(50);
      
      barChartSymbol.setShowAxes(false);
      barChartSymbol.setVerticalBars(false);
      barChartSymbol.setAxes(simpleLineSymbol);
      barChartSymbol.setShowAxes(true);
  //    barChartSymbol.setupDC(41, trans);
      barChartSymbol.setSpacing(0);
      
      ISymbolArray symbolArray = barChartSymbol;
      int f = 0;
	     while (f < fieldsSize){
	    	  SimpleFillSymbol fillSymbol1 = new SimpleFillSymbol();
		      fillSymbol1.setColor(getRGBColor(100, 200, 100));
		      symbolArray.addSymbol(fillSymbol1);
		      f++;
	     }
   
      this.populationRenderer.setChartSymbolByRef(barChartSymbol);
      this.populationRenderer.setLabel("Population");
	  
	  
  }
  
  public void initialize2() throws Exception {
	  this.populationRenderer2 = new ChartRenderer();
      IRendererFields rendererFields2 = this.populationRenderer2;
      
      int p = 0;
      int fieldsSize = fields2.size();
      
      while (p < fieldsSize){
    	  String fieldName = fields2.get(p);
    	  rendererFields2.addField(fieldName, null);
	      rendererFields2.setFieldAlias(p, rendererFields2.getField(p));
	      p++ ;
      }
      
 
      RgbColor color = new RgbColor();
      color.setRGB(0x000000);
      this.simpleLineSymbol = new SimpleLineSymbol();
      this.simpleLineSymbol.setWidth(1.0);
      this.simpleLineSymbol.setColor(color);
      this.simpleLineSymbol.setStyle(esriSimpleLineStyle.esriSLSSolid);		     
      
      double maxValue = 70;	      
  //    ITransformation trans = null;
      BarChartSymbol barChartSymbol2 = new BarChartSymbol();
      IChartSymbol chartSymbol = barChartSymbol2;
      barChartSymbol2.setWidth(5);
      IMarkerSymbol markerSymbol = barChartSymbol2;
      chartSymbol.setMaxValue(maxValue);
      markerSymbol.setSize(50);
      
      barChartSymbol2.setShowAxes(false);
      barChartSymbol2.setVerticalBars(false);
      barChartSymbol2.setAxes(simpleLineSymbol);
      barChartSymbol2.setShowAxes(true);
      barChartSymbol2.setSpacing(0);
      ISymbolArray symbolArray2 = barChartSymbol2;
      int f = 0;
	     while (f < fieldsSize){
	    	  SimpleFillSymbol fillSymbol1 = new SimpleFillSymbol();
		      fillSymbol1.setColor(getRGBColor(200, 100, 100));
		      symbolArray2.addSymbol(fillSymbol1);
		      f++;
	     }
   
      this.populationRenderer2.setChartSymbolByRef(barChartSymbol2);
      this.populationRenderer2.setLabel("Population2");
	  
	  
  }
  
  
   /* @see IFeatureRenderer#canRender
   * @param featureClass IFeatureClass
   * @param display IDisplay
   * @throws IOException
   * @throws AutomationException
   * @return boolean
   */
  public boolean canRender(IFeatureClass fc, IDisplay display) throws IOException, AutomationException {
    if (fc.getShapeType() == esriGeometryType.esriGeometryPoint || fc.getShapeType() == esriGeometryType.esriGeometryPolygon)
      return true;
    return false;
  }

  /**
   * This method selects the appropriated symbol depend on feature type.
   * @see IFeatureRenderer#prepareFilter
   * @param featureClass IFeatureClass
   * @param queryFilter IQueryFilter
   * @throws IOException
   * @throws AutomationException
   */
  public void prepareFilter(IFeatureClass featureClass, IQueryFilter queryFilter) throws IOException, AutomationException {
	
	  IFeatureRenderer rend = (IFeatureRenderer)this.populationRenderer;
	  rend.prepareFilter(featureClass, queryFilter);
	  IFeatureRenderer rend2 = (IFeatureRenderer)this.populationRenderer2;
	  rend2.prepareFilter(featureClass, queryFilter);
  }
  
  

  
  public boolean isRenderPhase(int drawPhase) {
	  //Renders only in the geography phase.
	    if (drawPhase == esriDrawPhase.esriDPGeography || drawPhase == esriDrawPhase.esriDPAnnotation)
	    return true;
	    else
	    	return false;
	  }

  /**
   * @see IFeatureRenderer#setExclusionSetByRef
   * @param featureIDSet IFeatureIDSet
   */
  @Override
  public void setExclusionSetByRef(IFeatureIDSet featureIDSet) {
    // not implemented
  }

@Override
public void readExternal(ObjectInput arg0) throws IOException, ClassNotFoundException {
	// TODO Auto-generated method stub
	
}

@Override
public void writeExternal(ObjectOutput arg0) throws IOException {
	// TODO Auto-generated method stub
	
}

@Override
public void createLegend() throws IOException, AutomationException {
	// TODO Auto-generated method stub
	
}

@Override
public ISymbol getBaseSymbol() throws IOException, AutomationException {
	 SimpleFillSymbol fillSymbol4 = new SimpleFillSymbol();
     fillSymbol4.setColor(getRGBColor(239, 228, 190));
	return fillSymbol4;
}

@Override
public IChartSymbol getChartSymbol() throws IOException, AutomationException {
 	return null;	
	}

@Override
public String getColorScheme() throws IOException, AutomationException {
	return null;
}



@Override
public String getLabel() throws IOException, AutomationException {
	return null;
}

@Override
public boolean isUseOverposter() throws IOException, AutomationException {
	return false;
}

@Override
public void setBaseSymbolByRef(ISymbol symbol) throws IOException, AutomationException {
}

@Override
public void setChartSymbolByRef(IChartSymbol symbol) throws IOException, AutomationException {
}

@Override
public void setColorScheme(String name) throws IOException, AutomationException {
	// TODO Auto-generated method stub	
}

@Override
public void setFieldTotal(int index, double total) throws IOException, AutomationException {
	// TODO Auto-generated method stub
	}

@Override
public void setLabel(String name) throws IOException, AutomationException {
}

@Override
public void setUseOverposter(boolean flag) throws IOException, AutomationException {
	// TODO Auto-generated method stub
}


@Override
public void draw(IFeatureCursor featureCursor, int drawPhase, IDisplay display, ITrackCancel trackCancel)
		throws IOException, AutomationException {
	if (display == null)
		return;
	if (drawPhase != esriDrawPhase.esriDPGeography)
	      return;
   
    	IFeature feat = featureCursor.nextFeature();
	    boolean bContinue = true;
	    ChartRenderer charRend = this.populationRenderer;
	    ChartRenderer charRend2 = this.populationRenderer2;
	    while (feat != null && bContinue == true) {
	    	ISymbol mainSym = charRend.getSymbolByFeature(feat);
	     	ISymbol mainSym2 = charRend2.getSymbolByFeature(feat);
	    	
	        display.setSymbol(mainSym2);	           
	        IFeatureDraw featDraw2 = (IFeatureDraw)feat;
	        featDraw2.draw(drawPhase, display, mainSym2, true, null, esriDrawStyle.esriDSNormal);
	        display.setSymbol(mainSym);	           
	        IFeatureDraw featDraw = (IFeatureDraw)feat;
	        featDraw.draw(drawPhase, display, mainSym, true, null, esriDrawStyle.esriDSNormal);
	        
	        feat = featureCursor.nextFeature();
	        
	        if (trackCancel != null)
	          bContinue = trackCancel.esri_continue();
	      }	    
}



@Override
public ISymbol getSymbolByFeature(IFeature feature) throws IOException, AutomationException {
return null;
}


public void setRendererByRef(IFeatureRenderer featureRenderer) throws IOException, AutomationException {
}


@Override
public double getFieldTotal(int index) throws IOException, AutomationException {
	// TODO Auto-generated method stub
	return 0;
}


private IRgbColor getRGBColor(int red, int green, int blue) {
    IRgbColor rgbColor = null;
    try {
      //Create rgb color and grab hold of the IRGBColor interface
      rgbColor = new RgbColor();
      rgbColor.setRed(red);
      rgbColor.setGreen(green);
      rgbColor.setBlue(blue);
      rgbColor.setUseWindowsDithering(true);
      rgbColor = (RgbColor)rgbColor;
    } catch (Exception ex) {
      ex.printStackTrace(); // never happened
    }
    return rgbColor;
}



@Override
public void addField(String arg0, String arg1) throws IOException, AutomationException {
	// TODO Auto-generated method stub
	
}



@Override
public void clearFields() throws IOException, AutomationException {
	// TODO Auto-generated method stub
	
}



@Override
public void deleteField(String name) throws IOException, AutomationException {
	// TODO Auto-generated method stub
	
}



@Override
public String getField(int index) throws IOException, AutomationException {
	// TODO Auto-generated method stub
	return null;
}



@Override
public String getFieldAlias(int index) throws IOException, AutomationException {
	// TODO Auto-generated method stub
	return null;
}



@Override
public int getFieldCount() throws IOException, AutomationException {
	// TODO Auto-generated method stub
	return 0;
}



@Override
public void setField(int index, String name) throws IOException, AutomationException {
	// TODO Auto-generated method stub
	
}



@Override
public void setFieldAlias(int index, String name) throws IOException, AutomationException {
	// TODO Auto-generated method stub
	
}




@Override
public String getRotationField() throws IOException, AutomationException {
	// TODO Auto-generated method stub
	return null;
}




@Override
public int getRotationType() throws IOException, AutomationException {
	// TODO Auto-generated method stub
	return 0;
}




@Override
public void setRotationField(String arg0) throws IOException, AutomationException {
	// TODO Auto-generated method stub
	
}




@Override
public void setRotationType(int type) throws IOException, AutomationException {
	// TODO Auto-generated method stub
	
}




@Override
public double getAngle() throws IOException, AutomationException {
	// TODO Auto-generated method stub
	return 0;
}




@Override
public IColor getColor() throws IOException, AutomationException {
	// TODO Auto-generated method stub
	return null;
}




@Override
public double getSize() throws IOException, AutomationException {
	// TODO Auto-generated method stub
	return 0;
}




@Override
public double getXOffset() throws IOException, AutomationException {
	// TODO Auto-generated method stub
	return 0;
}




@Override
public double getYOffset() throws IOException, AutomationException {
	// TODO Auto-generated method stub
	return 0;
}




@Override
public void setAngle(double angle) throws IOException, AutomationException {
	// TODO Auto-generated method stub
	
}




@Override
public void setColor(IColor color) throws IOException, AutomationException {
	// TODO Auto-generated method stub
	
}




@Override
public void setSize(double size) throws IOException, AutomationException {
	// TODO Auto-generated method stub
	
}




@Override
public void setXOffset(double xOffset) throws IOException, AutomationException {
	// TODO Auto-generated method stub
	
}




@Override
public void setYOffset(double yOffset) throws IOException, AutomationException {
	// TODO Auto-generated method stub
	
}




@Override
public boolean isRotateWithTransform() throws IOException, AutomationException {
	// TODO Auto-generated method stub
	return false;
}




@Override
public void setRotateWithTransform(boolean flag) throws IOException, AutomationException {
	// TODO Auto-generated method stub
	
}

}



