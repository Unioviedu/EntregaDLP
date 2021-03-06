/**
 * @generated VGen 1.3.3
 */

package ast;

import visitor.*;

//	charType:tipo -> 

public class CharType extends AbstractTipo {

	@Override
	public Object accept(Visitor v, Object param) { 
		return v.visit(this, param);
	}
	
	@Override
	public String toString() {
		return "CharType";
	}

	@Override
	public int getSize() {
		return 1;
	}

	@Override
	public String getSufijo() {
		return "b";
	}

}

