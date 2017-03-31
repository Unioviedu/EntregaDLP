/**
 * @generated VGen 1.3.3
 */

package visitor;

import ast.*;
import java.util.*;

/*
DefaultVisitor. Implementaci�n base del visitor para ser derivada por nuevos visitor.
	No modificar esta clase. Para crear nuevos visitor usar el fichero "_PlantillaParaVisitors.txt".
	DefaultVisitor ofrece una implementaci�n por defecto de cada nodo que se limita a visitar los nodos hijos.
*/
public class DefaultVisitor implements Visitor {

	//	class Programa { List<Definiciones> definiciones; }
	public Object visit(Programa node, Object param) {
		visitChildren(node.getDefiniciones(), param);
		return null;
	}

	//	class DefVariable { String nombre;  Tipo tipo; }
	public Object visit(DefVariable node, Object param) {
		if (node.getTipo() != null)
			node.getTipo().accept(this, param);
		return null;
	}

	//	class Struct { String nombre;  List<DefCampoStruct> defcampostruct; }
	public Object visit(Struct node, Object param) {
		visitChildren(node.getDefcampostruct(), param);
		return null;
	}

	//	class DefFuncion { String nombre;  List<Parametro> parametro;  Tipo tipo;  List<DefVariable> defvariable;  List<Sentencia> sentencia; }
	public Object visit(DefFuncion node, Object param) {
		visitChildren(node.getParametro(), param);
		if (node.getTipo() != null)
			node.getTipo().accept(this, param);
		visitChildren(node.getDefvariable(), param);
		visitChildren(node.getSentencia(), param);
		return null;
	}

	//	class Parametro { String nombre;  Tipo tipo; }
	public Object visit(Parametro node, Object param) {
		if (node.getTipo() != null)
			node.getTipo().accept(this, param);
		return null;
	}

	//	class DefCampoStruct { String nombre;  Tipo tipo; }
	public Object visit(DefCampoStruct node, Object param) {
		if (node.getTipo() != null)
			node.getTipo().accept(this, param);
		return null;
	}

	//	class IntType {  }
	public Object visit(IntType node, Object param) {
		return null;
	}

	//	class RealType {  }
	public Object visit(RealType node, Object param) {
		return null;
	}

	//	class FloatType {  }
	public Object visit(FloatType node, Object param) {
		return null;
	}

	//	class CharType {  }
	public Object visit(CharType node, Object param) {
		return null;
	}

	//	class StructType { String nombre; }
	public Object visit(StructType node, Object param) {
		return null;
	}

	//	class ArrayType { int dimension;  Tipo tipo; }
	public Object visit(ArrayType node, Object param) {
		if (node.getTipo() != null)
			node.getTipo().accept(this, param);
		return null;
	}

	//	class Asignacion { Expresion left;  Expresion right; }
	public Object visit(Asignacion node, Object param) {
		if (node.getLeft() != null)
			node.getLeft().accept(this, param);
		if (node.getRight() != null)
			node.getRight().accept(this, param);
		return null;
	}

	//	class CallFuncSent { String nombre;  List<Expresion> argumentos; }
	public Object visit(CallFuncSent node, Object param) {
		visitChildren(node.getArgumentos(), param);
		return null;
	}

	//	class Condicional { Expresion expresion;  List<Sentencia> sentif;  List<Sentencia> sentelse; }
	public Object visit(Condicional node, Object param) {
		if (node.getExpresion() != null)
			node.getExpresion().accept(this, param);
		visitChildren(node.getSentif(), param);
		visitChildren(node.getSentelse(), param);
		return null;
	}

	//	class Bucle { Expresion expresion;  List<Sentencia> sentencia; }
	public Object visit(Bucle node, Object param) {
		if (node.getExpresion() != null)
			node.getExpresion().accept(this, param);
		visitChildren(node.getSentencia(), param);
		return null;
	}

	//	class Read { Expresion expresion; }
	public Object visit(Read node, Object param) {
		if (node.getExpresion() != null)
			node.getExpresion().accept(this, param);
		return null;
	}

	//	class Return { Expresion expresion; }
	public Object visit(Return node, Object param) {
		if (node.getExpresion() != null)
			node.getExpresion().accept(this, param);
		return null;
	}

	//	class Print { Expresion expresion; }
	public Object visit(Print node, Object param) {
		if (node.getExpresion() != null)
			node.getExpresion().accept(this, param);
		return null;
	}

	//	class LiteralInt { String valor; }
	public Object visit(LiteralInt node, Object param) {
		return null;
	}

	//	class LiteralReal { String valor; }
	public Object visit(LiteralReal node, Object param) {
		return null;
	}

	//	class Caracter { String valor; }
	public Object visit(Caracter node, Object param) {
		return null;
	}

	//	class Variable { String nombre; }
	public Object visit(Variable node, Object param) {
		return null;
	}

	//	class Cast { Tipo tipo;  Expresion expresion; }
	public Object visit(Cast node, Object param) {
		if (node.getTipo() != null)
			node.getTipo().accept(this, param);
		if (node.getExpresion() != null)
			node.getExpresion().accept(this, param);
		return null;
	}

	//	class ExpresionBinaria { Expresion left;  String operador;  Expresion right; }
	public Object visit(ExpresionBinaria node, Object param) {
		if (node.getLeft() != null)
			node.getLeft().accept(this, param);
		if (node.getRight() != null)
			node.getRight().accept(this, param);
		return null;
	}

	//	class Negacion { Expresion expresion; }
	public Object visit(Negacion node, Object param) {
		if (node.getExpresion() != null)
			node.getExpresion().accept(this, param);
		return null;
	}

	//	class CallArray { Expresion variable;  Expresion acceso; }
	public Object visit(CallArray node, Object param) {
		if (node.getVariable() != null)
			node.getVariable().accept(this, param);
		if (node.getAcceso() != null)
			node.getAcceso().accept(this, param);
		return null;
	}

	//	class CampoStruct { Expresion left;  String right; }
	public Object visit(CampoStruct node, Object param) {
		if (node.getLeft() != null)
			node.getLeft().accept(this, param);
		return null;
	}

	//	class CallFunc { String nombre;  List<Expresion> argumentos; }
	public Object visit(CallFunc node, Object param) {
		visitChildren(node.getArgumentos(), param);
		return null;
	}
	
	// M�todo auxiliar -----------------------------
	protected void visitChildren(List<? extends AST> children, Object param) {
		if (children != null)
			for (AST child : children)
				child.accept(this, param);
	}
}
