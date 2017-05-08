/**
 * @generated VGen 1.3.3
 */

package ast;

import visitor.*;

//	campoStruct:expresion -> left:expresion  right:String

public class AccesoCampoStruct extends AbstractExpresion {

	public AccesoCampoStruct(Expresion left, String right) {
		this.left = left;
		this.right = right;

		searchForPositions(left);	// Obtener linea/columna a partir de los hijos
	}

	public AccesoCampoStruct(Object left, Object right) {
		this.left = (Expresion) left;
		this.right = (right instanceof Token) ? ((Token)right).getLexeme() : (String) right;

		searchForPositions(left, right);	// Obtener linea/columna a partir de los hijos
	}

	public Expresion getLeft() {
		return left;
	}
	public void setLeft(Expresion left) {
		this.left = left;
	}

	public String getRight() {
		return right;
	}
	public void setRight(String right) {
		this.right = right;
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

	private Expresion left;
	private String right;
	private Tipo tipo;
	private boolean modificable;
}

