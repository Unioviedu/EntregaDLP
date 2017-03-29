/**
 * @generated VGen 1.3.3
 */

package ast;

import java.util.*;

import visitor.*;

//	callFunc:expresion -> nombre:String  argumentos:expresion*

public class CallFunc extends AbstractExpresion {

	public CallFunc(String nombre, List<Expresion> argumentos) {
		this.nombre = nombre;
		this.argumentos = argumentos;

		searchForPositions(argumentos);	// Obtener linea/columna a partir de los hijos
	}

	@SuppressWarnings("unchecked")
	public CallFunc(Object nombre, Object argumentos) {
		this.nombre = (nombre instanceof Token) ? ((Token)nombre).getLexeme() : (String) nombre;
		this.argumentos = (List<Expresion>) argumentos;

		searchForPositions(nombre, argumentos);	// Obtener linea/columna a partir de los hijos
	}

	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public List<Expresion> getArgumentos() {
		return argumentos;
	}
	public void setArgumentos(List<Expresion> argumentos) {
		this.argumentos = argumentos;
	}
	
	public void setDefinicionFuncion(DefFuncion definicionFuncion) {
		this.definicionFuncion = definicionFuncion;
	}

	public DefFuncion getDefinicionFuncion() {
		return definicionFuncion;
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

	private String nombre;
	private List<Expresion> argumentos;
	private DefFuncion definicionFuncion;
	private Tipo tipo;
	private boolean modificable;
}

