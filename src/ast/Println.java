/**
 * @generated VGen 1.3.3
 */

package ast;

import visitor.*;

//	println:imprimir, sentencia -> expresion:expresion

public class Println extends AbstractTraceable implements Imprimir, Sentencia {

	public Println(Expresion expresion) {
		this.expresion = expresion;

		searchForPositions(expresion);	// Obtener linea/columna a partir de los hijos
	}

	public Println(Object expresion) {
		this.expresion = (Expresion) expresion;

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

	private Expresion expresion;
	private DefFuncion funcion;
}

