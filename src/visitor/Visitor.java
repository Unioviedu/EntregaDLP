/**
 * @generated VGen 1.3.3
 */

package visitor;

import ast.*;

public interface Visitor {
	public Object visit(Programa node, Object param);
	public Object visit(DefVariable node, Object param);
	public Object visit(DefStruct node, Object param);
	public Object visit(DefFuncion node, Object param);
	public Object visit(Parametro node, Object param);
	public Object visit(DefCampoStruct node, Object param);
	public Object visit(IntType node, Object param);
	public Object visit(RealType node, Object param);
	public Object visit(FloatType node, Object param);
	public Object visit(CharType node, Object param);
	public Object visit(StructType node, Object param);
	public Object visit(ArrayType node, Object param);
	public Object visit(Asignacion node, Object param);
	public Object visit(InvocaFuncSent node, Object param);
	public Object visit(Condicional node, Object param);
	public Object visit(Bucle node, Object param);
	public Object visit(Read node, Object param);
	public Object visit(Return node, Object param);
	public Object visit(Print node, Object param);
	public Object visit(LiteralInt node, Object param);
	public Object visit(LiteralReal node, Object param);
	public Object visit(Caracter node, Object param);
	public Object visit(Variable node, Object param);
	public Object visit(Cast node, Object param);
	public Object visit(ExpresionBinaria node, Object param);
	public Object visit(Negacion node, Object param);
	public Object visit(AccesoArray node, Object param);
	public Object visit(AccesoCampoStruct node, Object param);
	public Object visit(InvocaFunc node, Object param);
}
