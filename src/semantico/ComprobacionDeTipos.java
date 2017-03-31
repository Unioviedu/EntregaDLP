package semantico;

import ast.*;
import main.*;
import visitor.*;

public class ComprobacionDeTipos extends DefaultVisitor {

	public ComprobacionDeTipos(GestorErrores gestor) {
		this.gestorErrores = gestor;
	}

	public Object visit(DefFuncion node, Object param) {
		for (Sentencia sentencia : node.getSentencia())
			sentencia.setDefFuncion(node);
		super.visit(node, param);
			

		if (node.getTipo() != null)
			predicado(isPrimitivo(node.getTipo()),
				"El valor de retorno debe ser primitivo, y es "+node.getTipo(), 
				node.getStart());

		return null;
	}
	
	public Object visit(Parametro node, Object param) {
		super.visit(node, param);
		
		predicado(isPrimitivo(node.getTipo()), 
				"El parámetro "+node.getNombre()+" no es primitivo, es "+node.getTipo(),
				node.getStart());
		
		return null;
	}
	
	public Object visit (CallFuncSent node, Object param) {
		super.visit(node, param);
		
		if (predicado(node.getArgumentos().size() == node.getDefFuncionInvoca().getParametro().size(),
				"El numero de argumentos "+node.getArgumentos().size()+
				" tiene que ser igual que los de la funcion "+node.getDefFuncionInvoca().getParametro().size(),
				node.getStart()))
			return null; //si no tienen el mismo numero, no hace falta comparar tipos
		
		int cont = 0;
		for (Expresion p: node.getArgumentos()) {
			Tipo callFunc = p.getTipo();
			Tipo funcion = node.getDefFuncionInvoca().getParametro().get(cont).getTipo();
			predicado(callFunc.getClass() == funcion.getClass(),
			"El parametro "+cont+" tienen que ser del mismo tipo que el de su funcion y son: "+callFunc+", "+funcion,
			node.getStart());
			
			cont++;
		}
		
		return null;
	}

	public Object visit(Asignacion node, Object param) {

		super.visit(node, param);
		
		predicado(mismoTipo(node.getLeft(), node.getRight()),
				"Las dos partes de la expresion tienen que ser del mismo tipo y son: "
				+"izquierda: "+node.getLeft().getTipo()+ " derecha: "+node.getRight().getTipo(), 
				node.getLeft().getStart());
		
		predicado(node.getLeft().getModificable(), 
				"La parte izquierda tiene que ser modificable ",
				node.getLeft().getStart());

		return null;
	}
	
	public Object visit(Condicional node, Object param) {
		super.visit(node, param);
		
		predicado(isIntType(node.getExpresion()), 
				"La expresion del if debe ser de tipo entero, y es "+node.getExpresion().getTipo(), 
				node.getExpresion().getStart());
		
		for (Sentencia sent: node.getSentif())
			sent.setDefFuncion(node.getDefFuncion());
		
		if (node.getSentelse() != null)
			for(Sentencia sent: node.getSentelse())
				sent.setDefFuncion(node.getDefFuncion());
		
		return null;
	}
	
	public Object visit(Bucle node, Object param) {
		super.visit(node, param);
		
		predicado(isIntType(node.getExpresion()), 
				"La expresion del bucle debe ser de tipo entero, y es "+node.getExpresion().getTipo(), 
				node.getExpresion().getStart());
		
		for (Sentencia sent: node.getSentencia())
			sent.setDefFuncion(node.getDefFuncion());
		
		return null;
	}
	
	public Object visit(Read node, Object param) {
		super.visit(node, param);
		
		predicado(isPrimitivo(node.getExpresion().getTipo()),
				"La expresion de lectura tiene que ser de tipo simple, y es "+node.getExpresion().getTipo(),
				node.getExpresion().getStart());
		
		return null;
	}
	
	public Object visit(Return node, Object param) {
		super.visit(node, param);
		
		predicado(mismoTipo(node.getExpresion(), node.getDefFuncion().getTipo()),
				"El tipo del retorno debe coincidir con el especificado",
				node.getStart());
		
		return null;
	}
	
	public Object visit(Print node, Object param) {
		super.visit(node, param);
		
		if (node.getExpresion() != null)
			predicado(isPrimitivo(node.getExpresion().getTipo()),
					"El tipo a imprimir debe ser simple, y es "+node.getExpresion().getTipo(),
					node.getStart());
		
		return null;
	}
	
	public Object visit(LiteralInt node, Object param) {
		
		node.setTipo(new IntType());
		node.setModificable(false);
		
		return null;
	}
	
	public Object visit(LiteralReal node, Object param) {
		
		node.setTipo(new FloatType());
		node.setModificable(false);
		
		return null;
	}
	
	public Object visit(Caracter node, Object param) {
		
		node.setTipo(new CharType());
		node.setModificable(false);
		
		return null;
	}
	
	public Object visit(Variable node, Object param) {
		
		node.setTipo(node.getDefinicionVariable().getTipo());
		node.setModificable(false);
		
		return null;
	}
	
	public Object visit(Cast node, Object param) {
		super.visit(node, param);
		
		predicado(node.getTipo().getClass() != node.getExpresion().getTipo().getClass(),
				"No puedes castear un tipo a su mismo tipo",
				node.getStart());
		
		predicado(isPrimitivo(node.getTipo()),
				"El tipo a castear debe ser simple, y es "+node.getTipo(),
				node.getTipo().getStart());
		
		predicado(isPrimitivo(node.getExpresion().getTipo()),
				"El elemento que se quiere castear tiene que ser tipo simple, y es "+node.getExpresion().getTipo(),
				node.getExpresion().getStart());
		
		node.setModificable(false);
		
		return null;
	}
	
	public Object visit(Negacion node, Object param) {
		
		predicado(isIntType(node.getExpresion()),
				"La expresion debe ser de tipo entero, y es "+node.getExpresion().getTipo(),
				node.getExpresion().getStart());
		
		node.setTipo(node.getExpresion().getTipo());
		node.setModificable(false);
		
		return null;
	}
	
	public Object visit(CallArray node, Object param) {
		super.visit(node, param);
		
		if ( !predicado(node.getVariable().getTipo().getClass() == ArrayType.class,
				"La variable tiene que ser de tipo Array, y es "+node.getVariable().getTipo(),
				node.getVariable().getStart()) ) {
			
			ArrayType tipo = (ArrayType) node.getVariable().getTipo();
			
			node.setTipo(tipo.getTipo());
		}
		
		predicado(isIntType(node.getAcceso()),
				"La expresion de acceso tiene que ser de tipo entero, y es "+node.getAcceso().getTipo(),
				node.getAcceso().getStart());
		
		return null;
	}
	
	public Object visit(ExpresionBinaria node, Object param) {
		super.visit(node, param);
		
		predicado(node.getLeft().getTipo().getClass() == node.getRight().getTipo().getClass(),
				"Las dos partes de la expresion deben ser del mismo tipo, y son izquierda: "+node.getLeft().getTipo()
				+" derecha: "+node.getRight().getTipo(),
				node.getLeft().getStart());
		
		node.setTipo(node.getLeft().getTipo());
		node.setModificable(false);
		
		return null;
	}
	
	public Object visit(CampoStruct node, Object param) {
		super.visit(node, param);
		
		if ( !predicado(node.getLeft().getTipo().getClass() == StructType.class,
				"La parte izquierda debe ser de tipo Struct, y es "+node.getLeft().getTipo(),
				node.getLeft().getStart()) ) {
		
			StructType tipo = (StructType) node.getLeft().getTipo();
			for (DefCampoStruct c: tipo.getStruct().getDefcampostruct()) {
				if (c.getNombre().equals(node.getRight()))
					node.setTipo(c.getTipo());
			}
		}
		
		predicado(node.getTipo() != null,
				"El campo no esta definido en el Struct",
				node.getStart());
		
		return null;
	}
	
	public Object visit(CallFunc node, Object param) {
		super.visit(node, param);
		
		predicado(node.getArgumentos().size() == node.getDefFuncionInvoca().getParametro().size(),
				"El numero de parametros tiene que ser igual que el de la funcion",
				node.getStart());
		
		int cont = 0;
		for (Expresion p: node.getArgumentos())
			predicado(p.getTipo().getClass() == node.getDefFuncionInvoca().getParametro().get(cont).getTipo().getClass(),
			"Los parametros tienen que ser del mismo tipo",
			node.getStart());
		return null;
	}
	
	

	/**
	 * M�todo auxiliar opcional para ayudar a implementar los predicados de la
	 * Gram�tica Atribuida.
	 * 
	 * Ejemplo de uso (suponiendo implementado el m�todo "esPrimitivo"):
	 * predicado(esPrimitivo(expr.tipo),
	 * "La expresi�n debe ser de un tipo primitivo", expr.getStart());
	 * predicado(esPrimitivo(expr.tipo),
	 * "La expresi�n debe ser de un tipo primitivo", null);
	 * 
	 * NOTA: El m�todo getStart() indica la linea/columna del fichero fuente de
	 * donde se ley� el nodo. Si se usa VGen dicho m�todo ser� generado en todos
	 * los nodos AST. Si no se quiere usar getStart() se puede pasar null.
	 * 
	 * @param condicion
	 *            Debe cumplirse para que no se produzca un error
	 * @param mensajeError
	 *            Se imprime si no se cumple la condici�n
	 * @param posicionError
	 *            Fila y columna del fichero donde se ha producido el error. Es
	 *            opcional (acepta null)
	 */
	private boolean predicado(boolean condicion, String mensajeError,
			Position posicionError) {
		if (!condicion)
			gestorErrores.error("Comprobaci�n de tipos", mensajeError,
					posicionError);
		return !condicion;
	}
	
	private boolean mismoTipo(Expresion exp1, Expresion exp2) {
		return exp1.getTipo().getClass() == exp2.getTipo().getClass();
	}
	
	private boolean mismoTipo(Expresion e1, Tipo t2) {
		if (e1 == null && t2 == null)
			return true;
		if (e1 == null)
			return false;
		if (t2 == null)
			return false;
		return e1.getClass() == t2.getClass();
	}
	
	private boolean isIntType(Expresion exp) {
		return exp.getTipo().getClass() == IntType.class;
	}
	
	private boolean isPrimitivo(Tipo tipo) {
		if (tipo == null)
			return false;
		if (tipo.getClass() == IntType.class)
			return true;
		if (tipo.getClass() == FloatType.class)
			return true;
		if (tipo.getClass() == CharType.class)
			return true;
		return false;
	}

	private GestorErrores gestorErrores;
}
