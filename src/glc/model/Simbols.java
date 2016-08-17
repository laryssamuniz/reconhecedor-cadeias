/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package glc.model;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 *
 * @author laryssamuniz
 */
public class Simbols {
    private final StringProperty notTerminalName;
    private final StringProperty terminalName;

    /**
     * Default constructor.
     */
    public Simbols() {
        this(null, null);
    }

    /**
     * Constructor with some initial data.
     * 
     * @param notTerminalName
     * @param terminalName
     */
    public Simbols(String notTerminalName, String terminalName) {
        this.notTerminalName = new SimpleStringProperty(notTerminalName);
        this.terminalName = new SimpleStringProperty(terminalName);
    }

    public String getnotTerminalName() {
        return notTerminalName.get();
    }

    public void setnotTerminalName(String notTerminalName) {
        this.notTerminalName.set(notTerminalName);
    }

    public StringProperty notTerminalNameProperty() {
        return notTerminalName;
    }

    public String getTerminalName() {
        return terminalName.get();
    }

    public void setTerminalName(String terminalName) {
        this.terminalName.set(terminalName);
    }

    public StringProperty terminalNameProperty() {
        return terminalName;
    }
}
