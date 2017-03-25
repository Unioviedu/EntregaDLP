/**
 * @generated VGen 1.3.3
 */

package ast;

import visitor.*;

//	callArray:expresion -> variable:expresion  acceso:expresion

public class CallArray extends AbstractExpresion {

	public CallArray(Expresion variable, Expresion acceso) {
		this.variable = variable;
		this.acceso = acceso;

		searchForPositions(variable, acceso);	// Obtener linea/columna a partir de los hijos
	}

	public CallArray(Object variable, Object acceso) {
		this.variable = (Expresion) variable;
		this.acceso = (Expresion) acceso;

		searchForPositions(variable, acceso);	// Obtener linea/columna a partir de los hijos
	}

	public Expresion getVariable() {
		return variable;
	}
	public void setVariable(Expresion variable) {
		this.variable = variable;
	}

	public Expresion getAcceso() {
		return acceso;
	}
	public void setAcceso(Expresion acceso) {
		this.acceso = acceso;
	}

	@Override
	public Object accept(Visitor v, Object param) { 
		return v.visit(this, param);
	}

	private Expresion variable;
	private Expresion acceso;
}

