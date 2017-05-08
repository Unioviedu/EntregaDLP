//@author Ra�l Izquierdo

/* No es necesario modificar esta secci�n ------------------ */
%{
package sintactico;

import java.io.*;
import java.util.*;
import ast.*;
import main.*;
%}

/* Precedencias aqu� --------------------------------------- */
%left 'AND' 'OR' '!' 
%left '>' '<' 'MENORIGUAL' 'MAYORIGUAL' 'NOTIGUAL' 'IGUAL'
%left '+' '-'
%left '*' '/'
%right '.' '['

%%

/* Añadir las reglas en esta secci�n ----------------------- */

programa: definiciones 			  		{raiz = new Programa($1);		}
    
//--------
definiciones: 					  		{$$ = new ArrayList();			}
	| definiciones definicion	  		{$$ = $1; ((List)$1).add($2);	}

definicion: defVariable 			  	{$$ = $1;	}
	| defStruct 					  	{$$ = $1;	}
	| defFuncion 				  		{$$ = $1;	}
	
//VARIABLES--------

defVariables: 						  		{$$ = new ArrayList();			}
	| defVariables defVariable		  		{$$ = $1; ((List)$1).add($2);	}
	
defVariable: 'VAR' 'IDENT' ':' tipo ';'  	{$$ = new DefVariable($2,$4);		}   	
    
tipo: 'INT'							  	{$$ = new IntType();			}	
    | 'REAL' 						  	{$$ = new RealType();			}
    | 'FLOAT'						  	{$$ = new FloatType();			}
    | 'CHAR'						  	{$$ = new CharType();			}
    | 'IDENT'						  	{$$ = new StructType($1);		}
    | '[' 'LITERALINT' ']' tipo		  	{$$ = new ArrayType ($2, $4);	}

//STRUCTS----------

defStruct: 'STRUCT' 'IDENT' '{' defCamposStruct '}' ';'  		{$$ = new DefStruct ($2, $4);		}

defCamposStruct: 						  				{$$ = new ArrayList();			}
	| defCamposStruct defCampoStruct					{$$ = $1; ((List)$$).add($2);	}

defCampoStruct: 'IDENT' ':' tipo ';' 				  	{$$ = new DefCampoStruct($1,$3);}
    
//FUNCIONES--------

defFuncion: 'IDENT' '('paramsFuncOpt')' valorRetorno '{' defVariables sentencias '}'		{$$ = new DefFuncion($1,$3,$5,$7,$8);	}

valorRetorno:		{$$ = null;	}	
	| ':' tipo		{$$ = $2;	}

paramsFunc: parametro				  	{$$ = new ArrayList(); ((List)$$).add($1); 	}
	| paramsFunc ',' parametro		   	{$$ = $1; ((List)$$).add($3);				}

paramsFuncOpt: 						  	{$$ = new ArrayList();						}
	| paramsFunc					  	{$$ = $1;									}

parametro: 'IDENT' ':' tipo 		    {$$ = new Parametro($1, $3);					}
	
//SENTENCIAS--------

sentencias: 						  		{$$ = new ArrayList();			}
	| sentencias sentencia  		  		{$$ = $1; ((List)$1).add($2);	}
	
sentencia: 'READ' expr ';' 								  					{$$ = new Read($2);					}
	| 'RETURN' exprOpt ';' 								  					{$$ = new Return($2);				}
	| 'IDENT' '(' listaParamOpt ')' ';'		   								{$$ = new InvocaFuncSent($1, $3);	}
	| sentenciaPrint
	| expr '=' expr ';'													  	{$$ = new Asignacion($1, $3);		}
	| 'IF' '(' exprOpt ')' '{' sentencias '}' elseOpt	    				{$$ = new Condicional($3,$6,$8);	}
	| 'WHILE' '(' exprOpt ')' '{' sentencias '}'						    {$$ = new Bucle($3,$6);				}	

elseOpt:							{$$ = null; }
	| 'ELSE' '{' sentencias '}'		{$$ = $3; 	} 				
	
sentenciaPrint: 'PRINT' expr ';'   	{$$ = new Print($2,"");	}
	| 'PRINTSP' expr ';'		   	{$$ = new Print($2," ");	}
	| 'PRINTLN' exprOpt ';'		   	{$$ = new Print($2,"\n");	}

exprOpt: 						   {$$ = null;				}
	| expr 						   {$$ = $1;				}
	
//EXPRESIONES--------

expr: 'LITERALINT' 						   {$$ = new LiteralInt($1);	}
	| 'LITERALREAL'						   {$$ = new LiteralReal($1);	}
	| 'CHARACTER'						   {$$ = new Caracter($1);		}
	| 'CAST' '<' tipo '>' '(' expr ')'	   {$$ = new Cast($3, $6);		}
	| 'IDENT'							   {$$ = new Variable($1);		}
	| exprBinaria 						   {$$ = $1;					}
	| '(' exprBinaria ')'				   {$$ = $2;					}
	| '!' expr 							   {$$ = new Negacion($2);		}
	| expr '.' 'IDENT' 					   {$$ = new AccesoCampoStruct($1,$3);} 
	| expr '[' expr ']'		   		   	   {$$ = new AccesoArray($1, $3);	}
	| invocaFunc 						   {$$ = $1;					}

exprBinaria: expr '*' expr 		  	{$$ = new ExpresionBinaria($1,"*",$3);		}
	| expr '+' expr 			  	{$$ = new ExpresionBinaria($1,"+",$3);		}		
	| expr '/' expr 			  	{$$ = new ExpresionBinaria($1,"/",$3);		}		
	| expr '-' expr 			  	{$$ = new ExpresionBinaria($1,"-",$3);		}		
	| expr 'OR' expr 			  	{$$ = new ExpresionBinaria($1,"||",$3);		}		
	| expr 'AND' expr 			  	{$$ = new ExpresionBinaria($1,"&&",$3);		}		
	| expr '<' expr 			  	{$$ = new ExpresionBinaria($1,"<",$3);		}		
	| expr '>' expr 			  	{$$ = new ExpresionBinaria($1,">",$3);		}		
	| expr 'MAYORIGUAL' expr 	  	{$$ = new ExpresionBinaria($1,">=",$3);		}		
	| expr 'MENORIGUAL' expr 	  	{$$ = new ExpresionBinaria($1,"<=",$3);		}			
	| expr 'NOTIGUAL' expr 		  	{$$ = new ExpresionBinaria($1,"!=",$3);		}		
	| expr 'IGUAL' expr 		  	{$$ = new ExpresionBinaria($1,"==",$3);		}		

invocaFunc: 'IDENT' '(' listaParamOpt ')'   	{$$ = new InvocaFunc($1, $3);	}

listaParam: expr 				  	{$$ = new ArrayList(); ((List)$$).add($1); 	}
	| listaParam ',' expr 		  	{$$ = $1; ((List)$$).add($3);				}
	
listaParamOpt: 					   {$$ = new ArrayList();	}
	| listaParam 				   {$$ = $1;				}

	
%%
/* No es necesario modificar esta secci�n ------------------ */

public Parser(Yylex lex, GestorErrores gestor, boolean debug) {
	this(debug);
	this.lex = lex;
	this.gestor = gestor;
}

//M�todos de acceso para el main -------------
public int parse() { return yyparse(); }
public AST getAST() { return raiz; }

//Funciones requeridas por Yacc --------------
void yyerror(String msg) {
	Token lastToken = (Token) yylval;
	gestor.error("Sintactico", "Token = " + lastToken.getToken() + ", lexema = \"" + lastToken.getLexeme() + "\". " + msg, lastToken.getStart());
}

int yylex() {
	try {
		int token = lex.yylex();
		yylval = new Token(token, lex.lexeme(), lex.line(), lex.column());
		return token;
	} catch (IOException e) {
		return -1;
	}
}

private Yylex lex;
private GestorErrores gestor;
private AST raiz;
