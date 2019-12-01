package dad.javafx.bindings.calculadoracompleja;


import javafx.application.Application;
import javafx.beans.binding.Bindings;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.converter.NumberStringConverter;

public class CalculadoraComplejaApp extends Application {
	
	// clases complejo
	
	private Complejo complex1 = new Complejo();
	private Complejo complex2 = new Complejo();
	private Complejo resultado = new Complejo();
	
	private DoubleProperty elevado = new SimpleDoubleProperty(2);
	
	
	//  modelo
	
	private StringProperty operador = new SimpleStringProperty();
	

	//  vista
	
	private TextField numero1_realText;
	private TextField numero1_imagText;
	private TextField numero2_realText;
	private TextField numero2_imagText;
	
	private TextField resultado_realText;
	private TextField resultado_imagText;
	
	private ComboBox<String> operadorCombo;
	
	@Override
	public void start(Stage primaryStage) throws Exception {
		
	
		numero1_realText = new TextField();
		numero1_realText.setPrefColumnCount(4);
		numero1_imagText = new TextField();
		numero1_imagText.setPrefColumnCount(4);
		
		numero2_realText = new TextField();
		numero2_realText.setPrefColumnCount(4);
		numero2_imagText = new TextField();
		numero2_imagText.setPrefColumnCount(4);
		
		resultado_realText = new TextField();
		resultado_realText.setPrefColumnCount(4);
		resultado_realText.setDisable(true);
		resultado_imagText = new TextField();
		resultado_imagText.setPrefColumnCount(4);
		resultado_imagText.setDisable(true);
		
		operadorCombo = new ComboBox<String>();
		operadorCombo.getItems().addAll("+", "-", "*", "/");
		

		// VBox operadores: incluye todos los números el separador y el resultado
		
			HBox numero1Box = new HBox(5, numero1_realText, new Label(" + "), numero1_imagText, new Label("i"));
			numero1Box.setAlignment(Pos.BASELINE_CENTER);
			
			HBox numero2Box = new HBox(5, numero2_realText, new Label(" + "), numero2_imagText, new Label("i"));
			numero2Box.setAlignment(Pos.BASELINE_CENTER);
			
			Separator separator = new Separator(Orientation.HORIZONTAL);
			
			HBox resultadoBox = new HBox(5, resultado_realText, new Label(" + "), resultado_imagText, new Label("i"));
			resultadoBox.setAlignment(Pos.BASELINE_CENTER);
			
			VBox operadores = new VBox(5, numero1Box, numero2Box, separator, resultadoBox);
			operadores.setAlignment(Pos.CENTER);
			
		// VBox operacion: incluye el comboBox que selecciona la operacion
			
			VBox operacion = new VBox(5, operadorCombo);
			operacion.setAlignment(Pos.CENTER);
			
		// HBox Global
			
		HBox root = new HBox(5, operacion, operadores);
		root.setAlignment(Pos.CENTER);
			
		Scene scene = new Scene(root, 320, 200);
		
		primaryStage.setTitle("Calculadora Compleja");
		primaryStage.setScene(scene);
		primaryStage.show();
		
		
		// bindeos
		
		Bindings.bindBidirectional(numero1_realText.textProperty(), complex1.Real(), new NumberStringConverter());
		Bindings.bindBidirectional(numero1_imagText.textProperty(), complex1.Imag(), new NumberStringConverter());
		
		Bindings.bindBidirectional(numero2_realText.textProperty(), complex2.Real(), new NumberStringConverter());
		Bindings.bindBidirectional(numero2_imagText.textProperty(), complex2.Imag(), new NumberStringConverter());

		Bindings.bindBidirectional(resultado_realText.textProperty(), resultado.Real(), new NumberStringConverter());
		Bindings.bindBidirectional(resultado_imagText.textProperty(), resultado.Imag(), new NumberStringConverter());
	
		operador.bind(operadorCombo.getSelectionModel().selectedItemProperty());
		
		
		// listener
		operador.addListener((o, ov, nv) -> onOperadorChanged(nv));
		
		// seleción por defecto de la operacion
		operadorCombo.getSelectionModel().selectFirst();
	}
	
	
	private void onOperadorChanged(String nv) {
		
		switch(nv) {
		case "+":
			resultado.Real().bind(complex1.Real().add(complex2.Real() ));
			resultado.Imag().bind(complex1.Imag().add(complex2.Imag() ));
			break;
			
		 case "-":
			resultado.Real().bind(complex1.Real().subtract(complex2.Real() ));
			resultado.Imag().bind(complex1.Imag().subtract(complex2.Imag() ));	
			break;
			
		case "*": 
			resultado.getReal().bind(
					complex1.Real().multiply(complex2.Real()).subtract(complex1.Imag().multiply(complex2.Imag())) 
					);
			resultado.getImag().bind(
					complex1.Real().multiply(complex2.Imag()).add(complex1.Imag().multiply(complex2.Real()))
					);	
			break;
		case "/":
			
			PowBinding pow1 = new PowBinding(complex2.Real(), elevado);
			PowBinding pow2 = new PowBinding(complex2.Imag(), elevado);
			
			resultado.getReal().bind(
					
					(complex1.Real().multiply(complex2.Real()))
						.add(complex1.Imag().multiply(complex2.Imag()))
					.divide(
						//complex2.Real().multiply(complex2.Real())
						//Bindings.createDoubleBinding( () -> Math.pow(factor.get(), val1.get()), complex2.getReal(), 2))
						pow1.add(pow2)
						)
					
					);
			resultado.getImag().bind(
					
					(complex1.Imag().multiply(complex2.Imag())
							.subtract(complex1.Real().multiply(complex2.Imag())))
					.divide(
						pow1.add(pow2)
						)
					);
			
			break;
		}
	}
	
	
	public static void main(String[] args) {
		launch(args);		
	}
	


}
