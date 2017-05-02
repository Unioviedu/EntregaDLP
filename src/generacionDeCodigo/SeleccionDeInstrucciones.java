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
		int retorno = node.getTipo() != null ? node.getTipo().getSize() : 0;
		int locales = 0;
		int parametros = 0;
		// super.visit(node, param);
		
		genera(node.getNombre()+":");

		for (DefVariable v : node.getDefvariable())
			locales += v.getTipo().getSize();
		for (Parametro p: node.getParametro())
			parametros += p.getTipo().getSize();

		genera("enter " + locales);
		if (node.getSentencia() != null)
			for (Sentencia child : node.getSentencia())
				child.accept(this, param);
		genera("ret "+retorno +","+ locales +","+ parametros);

		return null;
	}
	
//	class Asignacion { Expresion left;  Expresion right; }
	public Object visit(Asignacion node, Object param) {

		// super.visit(node, param);

		//genera("#LINE");
		node.getLeft().accept(this, DIRECCION);
		node.getRight().accept(this, VALOR);
		genera("STORE"+node.getLeft().getTipo().getSufijo());

		return null;
	}
	
	private int tagIF = 0;
	
//	class Condicional { Expresion expresion;  List<Sentencia> sentif;  List<Sentencia> sentelse; }
	public Object visit(Condicional node, Object param) {
		tagIF++;
		// super.visit(node, param);

		//genera("#LINE");
		node.getExpresion().accept(this, VALOR);
		genera("JZ else"+tagIF);
			for(Sentencia s: node.getSentif())
				s.accept(this, param);
			genera("JMP finalIF"+tagIF);
			
			genera("else"+tagIF+":");	
			if (node.getSentelse() != null) 
				for(Sentencia s: node.getSentelse())
					s.accept(this, param);
			
		genera("finalIF"+tagIF+":");
		
		return null;
	}
	
	private int tagBucle = 0;
	
//	class Bucle { Expresion expresion;  List<Sentencia> sentencia; }
	public Object visit(Bucle node, Object param) {
		tagBucle++;

		// super.visit(node, param);

		//genera("#LINE");
		genera("inicio"+tagBucle+":");
		node.getExpresion().accept(this, VALOR);
		genera("JZ finalBucle"+tagBucle);
			for(Sentencia s: node.getSentencia())
				s.accept(this, param);
			genera("JMP inicio"+tagBucle);
		genera("finalBucle"+tagBucle+":");
		
		return null;
	}
	
//	class Read { Expresion expresion; }
	public Object visit(Read node, Object param) {

		// super.visit(node, param);

		//genera("#LINE");
		node.getExpresion().accept(this, DIRECCION);
		genera("IN");
		genera("STORE"+node.getExpresion().getTipo().getSufijo());

		return null;
	}
	
//	class Return { Expresion expresion; }
	public Object visit(Return node, Object param) {

		// super.visit(node, param);

		//genera("#LINE");
		if (node.getExpresion() != null)
			node.getExpresion().accept(this, VALOR);

		return null;
	}
	
//	class LiteralInt { String valor; }
	public Object visit(LiteralInt node, Object param) {
		//genera("#LINE");
		genera("PUSH "+node.getValor());
		
		return null;
	}
	
//	class LiteralReal { String valor; }
	public Object visit(LiteralReal node, Object param) {
		//genera("#LINE");
		genera("PUSHf "+node.getValor());
		
		return null;
	}
	
//	class Caracter { String valor; }
	public Object visit(Caracter node, Object param) {
		genera("PUSHb "+(int)node.getValor());
		return null;
	}
	
//	class Variable { String nombre; }
	public Object visit(Variable node, Object param) {
		//genera("#LINE");
		int dir = node.getDefinicionVariable().getDireccion();
		
		if (node.getAmbito() == Ambito.PARAMETRO) {
			dir = node.getDefinicionVariable().getParametro().getDireccion();
			genera("PUSHA BP");
			genera("PUSH "+dir);
			genera("ADD");
		} 
		
		else if (node.getAmbito() == Ambito.GLOBAL)
			genera("PUSHA "+dir);
		
		else {
			genera("PUSHA BP");
			genera("PUSH "+dir);
			genera("ADD");
		}
		
		if ((int) param == VALOR) 
			genera("LOAD"+node.getTipo().getSufijo());
		
		return null;
	}
	
//	class CampoStruct { Expresion left;  String right; }
	public Object visit(CampoStruct node, Object param) {
		int dir = 0;
		//genera("#LINE");
		node.getLeft().accept(this, DIRECCION);
		StructType tipo = (StructType) node.getLeft().getTipo();
		for (DefCampoStruct c: tipo.getStruct().getDefcampostruct()) 
			if (c.getNombre().equals(node.getRight()))
				dir = c.getDireccion();
		genera("PUSH "+dir);
		genera("ADD"+node.getLeft().getTipo().getSufijo());
		
		if ((int) param == VALOR)
			genera("LOAD"+node.getTipo().getSufijo());
		
		return null;
	}
	
//	class CallArray { Expresion variable;  Expresion acceso; }
	public Object visit(CallArray node, Object param) {

		// super.visit(node, param);

		//genera("#LINE");
		node.getVariable().accept(this, DIRECCION);
		node.getAcceso().accept(this, VALOR);
		ArrayType array = (ArrayType) node.getVariable().getTipo();
		genera("PUSH "+array.getTipo().getSize());
		genera("MULi");
		genera("ADDi");
		
		if ((int) param == VALOR)
			genera("LOAD"+node.getTipo().getSufijo());

		return null;
	}
	
	//	class Cast { Tipo tipo;  Expresion expresion; }
	public Object visit(Cast node, Object param) {

		// super.visit(node, param);

		//genera("#LINE");
		node.getExpresion().accept(this, VALOR);
		genera(node.getExpresion().getTipo().getSufijo()+"2"+node.getTipo().getSufijo());

		return null;
	}
	
	//	class ExpresionBinaria { Expresion left;  String operador;  Expresion right; }
	public Object visit(ExpresionBinaria node, Object param) {

		// super.visit(node, param);

		//genera("#LINE");
		node.getLeft().accept(this, VALOR);
		node.getRight().accept(this, VALOR);
		
		String sufijo = node.getLeft().getTipo().getSufijo();
		switch(node.getOperador()) {
			case "+":
				genera("ADD"+sufijo);
				break;
			case "-":
				genera("SUB"+sufijo);
				break;
			case "*":
				genera("MUL"+sufijo);
				break;
			case "/":
				genera("DIV"+sufijo);
				break;
			case "&&":
				genera("AND");
				break;
			case "||":
				genera("OR");
				break;
			case "==":
				genera("EQ"+sufijo);
				break;
			case "<":
				genera("LT"+sufijo);
				break;
			case "<=":
				genera("LE"+sufijo);
				break;
			case ">":
				genera("GT"+sufijo);
				break;
			case ">=":
				genera("GE"+sufijo);
				break;
		}

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
		
		for (Expresion ex: node.getArgumentos()) 
			ex.accept(this, VALOR);

		genera("call "+node.getDefFuncionInvoca().getNombre());

		return null;
	}

	//	class Print { Expresion expresion; }
	public Object visit(Print node, Object param) {

		// super.visit(node, param);
		
		node.getExpresion().accept(this, VALOR);
		genera("OUT"+node.getExpresion().getTipo().getSufijo());

		return null;
	}

	//	class Negacion { Expresion expresion; }
	public Object visit(Negacion node, Object param) {

		// super.visit(node, param);
		node.getExpresion().accept(this, VALOR);
		genera("NOT");

		return null;
	}

	//	class CallFunc { String nombre;  List<Expresion> argumentos; }
	public Object visit(CallFunc node, Object param) {

		// super.visit(node, param);
		for (Expresion ex: node.getArgumentos()) 
			ex.accept(this, VALOR);

		genera("call "+node.getDefFuncionInvoca().getNombre());

		return null;
	}
	
	
	

	
	
	
	
	
	
	// M�todo auxiliar recomendado -------------
	private void genera(String instruccion) {
		writer.println(instruccion);
	}

	private PrintWriter writer;
	private String sourceFile;
}
