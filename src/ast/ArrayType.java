/**
 * @generated VGen 1.3.3
 */

package ast;

import visitor.*;

//	arrayType:tipo -> dimension:int  tipo:tipo

public class ArrayType extends AbstractTipo {

	public ArrayType(int dimension, Tipo tipo) {
		this.dimension = dimension;
		this.tipo = tipo;

		searchForPositions(tipo);	// Obtener linea/columna a partir de los hijos
	}

	public ArrayType(Object dimension, Object tipo) {
		this.dimension = (dimension instanceof Token) ? Integer.parseInt(((Token)dimension).getLexeme()) : (Integer) dimension;
		this.tipo = (Tipo) tipo;

		searchForPositions(dimension, tipo);	// Obtener linea/columna a partir de los hijos
	}

	public int getDimension() {
		return dimension;
	}
	public void setDimension(int dimension) {
		this.dimension = dimension;
	}

	public Tipo getTipo() {
		return tipo;
	}
	public void setTipo(Tipo tipo) {
		this.tipo = tipo;
	}

	@Override
	public Object accept(Visitor v, Object param) { 
		return v.visit(this, param);
	}

	private int dimension;
	private Tipo tipo;
}

