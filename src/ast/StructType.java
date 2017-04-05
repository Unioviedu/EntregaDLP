/**
 * @generated VGen 1.3.3
 */

package ast;

import visitor.*;

//	structType:tipo -> nombre:String

public class StructType extends AbstractTipo {

	public StructType(String nombre) {
		this.nombre = nombre;
	}

	public StructType(Object nombre) {
		this.nombre = (nombre instanceof Token) ? ((Token)nombre).getLexeme() : (String) nombre;

		searchForPositions(nombre);	// Obtener linea/columna a partir de los hijos
	}

	public String getNombre() {
		return nombre;
	}
	
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	
	public void setStruct(Struct struct) {
		this.struct = struct;
	}
	
	public Struct getStruct() {
		return struct;
	}

	@Override
	public Object accept(Visitor v, Object param) { 
		return v.visit(this, param);
	}
	
	@Override
	public String toString() {
		return "StructType";
	}
	
	@Override
	public int getSize() {
		int tamaño = 0;
		for (DefCampoStruct d: struct.getDefcampostruct())
			tamaño += d.getTipo().getSize();
		return tamaño;
	}

	private String nombre;
	private Struct struct;
}

