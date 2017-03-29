/**
 * @generated VGen 1.3.3
 */

package ast;

import visitor.*;

//	literalReal:expresion -> valor:String

public class LiteralReal extends AbstractExpresion {

	public LiteralReal(String valor) {
		this.valor = valor;
	}

	public LiteralReal(Object valor) {
		this.valor = (valor instanceof Token) ? ((Token)valor).getLexeme() : (String) valor;

		searchForPositions(valor);	// Obtener linea/columna a partir de los hijos
	}

	public String getValor() {
		return valor;
	}
	public void setValor(String valor) {
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

	private String valor;
	private Tipo tipo;
	private boolean modificable;
}

