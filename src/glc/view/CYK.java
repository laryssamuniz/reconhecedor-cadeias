/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package glc.view;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

/**
 *
 * @author laryssamuniz
 */
public class CYK {
    
   private static ArrayList<String>[][] table;
   private HashMap<String, String[]> variables;
   private HashMap<String, Character> terminals;

   private static String vInicial;
    /**
    * Constructs a Cyk object and initializes the HashMaps of the variables and the terminals
    */
    public CYK(){
    }

    CYK(ArrayList resultItens, String text) {
        
        variables = new HashMap<>();
        terminals = new HashMap<>();
        
        ArrayList listTerminals = new ArrayList();
        ArrayList listVariables = new ArrayList();
        
        for (Object object: resultItens) {
            
            String[] varIni = object.toString().split("->");
            String variable = varIni[0];
            vInicial = varIni[0];
            
            if(!listVariables.contains(varIni[0])){
                listVariables.add(varIni[0]);
            }
            
            String varTerm = varIni[1].replace(";", "").trim();
             
            if(varTerm.equals("a") || varTerm.equals("b")){
                terminals.put(variable, varTerm.charAt(0));
            }else{
                String[] terList = varIni[1].split("[|]");
                
                int count = terList.length;
                
                if (terList != null){
                    
                    for (int i = 0; i < count; i++) {
                        String term = terList[i].replace(";", "");
                        listTerminals.add(term);
                    }
                    
                    String[] termArr = new String[listTerminals.size()];
                    termArr = (String[]) listTerminals.toArray(termArr);
                    
                    variables.put(variable, termArr);
                }
            }
        }
    }
    
    public boolean validateChain(String w){
        
        int length = w.length();
        
        table = new ArrayList[length][];
        
        for (int i = 0; i < length; ++i){
            
           table[i] = new ArrayList[length];
           
           for (int j = 0; j < length; ++j)
              table[i][j] = new ArrayList <> ();
        }
        
        for (int i = 0; i < length; ++i){
            
           Set<String> keys = terminals.keySet();
           
           for (String key : keys){
               
              if (terminals.get(key) == w.charAt(i)){
                  table[i][i].add(key);
              }     
           }
        }
        
        for (int l = 2; l <= length; ++l){
            
           for (int i = 0; i <= length - l; ++i){
               
              int j = i + l - 1;
              
              for (int k = i; k <= j - 1; ++k){
                  
                 Set<String> keys = variables.keySet();
                 
                 for (String key : keys){
                 
                    String[] values = variables.get(key);
                    if (table[i][k].contains((values[0])) && table[k + 1][j].contains(values[1])){
                       table[i][j].add(key); 
                    }
                 }
              }
           }
        }
      if (table[0][length - 1].contains(vInicial)){return true;}
      return false;
   }
    
    
}
