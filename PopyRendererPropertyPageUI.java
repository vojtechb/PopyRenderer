/* Copyright 2017 Vojtech Bekarek
* 
*/

package popy.renderer;

import java.awt.Rectangle;
import java.util.ArrayList;

import javax.swing.DefaultListModel;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.UIManager;
import javax.swing.border.TitledBorder;

import com.esri.arcgis.carto.IRendererFields;
import com.esri.arcgis.interop.AutomationException;

import javax.swing.JList;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.SortedSet;
import java.util.TreeSet;
import javax.swing.AbstractListModel;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListCellRenderer;
import javax.swing.ListModel;

public class PopyRendererPropertyPageUI extends JFrame implements IRendererFields, ActionListener{
  private JPanel jContentPane = null;
  private JTextField jTextField = null;
  private JPanel jPanelL = null;
  private JPanel jPanelR = null;
  public JList<String> sourceList;
  public DefaultListModel<String> sourceListModel;
  public JList<String> destList;
  public DefaultListModel<String> destListModel;
  public JList<String> sourceList2;
  public DefaultListModel<String> sourceListModel2;
  public JList<String> destList2;
  public DefaultListModel<String> destListModel2;
  private JButton addButton;
  private JButton removeButton;
  private JButton addButton2;
  private JButton removeButton2;
  private String count;

  public PopyRendererPropertyPageUI() throws Exception {
	  this.count = ("fieldcount" + getField(1));
	  initialize();
  
  
  }
 
  private void initialize() throws Exception {
    this.setSize(426, 500);
    this.setResizable(true);
    this.setContentPane(getJContentPane());
    this.setTitle("Population pyramid Property Page");	
  }
  /**
   * This method creates a ContentPane for the panel
   * @return javax.swing.JPanel
   */
  private JPanel getJContentPane() throws Exception {
    if (jContentPane == null) {
      jContentPane = new JPanel();
      jContentPane.setLayout(null);
      jContentPane.add(getJPanelL());
      jContentPane.add(getJPanelR());
      }
    return jContentPane;
  }
  
  private DefaultListModel<String> getSourceList(){
	  sourceListModel = new DefaultListModel<String>();
	  
		
	
		sourceListModel.addElement(count);
		sourceListModel.addElement("Field2");
		sourceListModel.addElement("Field3");
		sourceListModel.addElement("Field5");
		sourceListModel.addElement("Field6");
		sourceListModel.addElement("Field7");
		sourceListModel.addElement("Field8");
		sourceListModel.addElement("Field9");
		sourceListModel.addElement("Field10");
		sourceListModel.addElement("Field11");
		sourceListModel.addElement("Field12");
		sourceListModel.addElement("Field13");
		sourceListModel.addElement("Field14");
		sourceListModel.addElement("Field15");
	  return sourceListModel;
  }

  private DefaultListModel<String> getSourceList2(){
	  sourceListModel2 = new DefaultListModel<String>();
		sourceListModel2.addElement("Field1");
		sourceListModel2.addElement("Field2");
		sourceListModel2.addElement("Field3");
		sourceListModel2.addElement("Field5");
		sourceListModel2.addElement("Field6");
		sourceListModel2.addElement("Field7");
		sourceListModel2.addElement("Field8");
		sourceListModel2.addElement("Field9");
		sourceListModel2.addElement("Field10");
		sourceListModel2.addElement("Field11");
		sourceListModel2.addElement("Field12");
		sourceListModel2.addElement("Field13");
		sourceListModel2.addElement("Field14");
		sourceListModel2.addElement("Field15");
	  return sourceListModel2;
  }

 
  private JScrollPane JListLeft(){  

	sourceList = new JList<String>(getSourceList());
	sourceList.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
	
 
   
	 JScrollPane JSCRLP = new JScrollPane(sourceList);
	 JSCRLP.setBounds(6, 15, 80, 150);
	 
  return JSCRLP;
  }
  private JScrollPane JListLeft2(){  

		sourceList2 = new JList<String>(getSourceList2());
		sourceList2.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
		
	 
	   
		 JScrollPane JSCRLP2 = new JScrollPane(sourceList2);
		 JSCRLP2.setBounds(6, 15, 80, 150);
		 
	  return JSCRLP2;
	  }
  private JScrollPane JListLeftSel(){ 
	destListModel = new DefaultListModel<String>();
	destList = new JList<String>(destListModel);
	destList.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
	
  
     
	 JScrollPane PaneSelected = new JScrollPane(destList);
	 PaneSelected.setBounds(128, 15, 80, 150);
	 
  return PaneSelected; }
  private JScrollPane JListLeftSel2(){ 
		destListModel2 = new DefaultListModel<String>();
		destList2 = new JList<String>(destListModel2);
		destList2.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
		
	  
	     
		 JScrollPane PaneSelected = new JScrollPane(destList2);
		 PaneSelected.setBounds(128, 15, 80, 150);
		 
	  return PaneSelected; }
  
  private JButton getAddButton(){
	  addButton = new JButton();
	  addButton.setText(">");
	  addButton.addActionListener(this);
	  addButton.setBounds(87, 45, 40, 30);
	  return addButton;
  }
  
  private JButton getRemoveButton(){
	  removeButton = new JButton();
	  removeButton.setText("<");
	  removeButton.addActionListener(this);
	  removeButton.setBounds(87, 105, 40, 30);
	  return removeButton;
  }
  private JButton getAddButton2(){
	  addButton2 = new JButton();
	  addButton2.setText(">");
	  addButton2.addActionListener(this);
	  addButton2.setBounds(87, 45, 40, 30);
	  return addButton2;
  }
  
  private JButton getRemoveButton2(){
	  removeButton2 = new JButton();
	  removeButton2.setText("<");
	  removeButton2.addActionListener(this);
	  removeButton2.setBounds(87, 105, 40, 30);
	  return removeButton2;
  }
   
 @Override
 public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		 int i = 0;
	        
	        // When the 'in' button is pressed,
	        // we take the indices and values of the selected items
	        // and output them to an array.

	        if(e.getSource() == addButton)
	        {
	            int[] fromindex = sourceList.getSelectedIndices();
	            Object[] from = sourceList.getSelectedValues();

	            // Then, for each item in the array, we add them to
	            // the other list.
	            for(i = 0; i < from.length; i++)
	            {
	               destListModel.addElement((String) from[i]);
	            }
	            
	            // Finally, we remove the items from the first list.
	            // We must remove from the bottom, otherwise we try to 
	            // remove the wrong objects.
	            for(i = (fromindex.length-1); i >=0; i--)
	            {
	                sourceListModel.remove(fromindex[i]);
	            }
	        }
	        
	        if(e.getSource() == addButton2)
	        {
	            int[] fromindex = sourceList2.getSelectedIndices();
	            Object[] from = sourceList2.getSelectedValues();

	            // Then, for each item in the array, we add them to
	            // the other list.
	            for(i = 0; i < from.length; i++)
	            {
	               destListModel2.addElement((String) from[i]);
	            }
	            
	            
	            for(i = (fromindex.length-1); i >=0; i--)
	            {
	                sourceListModel2.remove(fromindex[i]);
	            }
	        }
	        
	        if(e.getSource() == removeButton2)
	        {
	            Object[] to = destList2.getSelectedValues();
	            int[] toindex = destList2.getSelectedIndices();
	            
	            for(i = 0; i < to.length; i++)
	            {
	                sourceListModel2.addElement((String) to[i]);
	            }
	            
	            for(i = (toindex.length-1); i >=0; i--)
	            {
	         		destListModel2.remove(toindex[i]);
	         	}
	        }
	        else if(e.getSource() == removeButton)
	        {
	            Object[] to = destList.getSelectedValues();
	            int[] toindex = destList.getSelectedIndices();
	            
	            // Then, for each item in the array, we add them to
	            // the other list.
	            for(i = 0; i < to.length; i++)
	            {
	                sourceListModel.addElement((String) to[i]);
	            }
	            
	            // Finally, we remove the items from the first list.
	            // We must remove from the bottom, otherwise we try to
	            // remove the wrong objects.
	            for(i = (toindex.length-1); i >=0; i--)
	            {
	         		destListModel.remove(toindex[i]);
	         		
	            }
	        }
	    }
	
  
  
  
  
  private JPanel getJPanelL() {
    if (jPanelL == null) {
      jPanelL = new JPanel();
      jPanelL.setLayout(null);
      jPanelL.setBounds(new Rectangle(0, 0, 214, 250));
      TitledBorder titled = new TitledBorder("Select fields for LEFT side");
      jPanelL.setBorder(titled);
      jPanelL.add(getAddButton());
      jPanelL.add(getRemoveButton());      
      jPanelL.add(JListLeft());
      jPanelL.add(JListLeftSel());
    }
    return jPanelL;
  }


  private JPanel getJPanelR() {
    if (jPanelR == null) {
      jPanelR = new JPanel();
      jPanelR.setLayout(null);
      jPanelR.setBounds(new Rectangle(214, 0, 214, 250));
      TitledBorder titled = new TitledBorder("Select fields for RIGHT side");
      jPanelR.setBorder(titled);
      jPanelR.add(getAddButton2());
      jPanelR.add(getRemoveButton2());      
      jPanelR.add(JListLeft2());
      jPanelR.add(JListLeftSel2());
    }
    return jPanelR;
  }
  
  
  /**
   * This method creates a TextField that could accept dispersal ratio for the pointDispersalRenderer
   * @return javax.swing.JTextField 
   */
  public JTextField getJTextField() {
    if (jTextField == null) {
      jTextField = new JTextField();
      jTextField.setBounds(new Rectangle(15, 23, 130, 127));
    }
    return jTextField;
  }

  static {
    try {
      UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
    }catch (Exception e){e.printStackTrace();}
  }

  public static void main(String[] args) throws Exception {
    PopyRendererPropertyPageUI thisclass = new PopyRendererPropertyPageUI();
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
	return 9;
}

@Override
public void setField(int index, String name) throws IOException, AutomationException {
	// TODO Auto-generated method stub
	
}

@Override
public void setFieldAlias(int index, String name) throws IOException, AutomationException {
	// TODO Auto-generated method stub
	
}


}

