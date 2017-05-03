package semantico;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ast.Ambito;
import ast.CallFunc;
import ast.CallFuncSent;
import ast.DefCampoStruct;
import ast.DefFuncion;
import ast.DefVariable;
import ast.Parametro;
import ast.Position;
import ast.Struct;
import ast.StructType;
import ast.Variable;
import main.GestorErrores;
import visitor.DefaultVisitor;


public class Identificacion extends DefaultVisitor {

	public Identificacion(GestorErrores gestor) {
		this.gestorErrores = gestor;
	}

	public Object visit(DefVariable node, Object param) {
		super.visit(node, param);
		
		DefVariable definicion = variables.getFromTop(node.getNombre());
		predicado(definicion == null, "Variable ya definida: "+ node.getNombre(), node.getStart());
		variables.put(node.getNombre(), node);
		
		return null;
	}
	
	public Object visit(Parametro node, Object param) {
		super.visit(node, param);
		
		DefVariable definicion = variables.getFromTop(node.getNombre());
		predicado(definicion == null, "Parametro ya definido: "+ node.getNombre(), node.getStart());
		DefVariable variable = new DefVariable(node.getNombre(), node.getTipo());
		variable.setParametro(node);
		variables.put(node.getNombre(), variable);
		
		return null;
	}
	
	public Object visit(Variable node, Object param) {
		super.visit(node, param);
		
		DefVariable definicion = variables.getFromAny(node.getNombre());
		
		if (variables.getFromTop(node.getNombre()) != null)
			node.setAmbito(Ambito.LOCAL);
		else if (definicion != null)
			node.setAmbito(Ambito.GLOBAL);
		
		predicado(definicion != null, "Variable no definida: "+node.getNombre(), node.getStart());
		node.setDefinicionVariable(definicion);
		
		if (node.getDefinicionVariable().getParametro() != null)
			node.setAmbito(Ambito.PARAMETRO);
		
		return null;
	}
	
	public Object visit(DefFuncion node, Object param) {
		variables.set();
		
		DefFuncion definicion = funciones.get(node.getNombre());
		predicado(definicion == null, "Funcion ya definida: " + node.getNombre(), node.getStart());
		funciones.put(node.getNombre(), node);
		
		super.visit(node, param);
		
		variables.reset();
		return null;
	}
	
	public Object visit(CallFunc node, Object param) {
		super.visit(node, param);
		
		DefFuncion definicion = funciones.get(node.getNombre());
		predicado(definicion != null, "Funcion no definida: " + node.getNombre(), node.getStart());
		node.setDefFuncionInvoca(definicion); // Enlazar referencia con definición
	
		return null;
	}
	
	public Object visit(CallFuncSent node, Object param) {
		super.visit(node, param);
		
		DefFuncion definicion = funciones.get(node.getNombre());
		predicado(definicion != null, "Funcion no definida: " + node.getNombre(), node.getStart());
		node.setDefFuncionInvoca(definicion); // Enlazar referencia con definición
		
		return null;
	}

	public Object visit(Struct node, Object param) {
		super.visit(node, param);
		
		Struct definicion = structs.get(node.getNombre());
		predicado(definicion == null, "Struct ya definido: " + node.getNombre(), node.getStart());
		structs.put(node.getNombre(), node);
		
		List<String> atributos = new ArrayList<String>();
		for (DefCampoStruct a: node.getDefcampostruct()) {
			predicado(!atributos.contains(a.getNombre()), "Campo ya definido: "+ a.getNombre(), a.getStart());
			atributos.add(a.getNombre());
		}
		
		return null;
	}
	
	public Object visit(StructType node, Object param) {
		super.visit(node, param);
		
		Struct ident = structs.get(node.getNombre());
		predicado(ident != null, "Struct no definido: " + node.getNombre(), node.getStart());
		node.setStruct(ident); // Enlazar referencia con definición
		
		return null;
	}
	
	/**
	 * M�todo auxiliar opcional para ayudar a implementar los predicados de la Gram�tica Atribuida.
	 * 
	 * Ejemplo de uso:
	 * 	predicado(variables.get(nombre), "La variable no ha sido definida", expr.getStart());
	 * 	predicado(variables.get(nombre), "La variable no ha sido definida", null);
	 * 
	 * NOTA: El m�todo getStart() indica la linea/columna del fichero fuente de donde se ley� el nodo.
	 * Si se usa VGen dicho m�todo ser� generado en todos los nodos AST. Si no se quiere usar getStart() se puede pasar null.
	 * 
	 * @param condicion Debe cumplirse para que no se produzca un error
	 * @param mensajeError Se imprime si no se cumple la condici�n
	 * @param posicionError Fila y columna del fichero donde se ha producido el error. Es opcional (acepta null)
	 */
	private void predicado(boolean condicion, String mensajeError, Position posicionError) {
		if (!condicion)
			gestorErrores.error("Identificaci�n", mensajeError, posicionError);
	}


	private GestorErrores gestorErrores;
	private Map<String, DefFuncion> funciones = new HashMap<String, DefFuncion>();
	private Map<String, Struct> structs = new HashMap<String, Struct>();
	ContextMap<String, DefVariable> variables = new ContextMap<String, DefVariable>();
}
