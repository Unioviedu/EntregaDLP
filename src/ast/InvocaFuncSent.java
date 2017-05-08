/**
 * @generated VGen 1.3.3
 */

package ast;

import java.util.*;
import visitor.*;

//	callFuncSent:sentencia -> nombre:String  argumentos:expresion*

public class InvocaFuncSent extends AbstractSentencia {

	public InvocaFuncSent(String nombre, List<Expresion> argumentos) {
		this.nombre = nombre;
		this.argumentos = argumentos;

		searchForPositions(argumentos);	// Obtener linea/columna a partir de los hijos
	}

	@SuppressWarnings("unchecked")
	public InvocaFuncSent(Object nombre, Object argumentos) {
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

	public DefFuncion getDefFuncionInvoca() {
		return defFuncionInvoca;
	}

	public void setDefFuncionInvoca(DefFuncion defFuncionInvoca) {
		this.defFuncionInvoca = defFuncionInvoca;
	}

	private String nombre;
	private List<Expresion> argumentos;
	private DefFuncion funcion;
	private DefFuncion defFuncionInvoca;
}

