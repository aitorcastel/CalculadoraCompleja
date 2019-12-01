package dad.javafx.bindings.calculadoracompleja;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;

public class Complejo {
	
    private DoubleProperty real = new SimpleDoubleProperty();;
    private DoubleProperty imag = new SimpleDoubleProperty();
    
    // https://docs.oracle.com/javafx/2/api/javafx/beans/binding/DoubleBinding.html
 
   //Constructor por defecto de la clase
   public Complejo() {
  
   }
   //Constructor con parámetros
   public Complejo(DoubleProperty real, DoubleProperty imag) {
       this.real = real;
       this.imag = imag;
   }

   //métodos setters y getters
   public DoubleProperty getImag() {
       return imag;
   }

   public void setImag(DoubleProperty imag) {
       this.imag = imag;
   }

   public DoubleProperty getReal() {
       return real;
   }

   public void setReal(DoubleProperty real) {
       this.real = real;
   }
   
   public final DoubleProperty Real() { return this.real; }
   public final DoubleProperty Imag() { return this.imag; }

} //Fin de la Clase Complejo Java
