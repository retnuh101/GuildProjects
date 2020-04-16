/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dto;

/**
 *
 * @author hschuler2992
 */
public class ChangePurse {
    
    private int numPennies;
    private int numNickels;
    private int numDimes;
    private int numQuarters;

    public ChangePurse(){}

    public ChangePurse(int numPennies, int numNickels, int numDimes, int numQuarters) {
        this.numPennies = numPennies;
        this.numNickels = numNickels;
        this.numDimes = numDimes;
        this.numQuarters = numQuarters;
    }
        
    public int getNumPennies() {
        return numPennies;
    }

    public void setNumPennies(int numPennies) {
        this.numPennies = numPennies;
    }

    public int getNumNickels() {
        return numNickels;
    }

    public void setNumNickels(int numNickels) {
        this.numNickels = numNickels;
    }

    public int getNumDimes() {
        return numDimes;
    }

    public void setNumDimes(int numDimes) {
        this.numDimes = numDimes;
    }

    public int getNumQuarters() {
        return numQuarters;
    }

    public void setNumQuarters(int numQuarters) {
        this.numQuarters = numQuarters;
    }
    
    
}
