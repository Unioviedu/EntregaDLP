/**
 * @generated VGen 1.3.3
 */

package ast;

import visitor.*;

//	floatType:tipo -> 

public class FloatType extends AbstractTipo {

	@Override
	public Object accept(Visitor v, Object param) { 
		return v.visit(this, param);
	}
	
	@Override
	public String toString() {
		return "FloatType";
	}

	@Override
	public int getSize() {
		return 4;
	}

	@Override
	public String getSufijo() {
		return "f";
	}

}

