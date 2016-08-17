/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package glc.view;

import java.util.ArrayList;

/**
 *
 * @author laryssamuniz
 */
public class CYK {
    
    public CYK(){
    }
    
    CYK(ArrayList resultItens, String sequence){
        
        ArrayList listGrammar = new ArrayList();
        ArrayList listTerminals = new ArrayList();
        ArrayList listVariables = new ArrayList();
        
        for (int i = 0; i < resultItens.size(); i++) {
            
            String[] variables = resultItens.get(i).toString().split("->");
            
            for (int j = 0; j < variables.length; j++) {
                
                listVariables.add(variables[0]);
                
                String[] terminails = variables[1].split("|");
                
                System.out.println(terminails);
                    
                
//                for (int k = 0; k < terminails.length; k++) {
//                    
//                }
            }
        }
    }

    void generate() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
