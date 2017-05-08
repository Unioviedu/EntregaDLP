package generacionDeCodigo;

import java.util.Collections;
import java.util.List;

import ast.*;
import visitor.*;

/** 
 * Clase encargada de asignar direcciones a las variables 
 */
public class GestionDeMemoria extends DefaultVisitor {

	/*
	 * Poner aqu� los visit necesarios.
	 * Si se ha usado VGen solo hay que copiarlos de la clase 'visitor/_PlantillaParaVisitors.txt'.
	 */

	public Object visit(Programa node, Object param) {

		int sumaTamañoVariables = 0;

		for (Definiciones def : node.getDefiniciones()) 		{
			if (def.getClass() == DefVariable.class) {
				DefVariable child = (DefVariable) def;
				child.setDireccion(sumaTamañoVariables);
				sumaTamañoVariables += child.getTipo().getSize();
			} else if (def.getClass() == DefFuncion.class) {
				DefFuncion child = (DefFuncion) def;
				child.accept(this, null);
			} else if (def.getClass() == DefStruct.class) {
				DefStruct child = (DefStruct) def;
				child.accept(this, null);
			}
		}
		
		return null;
	}
	
	public Object visit(DefFuncion node, Object param) {
		
		List<Parametro> parametros = node.getParametro();
		Collections.reverse(parametros);
		int sumaTamañoParametros = 0;
		for (Parametro p: parametros) {
			p.setDireccion(sumaTamañoParametros+4);
			sumaTamañoParametros += p.getTipo().getSize();
		}
		
		int sumaTamañoVariables = 0;
		for (DefVariable child: node.getDefvariable()) {
			child.setDireccion(sumaTamañoVariables-child.getTipo().getSize());
			sumaTamañoVariables -= child.getTipo().getSize();
		}
		
		
		return null;
	}
	
	public Object visit(DefStruct node, Object param) {
		
		int sumaTamañoCampos = 0;
		for (DefCampoStruct campo: node.getDefcampostruct()) {
			campo.setDireccion(sumaTamañoCampos);
			sumaTamañoCampos += campo.getTipo().getSize();
		}
		return null;
	}




}
