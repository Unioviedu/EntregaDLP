/**
 * @generated VGen 1.3.3
 */

package ast;

import java.util.*;
import visitor.*;

//	defFuncion:definiciones -> nombre:String  parametro:parametro*  tipo:tipo  defvariable:defVariable*  sentencia:sentencia*

public class DefFuncion extends AbstractDefiniciones {

	public DefFuncion(String nombre, List<Parametro> parametro, Tipo tipo, List<DefVariable> defvariable, List<Sentencia> sentencia) {
		this.nombre = nombre;
		this.parametro = parametro;
		this.tipo = tipo;
		this.defvariable = defvariable;
		this.sentencia = sentencia;

		searchForPositions(parametro, tipo, defvariable, sentencia);	// Obtener linea/columna a partir de los hijos
	}

	@SuppressWarnings("unchecked")
	public DefFuncion(Object nombre, Object parametro, Object tipo, Object defvariable, Object sentencia) {
		this.nombre = (nombre instanceof Token) ? ((Token)nombre).getLexeme() : (String) nombre;
		this.parametro = (List<Parametro>) parametro;
		this.tipo = (Tipo) tipo;
		this.defvariable = (List<DefVariable>) defvariable;
		this.sentencia = (List<Sentencia>) sentencia;

		searchForPositions(nombre, parametro, tipo, defvariable, sentencia);	// Obtener linea/columna a partir de los hijos
	}

	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public List<Parametro> getParametro() {
		return parametro;
	}
	public void setParametro(List<Parametro> parametro) {
		this.parametro = parametro;
	}

	public Tipo getTipo() {
		return tipo;
	}
	public void setTipo(Tipo tipo) {
		this.tipo = tipo;
	}

	public List<DefVariable> getDefvariable() {
		return defvariable;
	}
	public void setDefvariable(List<DefVariable> defvariable) {
		this.defvariable = defvariable;
	}

	public List<Sentencia> getSentencia() {
		return sentencia;
	}
	public void setSentencia(List<Sentencia> sentencia) {
		this.sentencia = sentencia;
	}

	@Override
	public Object accept(Visitor v, Object param) { 
		return v.visit(this, param);
	}

	private String nombre;
	private List<Parametro> parametro;
	private Tipo tipo;
	private List<DefVariable> defvariable;
	private List<Sentencia> sentencia;
}

