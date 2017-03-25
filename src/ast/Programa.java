/**
 * @generated VGen 1.3.3
 */

package ast;

import java.util.*;
import visitor.*;

//	programa -> definiciones:definiciones*

public class Programa extends AbstractTraceable implements AST {

	public Programa(List<Definiciones> definiciones) {
		this.definiciones = definiciones;

		searchForPositions(definiciones);	// Obtener linea/columna a partir de los hijos
	}

	@SuppressWarnings("unchecked")
	public Programa(Object definiciones) {
		this.definiciones = (List<Definiciones>) definiciones;

		searchForPositions(definiciones);	// Obtener linea/columna a partir de los hijos
	}

	public List<Definiciones> getDefiniciones() {
		return definiciones;
	}
	public void setDefiniciones(List<Definiciones> definiciones) {
		this.definiciones = definiciones;
	}

	@Override
	public Object accept(Visitor v, Object param) { 
		return v.visit(this, param);
	}

	private List<Definiciones> definiciones;
}

