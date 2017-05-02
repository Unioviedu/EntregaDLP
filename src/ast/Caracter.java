/**
 * @generated VGen 1.3.3
 */

package ast;

import visitor.*;

//	caracter:expresion -> valor:String

public class Caracter extends AbstractExpresion {

	public Caracter(char valor) {
		this.valor = valor;
	}

	public Caracter(Object valor) {
		if (valor instanceof Token)
			this.valor = ((Token) valor).getLexeme().charAt(1);
		else
			this.valor = (char) valor;

		searchForPositions(valor);	// Obtener linea/columna a partir de los hijos
	}

	public char getValor() {
		return valor;
	}
	public void setValor(char valor) {
		this.valor = valor;
	}

	@Override
	public Object accept(Visitor v, Object param) { 
		return v.visit(this, param);
	}
	
	@Override
	public Tipo getTipo() {
		return tipo;
	}

	@Override
	public void setTipo(Tipo tipo) {
		this.tipo = tipo;
	}
	
	@Override
	public boolean getModificable() {
		return modificable;
	}

	@Override
	public void setModificable(boolean modificable) {
		this.modificable = modificable;
	}

	private char valor;
	private Tipo tipo;
	private boolean modificable;
}

