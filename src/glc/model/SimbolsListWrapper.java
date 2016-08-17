/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package glc.model;

import java.util.List;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author laryssamuniz
 */
@XmlRootElement(name = "simbolsName")
public class SimbolsListWrapper {

    private List<Simbols> simbolsName;

    @XmlElement(name = "simbols")
    public List<Simbols> getSimbols() {
        return simbolsName;
    }

    public void setSimbols(List<Simbols> simbolsName) {
        this.simbolsName = simbolsName;
    }
}
