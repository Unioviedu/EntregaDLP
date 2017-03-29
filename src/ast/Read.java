/**
 * @generated VGen 1.3.3
 */

package ast;

import visitor.*;

//	read:sentencia -> expresion:expresion

public class Read extends AbstractSentencia {

	public Read(Expresion expresion) {
		this.expresion = expresion;

		searchForPositions(expresion);	// Obtener linea/columna a partir de los hijos
	}

	public Read(Object expresion) {
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

