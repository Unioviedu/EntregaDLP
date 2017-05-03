/**
 * @generated VGen 1.3.3
 */

package ast;

import visitor.*;

//	print:imprimir, sentencia -> expresion:expresion

public class Print extends AbstractTraceable implements Imprimir, Sentencia {

	public Print(Expresion expresion, String separador) {
		this.expresion = expresion;
		this.separador = separador;

		searchForPositions(expresion);	// Obtener linea/columna a partir de los hijos
	}

	public Print(Object expresion, String separador) {
		this.expresion = (Expresion) expresion;
		this.separador = (String) separador;
		
		searchForPositions(expresion);	// Obtener linea/columna a partir de los hijos
	}

	public Expresion getExpresion() {
		return expresion;
	}
	public void setExpresion(Expresion expresion) {
		this.expresion = expresion;
	}

	@Override
	public Object accept(Visitor v, Object param) { 
		return v.visit(this, param);
	}
	
	@Override
	public DefFuncion getDefFuncion() {
		return funcion;
	}

	@Override
	public void setDefFuncion(DefFuncion funcion) {
		this.funcion = funcion;
	}

	public String getSeparador() {
		return separador;
	}

	public void setSeparador(String separador) {
		this.separador = separador;
	}



	private Expresion expresion;
	private DefFuncion funcion;
	private String separador;
}

