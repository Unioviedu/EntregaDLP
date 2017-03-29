package semantico;

import ast.*;
import main.*;
import visitor.*;

public class ComprobacionDeTipos extends DefaultVisitor {

	public ComprobacionDeTipos(GestorErrores gestor) {
		this.gestorErrores = gestor;
	}

	public Object visit(DefFuncion node, Object param) {

		super.visit(node, param);

		for (Parametro parametro : node.getParametro())
			predicado(isPrimitivo(parametro.getTipo()), "El parámetro "
					+ parametro.getNombre() + " no es primitivo",
					parametro.getStart());

		predicado(isPrimitivo(node.getTipo()),
				"El valor de retorno debe ser primitivo", node.getTipo()
						.getStart());

		for (Sentencia sentencia : node.getSentencia())
			sentencia.setDefFuncion(node);

		return null;
	}

	public Object visit(Asignacion node, Object param) {

		super.visit(node, param);
		
		predicado(node.getLeft().getTipo().getClass() == node.getRight().getTipo().getClass(),
				"Las dos partes de la expresion tienen que ser del mismo tipo "
				+node.getLeft(), node.getLeft().getStart());
		
		predicado(node.getLeft().getModificable(), "La parte izquierda tiene que ser modificable "
				+node.getLeft(), node.getLeft().getStart());

		return null;
	}
	
	public Object visit(Condicional node, Object param) {
		super.visit(node, param);
		
		predicado(node.getExpresion().getTipo().getClass() == IntType.class, 
				"La expresion debe ser de tipo entero "+node.getExpresion(), node.getExpresion().getStart());
		
		for (Sentencia sent: node.getSentif())
			sent.setDefFuncion(node.getDefFuncion());
		
		for(Sentencia sent: node.getSentelse())
			sent.setDefFuncion(node.getDefFuncion());
		
		return null;
	}
	
	public Object visit(Bucle node, Object param) {
		super.visit(node, param);
		
		predicado(node.getExpresion().getTipo().getClass() == IntType.class, 
				"La expresion debe ser de tipo entero "+node.getExpresion(), node.getExpresion().getStart());
		
		for (Sentencia sent: node.getSentencia())
			sent.setDefFuncion(node.getDefFuncion());
		
		return null;
	}
	
	public Object visit(Return node, Object param) {
		super.visit(node, param);
		
		predicado(node.getExpresion().getTipo().getClass() == node.getDefFuncion().getTipo().getClass(),
				"El tipo del retorno debe coincidir con el especificado "+node.getExpresion(), 
				node.getExpresion().getStart());
		
		return null;
	}
	
	public Object visit(Read node, Object param) {
		super.visit(node, param);
		
		predicado(isPrimitivo(node.getExpresion().getTipo()),
				"La expresion tiene que ser de tipo entero "+node.getExpresion(),
				node.getExpresion().getStart());
		
		return null;
	}
	
	public Object visit(ExpresionBinaria node, Object param) {
		super.visit(node, param);
		
		predicado(node.getLeft().getTipo().getClass() == node.getRight().getTipo().getClass(),
				"Las dos partes de la expresion deben ser del mismo tipo "+node.getLeft(),
				node.getLeft().getStart());
		
		node.setTipo(node.getLeft().getTipo());
		node.setModificable(false);
		
		return null;
	}
	
	public Object visit(Cast node, Object param) {
		super.visit(node, param);
		
		predicado(node.getTipo().getClass() != node.getExpresion().getTipo().getClass(),
				"No puedes castear un tipo a su mismo tipo",
				node.getStart());
		
		predicado(isPrimitivo(node.getTipo()),
				"El tipo a castear debe ser primitivo",
				node.getTipo().getStart());
		
		predicado(isPrimitivo(node.getExpresion().getTipo()),
				"El elemento que se quiere castear tiene que ser tipo primitivo",
				node.getExpresion().getStart());
		
		node.setModificable(false);
		
		return null;
	}
	
	public Object visit(CallFunc node, Object param) {
		super.visit(node, param);
		
		predicado(node.getArgumentos().size() == node.getDefinicionFuncion().getParametro().size(),
				"El numero de parametros tiene que ser igual que el de la funcion",
				node.getStart());
		
		int cont = 0;
		for (Expresion p: node.getArgumentos())
			predicado(p.getTipo().getClass() == node.getDefinicionFuncion().getParametro().get(cont).getTipo().getClass(),
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
	private void predicado(boolean condicion, String mensajeError,
			Position posicionError) {
		if (!condicion)
			gestorErrores.error("Comprobaci�n de tipos", mensajeError,
					posicionError);
	}

	private boolean isPrimitivo(Tipo tipo) {
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
