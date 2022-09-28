/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package msg;

import java.awt.Component;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JCheckBox;
import javax.swing.JPanel;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

/**
 *
 * @author Support
 */
public class methods_to_share {
    //initialise variables for methods:
    String timerValue = " ";
    int crS = 0;
    int blrS = 0;
    int mcS = 0;
    boolean hipempty = true;
    boolean fieldempty = true;
    private javax.swing.JSlider timerslider;
    private javax.swing.JTabbedPane venue_select_pane;
    private javax.swing.JButton sendb;
    private javax.swing.JTextArea messagearea;
    private javax.swing.JPanel MC_Panel;
    private javax.swing.JCheckBox all_blr;
    private javax.swing.JCheckBox all_cr;
    private javax.swing.JCheckBox all_mc;
    private javax.swing.JPanel CR_Panel;
    private javax.swing.JPanel C_Panel;
    private javax.swing.JTextField HorIP;
    private javax.swing.JPanel BLR_Panel;
    private javax.swing.JCheckBox HP04;
    private javax.swing.JLabel timerlabel;
    
    //method enables or disables button based on variable states
    public void buttoncheck(){
        if (mcS>0 || crS>0 || blrS>0 || !hipempty)
        {
            if(!fieldempty)
            {
                sendb.setEnabled(true);
            }
        }
        else
        {
            sendb.setEnabled(false);
        }
    }
    
    //method name self explanatory
    public int DeselectAllinPanel (int s,javax.swing.JPanel panel){
        for (Component c : panel.getComponents()) 
        {
                if (c instanceof JCheckBox checkBox) 
                {
                    checkBox.setSelected(false);
                }
        }
        s = 0;
        return s;
    }
    
    //method name self explanatory
    public int SelectAllinPanel (int s,javax.swing.JPanel panel){
        for (Component c : panel.getComponents()) 
        {
                if (c instanceof JCheckBox checkBox) 
                {
                    checkBox.setSelected(true);
                }
        }
        if (panel.getName().equals(MC_Panel.getName())){
            s = 36;
        }
        else
        {
            s = 30;
        }
        return s;
    }
    
    //method selects all handles logic for whether to select/deselect boxes for all on each panel
    public void CheckWholePanel (){//goal here is to add parameters that remove the need for if statements
        if (CR_Panel.isVisible()) 
        {
            if (all_cr.isSelected()) 
            {
                crS = SelectAllinPanel(crS,CR_Panel);
            } 
            else 
            {
                if (all_cr.isFocusOwner())
                {
                    crS = DeselectAllinPanel(crS,CR_Panel);
                }
            }
            blrS = DeselectAllinPanel(blrS,BLR_Panel);
            mcS = DeselectAllinPanel(mcS,MC_Panel);
        }
        if (BLR_Panel.isVisible()) 
        {
            if (all_blr.isSelected()) 
            {
                blrS = SelectAllinPanel(blrS,BLR_Panel);
            } 
            else 
            {
                if (all_blr.isFocusOwner())
                {
                    blrS = DeselectAllinPanel(blrS,BLR_Panel);
                }
            }
            crS = DeselectAllinPanel(crS,CR_Panel);
            mcS = DeselectAllinPanel(mcS,MC_Panel);
        }
        if (MC_Panel.isVisible()) 
        {
            if (all_mc.isSelected()) 
            {
                mcS = SelectAllinPanel(mcS,MC_Panel);
            } 
            else 
            {
                if (all_mc.isFocusOwner())
                {
                    mcS = DeselectAllinPanel(mcS,MC_Panel);
                }
            }
            blrS = DeselectAllinPanel(blrS,BLR_Panel);
            crS = DeselectAllinPanel(crS,CR_Panel);
        }
        if (C_Panel.isVisible()) 
        {
            blrS = DeselectAllinPanel(blrS,BLR_Panel);
            crS = DeselectAllinPanel(crS,CR_Panel);
            mcS = DeselectAllinPanel(mcS,MC_Panel);
        }
        buttoncheck();
    }
    
    //method enables or diables button based on whether the host name or IP field is populated
    public void checkHorIP(){
        if (HorIP.getText().equals(""))
        {
            hipempty = true;
            sendb.setEnabled(false);
        } 
        else
        {
            hipempty = false;
            checkmessagearea();
        }
    }
    
    //method enables or diables button based on whether the messagearea is populated
    public void checkmessagearea(){
        if (messagearea.getText().equals(""))
        {
            sendb.setEnabled(false);
            fieldempty = true;
        } 
        else
        {
            fieldempty = false;
            buttoncheck();
        }
    }
    
    //method sends message depending on panels focused and variables pupulated
    public void message (javax.swing.JPanel panel){
        List<String> cmdsend = new ArrayList<>();
        if (panel == C_Panel)
        {
            cmdsend.add("msg * /server:" + HorIP.getText() + " " + timerValue + " " + messagearea.getText());
        } 
        else 
        {
        for (Component c : panel.getComponents())
        {
            if (c instanceof JCheckBox checkBox)
            {
                if (checkBox.getToolTipText() != null)
                {
                    if (checkBox.isSelected()) 
                    {
                        cmdsend.add("msg * /SERVER:" + checkBox.getToolTipText() + " " + timerValue + " " + messagearea.getText());
                    }
                }
            }
        }
        }
        try 
        {
            for (int i = 0; i < cmdsend.size(); i++) 
            {
                Runtime.getRuntime().exec(cmdsend.get(i));
            }
        } 
        catch (IOException ex) 
        {
            System.out.println("failed");
        }
    }
    
    //method updates variables when a check box is selected
    public int logBoxUpdate(javax.swing.JCheckBox component, int room, javax.swing.JCheckBox all) {
        if (component.isSelected()) 
        {
            room++;
            if (MC_Panel.isVisible()&&room ==36)
            {
                all.setSelected(true);
            }
            else
            {
                if (!MC_Panel.isVisible()&&room == 30) 
                {
                    all.setSelected(true);
                }
            }
            if (!messagearea.getText().equals("")) 
            {
                sendb.setEnabled(true);
            }
        } 
        else 
        {
            room--;
            all.setSelected(false);
            if (room == 0) 
            {
                sendb.setEnabled(false);
            }
        }
        return room;
    }

    //example of check box code
    private void HP04ActionPerformed(java.awt.event.ActionEvent evt) {                                     
        blrS = logBoxUpdate(HP04, blrS, all_blr);
    } 
    
    //example of an "all" check box on one of the panels
    private void all_mcActionPerformed(java.awt.event.ActionEvent evt) {                                       
        CheckWholePanel();
    } 
    
    //action perfromed for the button to send message
    private void sendbActionPerformed(java.awt.event.ActionEvent evt) {                                      
        message((JPanel) venue_select_pane.getSelectedComponent());                
    }
    
    //timer slider state change
    private void timersliderStateChanged(javax.swing.event.ChangeEvent evt) {                                         
        if (timerslider.getValue() != 0) 
        {
            timerValue = " /time:" + timerslider.getValue();
            timerlabel.setText(timerslider.getValue() + "s Timer");
        } 
        else 
        {
            timerValue = "";
            timerlabel.setText("No Timer");
        }
    }
    
    private void initComponents() {
    //listens for updates in host name or IP field    
    HorIP.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                checkHorIP();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                checkHorIP();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                checkHorIP();
            }
        });
    
    //listens for updates in message field     
    messagearea.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                checkmessagearea();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                checkmessagearea();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                checkmessagearea();
            }
        });    
    }
}
