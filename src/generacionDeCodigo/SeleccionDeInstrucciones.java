package generacionDeCodigo;

import java.io.*;

import ast.*;
import visitor.*;

public class SeleccionDeInstrucciones extends DefaultVisitor {
	
	public static int DIRECCION = 0;
	public static int VALOR = 1;

	public SeleccionDeInstrucciones(Writer writer, String sourceFile) {
		this.writer = new PrintWriter(writer);
		this.sourceFile = sourceFile;
	}

	/*
	 * Poner aqu� los visit necesarios.
	 * Si se ha usado VGen solo hay que copiarlos de la clase 'visitor/_PlantillaParaVisitors.txt'.
	 */

	//	Ejemplo:
	
	public Object visit(Programa node, Object param) {
		genera("#source \"" + sourceFile + "\"");
		genera("call main");
		genera("halt");
		super.visit(node, param);	// Recorrer los hijos
		return null;
	}

//	class DefVariable { String nombre;  Tipo tipo; }
	public Object visit(DefVariable node, Object param) {

		// super.visit(node, param);

		return null;
	}

	//	class Struct { String nombre;  List<DefCampoStruct> defcampostruct; }
	public Object visit(Struct node, Object param) {

		// super.visit(node, param);

		if (node.getDefcampostruct() != null)
			for (DefCampoStruct child : node.getDefcampostruct())
				child.accept(this, param);

		return null;
	}

	//	class DefFuncion { String nombre;  List<Parametro> parametro;  Tipo tipo;  List<DefVariable> defvariable;  List<Sentencia> sentencia; }
	public Object visit(DefFuncion node, Object param) {

		// super.visit(node, param);

		if (node.getSentencia() != null)
			for (Sentencia child : node.getSentencia())
				child.accept(this, param);

		return null;
	}
	
//	class Asignacion { Expresion left;  Expresion right; }
	public Object visit(Asignacion node, Object param) {

		// super.visit(node, param);

		genera("#LINE");
		node.getLeft().accept(this, DIRECCION);
		node.getRight().accept(this, VALOR);
		genera("STORE");

		return null;
	}
	
//	class Condicional { Expresion expresion;  List<Sentencia> sentif;  List<Sentencia> sentelse; }
	public Object visit(Condicional node, Object param) {

		// super.visit(node, param);

		genera("#LINE");
		node.getExpresion().accept(this, VALOR);
		genera("JZ else");
			for(Sentencia s: node.getSentif())
				s.accept(this, param);
			genera("JMP final");
			
		genera("else:");
			for(Sentencia s: node.getSentelse())
				s.accept(this, param);
			
		genera("final:");

		return null;
	}
	
//	class Bucle { Expresion expresion;  List<Sentencia> sentencia; }
	public Object visit(Bucle node, Object param) {

		// super.visit(node, param);

		genera("#LINE");
		node.getExpresion().accept(this, VALOR);
		genera("JZ final");
			for(Sentencia s: node.getSentencia())
				s.accept(this, param);
		
		genera("final:");

		return null;
	}
	
//	class Read { Expresion expresion; }
	public Object visit(Read node, Object param) {

		// super.visit(node, param);

		genera("#LINE");
		node.getExpresion().accept(this, DIRECCION);
		genera("IN");
		genera("STORE"+node.getExpresion().getTipo().getSufijo());

		return null;
	}
	
//	class Return { Expresion expresion; }
	public Object visit(Return node, Object param) {

		// super.visit(node, param);

		genera("#LINE");
		node.getExpresion().accept(this, VALOR);

		return null;
	}
	
//	class LiteralInt { String valor; }
	public Object visit(LiteralInt node, Object param) {
		genera("#LINE");
		genera("PUSH "+node.getValor());
		
		return null;
	}
	
//	class LiteralReal { String valor; }
	public Object visit(LiteralReal node, Object param) {
		genera("#LINE");
		genera("PUSH "+node.getValor());
		
		return null;
	}
	
//	class Variable { String nombre; }
	public Object visit(Variable node, Object param) {
		genera("#LINE");
		
		genera("PUSHA "+node.getDefinicionVariable().getDireccion());
		
		if (param == "VALOR") 
			genera("LOAD"+node.getTipo().getSufijo());
		
		return null;
	}
	
//	class CallArray { Expresion variable;  Expresion acceso; }
	public Object visit(CallArray node, Object param) {

		// super.visit(node, param);

		genera("#LINE");
		node.getVariable().accept(this, DIRECCION);
		node.getAcceso().accept(this, VALOR);
		genera("PUSHi "+node.getVariable().getTipo().getSize());
		genera("MULi");
		genera("ADDi");
		genera("LOAD"+node.getTipo().getSufijo());

		return null;
	}
	
	//	class Cast { Tipo tipo;  Expresion expresion; }
	public Object visit(Cast node, Object param) {

		// super.visit(node, param);

		genera("#LINE");
		node.getExpresion().accept(this, DIRECCION);
		genera("LOAD"+node.getExpresion().getTipo().getSufijo());
		genera(node.getExpresion().getTipo().getSufijo()+"2"+node.getTipo().getSufijo());

		return null;
	}

	//	class Parametro { String nombre;  Tipo tipo; }
	public Object visit(Parametro node, Object param) {

		// super.visit(node, param);

		if (node.getTipo() != null)
			node.getTipo().accept(this, param);

		return null;
	}

	//	class DefCampoStruct { String nombre;  Tipo tipo; }
	public Object visit(DefCampoStruct node, Object param) {

		// super.visit(node, param);

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

		// super.visit(node, param);

		if (node.getTipo() != null)
			node.getTipo().accept(this, param);

		return null;
	}

	//	class CallFuncSent { String nombre;  List<Expresion> argumentos; }
	public Object visit(CallFuncSent node, Object param) {

		// super.visit(node, param);

		if (node.getArgumentos() != null)
			for (Expresion child : node.getArgumentos())
				child.accept(this, param);

		return null;
	}

	//	class Print { Expresion expresion; }
	public Object visit(Print node, Object param) {

		// super.visit(node, param);

		if (node.getExpresion() != null)
			node.getExpresion().accept(this, param);

		return null;
	}

	//	class Caracter { String valor; }
	public Object visit(Caracter node, Object param) {
		return null;
	}

	//	class ExpresionBinaria { Expresion left;  String operador;  Expresion right; }
	public Object visit(ExpresionBinaria node, Object param) {

		// super.visit(node, param);

		if (node.getLeft() != null)
			node.getLeft().accept(this, param);

		if (node.getRight() != null)
			node.getRight().accept(this, param);

		return null;
	}

	//	class Negacion { Expresion expresion; }
	public Object visit(Negacion node, Object param) {

		// super.visit(node, param);

		if (node.getExpresion() != null)
			node.getExpresion().accept(this, param);

		return null;
	}

	//	class CampoStruct { Expresion left;  String right; }
	public Object visit(CampoStruct node, Object param) {

		// super.visit(node, param);

		if (node.getLeft() != null)
			node.getLeft().accept(this, param);

		return null;
	}

	//	class CallFunc { String nombre;  List<Expresion> argumentos; }
	public Object visit(CallFunc node, Object param) {

		// super.visit(node, param);

		if (node.getArgumentos() != null)
			for (Expresion child : node.getArgumentos())
				child.accept(this, param);

		return null;
	}
	
	
	

	
	
	
	
	
	
	// M�todo auxiliar recomendado -------------
	private void genera(String instruccion) {
		writer.println(instruccion);
	}

	private PrintWriter writer;
	private String sourceFile;
}
