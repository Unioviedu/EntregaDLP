/**
 * @generated VGen 1.3.3
 */

package ast;

import java.util.*;
import visitor.*;

//	condicional:sentencia -> expresion:expresion  sentif:sentencia*  sentelse:sentencia*

public class Condicional extends AbstractSentencia {

	public Condicional(Expresion expresion, List<Sentencia> sentif, List<Sentencia> sentelse) {
		this.expresion = expresion;
		this.sentif = sentif;
		this.sentelse = sentelse;

		searchForPositions(expresion, sentif, sentelse);	// Obtener linea/columna a partir de los hijos
	}

	@SuppressWarnings("unchecked")
	public Condicional(Object expresion, Object sentif, Object sentelse) {
		this.expresion = (Expresion) expresion;
		this.sentif = (List<Sentencia>) sentif;
		this.sentelse = (List<Sentencia>) sentelse;

		searchForPositions(expresion, sentif, sentelse);	// Obtener linea/columna a partir de los hijos
	}

	public Expresion getExpresion() {
		return expresion;
	}
	public void setExpresion(Expresion expresion) {
		this.expresion = expresion;
	}

	public List<Sentencia> getSentif() {
		return sentif;
	}
	public void setSentif(List<Sentencia> sentif) {
		this.sentif = sentif;
	}

	public List<Sentencia> getSentelse() {
		return sentelse;
	}
	public void setSentelse(List<Sentencia> sentelse) {
		this.sentelse = sentelse;
	}

	@Override
	public Object accept(Visitor v, Object param) { 
		return v.visit(this, param);
	}

	private Expresion expresion;
	private List<Sentencia> sentif;
	private List<Sentencia> sentelse;
}

