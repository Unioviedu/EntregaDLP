/**
 * @generated VGen 1.3.3
 */

package ast;

import java.util.*;
import visitor.*;

//	struct:definiciones -> nombre:String  defcampostruct:defCampoStruct*

public class DefStruct extends AbstractDefiniciones {

	public DefStruct(String nombre, List<DefCampoStruct> defcampostruct) {
		this.nombre = nombre;
		this.defcampostruct = defcampostruct;

		searchForPositions(defcampostruct);	// Obtener linea/columna a partir de los hijos
	}

	@SuppressWarnings("unchecked")
	public DefStruct(Object nombre, Object defcampostruct) {
		this.nombre = (nombre instanceof Token) ? ((Token)nombre).getLexeme() : (String) nombre;
		this.defcampostruct = (List<DefCampoStruct>) defcampostruct;

		searchForPositions(nombre, defcampostruct);	// Obtener linea/columna a partir de los hijos
	}

	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public List<DefCampoStruct> getDefcampostruct() {
		return defcampostruct;
	}
	public void setDefcampostruct(List<DefCampoStruct> defcampostruct) {
		this.defcampostruct = defcampostruct;
	}

	@Override
	public Object accept(Visitor v, Object param) { 
		return v.visit(this, param);
	}

	private String nombre;
	private List<DefCampoStruct> defcampostruct;
}

