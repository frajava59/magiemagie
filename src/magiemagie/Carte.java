/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package magiemagie;

/**
 *
 * @author Formation
 */
public class Carte {
    
    public enum TypeCarte{
        BAVE_DE_CRAPAUD,
        AILE_DE_CHAUVE_SOURIS,
        LAPIS_LAZULI,
        MANDRAGORE,
        CORNE_DE_LICORNE
    }
    
    
    protected TypeCarte type;

    public TypeCarte getType() {
        return type;
    }

    public void setType(TypeCarte type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return type.toString();
    }

//    @Override
//    public boolean equals(Object obj) {
//        
//        
//        Carte carte2 = (Carte) obj;
//        
//        return this.type.toString().equals( carte2.getType().toString() );
//    }
    
    
//    @Override
//    public boolean equals(Object obj) {
//        Carte carte2 = (Carte) obj;
//        
//        return 
//    }
    




    
    
}
