//### This file created by BYACC 1.8(/Java extension  1.14)
//### Java capabilities added 7 Jan 97, Bob Jamison
//### Updated : 27 Nov 97  -- Bob Jamison, Joe Nieten
//###           01 Jan 98  -- Bob Jamison -- fixed generic semantic constructor
//###           01 Jun 99  -- Bob Jamison -- added Runnable support
//###           06 Aug 00  -- Bob Jamison -- made state variables class-global
//###           03 Jan 01  -- Bob Jamison -- improved flags, tracing
//###           16 May 01  -- Bob Jamison -- added custom stack sizing
//###           04 Mar 02  -- Yuval Oren  -- improved java performance, added options
//###           14 Mar 02  -- Tomas Hurka -- -d support, static initializer workaround
//### Please send bug reports to tom@hukatronic.cz
//### static char yysccsid[] = "@(#)yaccpar	1.8 (Berkeley) 01/20/90";






//#line 5 "sintac.y"
package sintactico;

import java.io.*;
import java.util.*;
import ast.*;
import main.*;
//#line 24 "Parser.java"




public class Parser
{

boolean yydebug;        //do I want debug output?
int yynerrs;            //number of errors so far
int yyerrflag;          //was there an error?
int yychar;             //the current working character

//########## MESSAGES ##########
//###############################################################
// method: debug
//###############################################################
void debug(String msg)
{
  if (yydebug)
    System.out.println(msg);
}

//########## STATE STACK ##########
final static int YYSTACKSIZE = 500;  //maximum stack size
int statestk[] = new int[YYSTACKSIZE]; //state stack
int stateptr;
int stateptrmax;                     //highest index of stackptr
int statemax;                        //state when highest index reached
//###############################################################
// methods: state stack push,pop,drop,peek
//###############################################################
final void state_push(int state)
{
  try {
		stateptr++;
		statestk[stateptr]=state;
	 }
	 catch (ArrayIndexOutOfBoundsException e) {
     int oldsize = statestk.length;
     int newsize = oldsize * 2;
     int[] newstack = new int[newsize];
     System.arraycopy(statestk,0,newstack,0,oldsize);
     statestk = newstack;
     statestk[stateptr]=state;
  }
}
final int state_pop()
{
  return statestk[stateptr--];
}
final void state_drop(int cnt)
{
  stateptr -= cnt; 
}
final int state_peek(int relative)
{
  return statestk[stateptr-relative];
}
//###############################################################
// method: init_stacks : allocate and prepare stacks
//###############################################################
final boolean init_stacks()
{
  stateptr = -1;
  val_init();
  return true;
}
//###############################################################
// method: dump_stacks : show n levels of the stacks
//###############################################################
void dump_stacks(int count)
{
int i;
  System.out.println("=index==state====value=     s:"+stateptr+"  v:"+valptr);
  for (i=0;i<count;i++)
    System.out.println(" "+i+"    "+statestk[i]+"      "+valstk[i]);
  System.out.println("======================");
}


//########## SEMANTIC VALUES ##########
//## **user defined:Object
String   yytext;//user variable to return contextual strings
Object yyval; //used to return semantic vals from action routines
Object yylval;//the 'lval' (result) I got from yylex()
Object valstk[] = new Object[YYSTACKSIZE];
int valptr;
//###############################################################
// methods: value stack push,pop,drop,peek.
//###############################################################
final void val_init()
{
  yyval=new Object();
  yylval=new Object();
  valptr=-1;
}
final void val_push(Object val)
{
  try {
    valptr++;
    valstk[valptr]=val;
  }
  catch (ArrayIndexOutOfBoundsException e) {
    int oldsize = valstk.length;
    int newsize = oldsize*2;
    Object[] newstack = new Object[newsize];
    System.arraycopy(valstk,0,newstack,0,oldsize);
    valstk = newstack;
    valstk[valptr]=val;
  }
}
final Object val_pop()
{
  return valstk[valptr--];
}
final void val_drop(int cnt)
{
  valptr -= cnt;
}
final Object val_peek(int relative)
{
  return valstk[valptr-relative];
}
//#### end semantic value section ####
public final static short AND=257;
public final static short OR=258;
public final static short MENORIGUAL=259;
public final static short MAYORIGUAL=260;
public final static short NOTIGUAL=261;
public final static short IGUAL=262;
public final static short VAR=263;
public final static short IDENT=264;
public final static short INT=265;
public final static short REAL=266;
public final static short FLOAT=267;
public final static short CHAR=268;
public final static short LITERALINT=269;
public final static short STRUCT=270;
public final static short READ=271;
public final static short RETURN=272;
public final static short IF=273;
public final static short WHILE=274;
public final static short ELSE=275;
public final static short PRINT=276;
public final static short PRINTSP=277;
public final static short PRINTLN=278;
public final static short LITERALREAL=279;
public final static short CHARACTER=280;
public final static short CAST=281;
public final static short YYERRCODE=256;
final static short yylhs[] = {                           -1,
    0,    1,    1,    2,    2,    2,    6,    6,    3,    7,
    7,    7,    7,    7,    7,    4,    8,    8,    9,    5,
   11,   11,   13,   13,   10,   10,   14,   12,   12,   15,
   15,   15,   15,   15,   15,   15,   20,   20,   19,   19,
   19,   17,   17,   16,   16,   16,   16,   16,   16,   16,
   16,   16,   16,   16,   21,   21,   21,   21,   21,   21,
   21,   21,   21,   21,   21,   21,   18,   23,   23,   22,
   22,
};
final static short yylen[] = {                            2,
    1,    0,    2,    1,    1,    1,    0,    2,    5,    1,
    1,    1,    1,    1,    4,    6,    0,    2,    4,    9,
    0,    2,    1,    3,    0,    1,    3,    0,    2,    3,
    3,    2,    1,    4,    8,    7,    0,    4,    3,    3,
    3,    0,    1,    1,    1,    1,    7,    1,    1,    3,
    2,    3,    4,    1,    3,    3,    3,    3,    3,    3,
    3,    3,    3,    3,    3,    3,    4,    1,    3,    0,
    1,
};
final static short yydefred[] = {                         2,
    0,    0,    0,    0,    0,    3,    4,    5,    6,    0,
    0,    0,    0,    0,    0,    0,   23,   17,    0,   14,
   10,   11,   12,   13,    0,    0,    0,    0,    0,    0,
    9,   27,    0,    0,   24,    0,    0,   18,    0,   22,
    7,    0,   16,   15,    0,    0,    8,    0,   19,    0,
    0,   44,   20,    0,    0,    0,    0,    0,    0,    0,
    0,   45,   46,    0,   29,    0,    0,   33,   49,    0,
   54,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,   32,    0,
    0,    0,   50,   30,   31,    0,    0,   39,   40,   41,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,   52,    0,    0,   67,    0,    0,    0,
    0,   53,   34,    0,   28,   28,    0,    0,    0,    0,
    0,   36,   47,    0,   35,   28,    0,   38,
};
final static short yydgoto[] = {                          1,
    2,    6,    7,    8,    9,   45,   25,   29,   38,   15,
   34,   48,   16,   17,   65,   66,   77,   71,   68,  145,
   69,  101,  102,
};
final static short yysindex[] = {                         0,
    0, -237, -256,  -28, -245,    0,    0,    0,    0,  -35,
 -240,  -77,  -70,   -7,   14,   13,    0,    0, -211,    0,
    0,    0,    0,    0,    1,  -70,    6, -240, -123,  -23,
    0,    0,  -70,  -55,    0,   16,   17,    0,  -70,    0,
    0,  -70,    0,    0, -188,   18,    0,  -33,    0,   63,
   38,    0,    0,   63,   63,   63,   40,   41,   63,   63,
   63,    0,    0,   22,    0,  432,   24,    0,    0,  619,
    0,   63,  526,   43,  440,  526,   26,   63,   63,  462,
  468,   27,  -70,   63,   63,   63,   63,   63,   63,   63,
   63,   63,   63,   63,   63, -177,   63,   63,    0,  526,
   48,   47,    0,    0,    0,   54,   58,    0,    0,    0,
   36,  619,  619,   20,   20,   20,   20,   20,   20,  -41,
  -41,  -42,  -42,    0,  490,  496,    0,   63,  -22,  -19,
   60,    0,    0,  526,    0,    0,   63,  -15,    3,  -32,
 -168,    0,    0,  -11,    0,    0,   21,    0,
};
final static short yyrindex[] = {                         0,
    0,  108,    0,    0,    0,    0,    0,    0,    0,    0,
   72,    0,    0,    0,    0,   73,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,   -8,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,   39,    0,    0,    0,    0,    0,
   88,    0,    0,    0,    0,   59,    0,    0,    0,    0,
   59,    0,    0,    0,    0,    0,  502,    0,    0,  -24,
    0,   75,    0,  591,    0,  -25,    0,   76,   76,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,  -12,
    0,   78,    0,    0,    0,    0,    0,    0,    0,    0,
    0,   12,   65,  317,  353,  363,  388,  398,  410,  131,
  159,   95,  124,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    4,    0,    0,    0,    0,    0,    0,
   57,    0,    0,    0,    0,    0,    0,    0,
};
final static short yygindex[] = {                         0,
    0,    0,   77,    0,    0,    0,    5,    0,    0,    0,
    0,  -94,    0,   92,    0,  717,  -39,  -45,    0,    0,
   67,    0,    0,
};
final static int YYTABLESIZE=881;
static short yytable[];
static { yytable();}
static void yytable(){
yytable = new short[]{                         50,
   94,   37,   67,   96,   96,   95,   54,   10,  143,   94,
   92,   11,   93,   96,   95,   43,   51,   50,   12,   51,
   19,   82,   13,   14,   54,    3,    4,   87,   68,   86,
   32,   68,    5,   43,   51,   50,   51,   40,  106,  107,
  138,  139,   54,   44,   69,   18,   46,   69,   97,   97,
   26,  147,   60,   50,   27,   60,   28,   30,   97,   31,
   54,   94,   92,   33,   93,   96,   95,   41,   51,   39,
   60,   28,   60,   42,    3,   43,   49,   72,   28,   78,
   79,   83,   99,  103,  105,  110,  124,  111,  127,   37,
  128,   53,   67,   67,  129,   50,   37,  131,  130,  137,
  135,   67,   54,  136,   60,   59,  144,    1,   59,  141,
   97,  146,   25,   26,   21,   70,   42,   42,   71,   35,
   74,   47,    0,   59,    0,   59,    0,  142,   48,   48,
   48,   48,   48,   48,   48,   55,   55,   55,   55,   55,
   36,   55,    0,    0,    0,  148,   48,   48,   48,   48,
    0,    0,    0,   55,   55,   55,   55,   59,    0,    0,
    0,    0,    0,   28,   57,   57,   57,   57,   57,    0,
   57,   56,    0,   56,   56,   56,    0,    0,   48,    0,
   48,   37,   57,   57,   57,   57,    0,   55,    0,   56,
   56,   56,   56,   20,   21,   22,   23,   24,    0,   58,
    0,   58,   58,   58,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,   57,   58,   58,   58,
   58,    0,    0,   56,   84,   85,   88,   89,   90,   91,
   51,    0,   51,   51,    0,   52,    0,   55,   56,   57,
   58,    0,   59,   60,   61,   62,   63,   64,   51,    0,
    0,   58,    0,   52,    0,   55,   56,   57,   58,    0,
   59,   60,   61,   62,   63,   64,   51,    0,   60,   60,
    0,   52,    0,   55,   56,   57,   58,    0,   59,   60,
   61,   62,   63,   64,   51,    0,    0,    0,    0,   52,
    0,   55,   56,   57,   58,    0,   59,   60,   61,   62,
   63,   64,   28,    0,    0,    0,    0,   28,    0,   28,
   28,   28,   28,    0,   28,   28,   28,   28,   28,   28,
   37,   59,   59,    0,    0,   37,   51,   37,   37,   37,
   37,   52,   37,   37,   37,   37,   37,   37,    0,    0,
    0,   62,   63,   64,   48,   48,   48,   48,   48,   48,
    0,   55,   55,   55,   55,   55,   55,   62,    0,    0,
   62,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,   62,   62,   62,   62,    0,
   57,   57,   57,   57,   57,   57,    0,   56,   56,   56,
   56,   56,   56,   61,    0,    0,   61,    0,    0,    0,
    0,    0,    0,   64,    0,    0,   64,    0,    0,   62,
    0,   61,   61,   61,   61,   58,   58,   58,   58,   58,
   58,   64,   64,   64,   64,    0,    0,    0,   63,    0,
    0,   63,    0,    0,    0,    0,    0,    0,   65,    0,
    0,   65,    0,    0,    0,   61,   63,   63,   63,   63,
   66,    0,    0,   66,    0,   64,   65,   65,   65,   65,
    0,    0,    0,    0,    0,    0,    0,    0,   66,   66,
   66,   66,    0,   94,   92,    0,   93,   96,   95,    0,
   63,   94,   92,    0,   93,   96,   95,    0,    0,    0,
   65,   87,   98,   86,    0,    0,    0,    0,  104,   87,
    0,   86,   66,   94,   92,    0,   93,   96,   95,   94,
   92,    0,   93,   96,   95,    0,    0,    0,    0,    0,
  108,   87,   97,   86,    0,    0,  109,   87,    0,   86,
   97,   94,   92,    0,   93,   96,   95,   94,   92,    0,
   93,   96,   95,   54,   54,    0,   54,   54,   54,   87,
    0,   86,   97,    0,  133,   87,    0,   86,   97,    0,
    0,   54,   54,   54,    0,    0,    0,   94,   92,    0,
   93,   96,   95,   62,   62,   62,   62,   62,   62,    0,
   97,    0,  132,    0,    0,   87,   97,   86,    0,    0,
    0,    0,   54,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,   61,
   61,   61,   61,   61,   61,    0,   97,    0,    0,   64,
   64,   64,   64,   64,   64,    0,    0,    0,    0,    0,
    0,    0,   49,   49,    0,   49,   49,   49,    0,    0,
    0,    0,    0,    0,   63,   63,   63,   63,   63,   63,
   49,    0,   49,    0,   65,   65,   65,   65,   65,   65,
   94,   92,    0,   93,   96,   95,   66,   66,   66,   66,
   66,   66,    0,    0,    0,    0,    0,    0,   87,    0,
   86,   49,    0,    0,    0,    0,    0,    0,   84,   85,
   88,   89,   90,   91,    0,    0,   84,   85,   88,   89,
   90,   91,    0,    0,    0,    0,    0,    0,    0,   97,
    0,    0,    0,    0,    0,    0,    0,    0,   84,   85,
   88,   89,   90,   91,   84,   85,   88,   89,   90,   91,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,   84,   85,   88,   89,
   90,   91,   84,   85,   88,   89,   90,   91,   54,   54,
   54,   54,   54,   54,    0,    0,   70,    0,    0,    0,
   73,   75,   76,    0,    0,   80,   81,   76,    0,    0,
    0,    0,   84,   85,   88,   89,   90,   91,  100,    0,
    0,    0,    0,    0,   76,   76,    0,    0,    0,    0,
  112,  113,  114,  115,  116,  117,  118,  119,  120,  121,
  122,  123,    0,  125,  126,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,  134,    0,    0,   49,   49,   49,
   49,   49,   49,  140,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,   88,   89,   90,
   91,
};
}
static short yycheck[];
static { yycheck(); }
static void yycheck() {
yycheck = new short[] {                         33,
   42,  125,   48,   46,   46,   47,   40,  264,   41,   42,
   43,   40,   45,   46,   47,   41,   41,   33,  264,   44,
   91,   61,   58,  264,   40,  263,  264,   60,   41,   62,
   26,   44,  270,   59,   59,   33,   61,   33,   78,   79,
  135,  136,   40,   39,   41,  123,   42,   44,   91,   91,
   58,  146,   41,   33,   41,   44,   44,  269,   91,   59,
   40,   42,   43,   58,   45,   46,   47,  123,   93,   93,
   59,   33,   61,   58,  263,   59,   59,   40,   40,   40,
   40,   60,   59,   41,   59,   59,  264,   83,   41,   33,
   44,  125,  138,  139,   41,   33,   40,   62,   41,   40,
  123,  147,   40,  123,   93,   41,  275,    0,   44,  125,
   91,  123,   41,   41,  123,   41,   41,   59,   41,   28,
   54,   45,   -1,   59,   -1,   61,   -1,  125,   41,   42,
   43,   44,   45,   46,   47,   41,   42,   43,   44,   45,
  264,   47,   -1,   -1,   -1,  125,   59,   60,   61,   62,
   -1,   -1,   -1,   59,   60,   61,   62,   93,   -1,   -1,
   -1,   -1,   -1,  125,   41,   42,   43,   44,   45,   -1,
   47,   41,   -1,   43,   44,   45,   -1,   -1,   91,   -1,
   93,  125,   59,   60,   61,   62,   -1,   93,   -1,   59,
   60,   61,   62,  264,  265,  266,  267,  268,   -1,   41,
   -1,   43,   44,   45,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   93,   59,   60,   61,
   62,   -1,   -1,   93,  257,  258,  259,  260,  261,  262,
  264,   -1,  257,  258,   -1,  269,   -1,  271,  272,  273,
  274,   -1,  276,  277,  278,  279,  280,  281,  264,   -1,
   -1,   93,   -1,  269,   -1,  271,  272,  273,  274,   -1,
  276,  277,  278,  279,  280,  281,  264,   -1,  257,  258,
   -1,  269,   -1,  271,  272,  273,  274,   -1,  276,  277,
  278,  279,  280,  281,  264,   -1,   -1,   -1,   -1,  269,
   -1,  271,  272,  273,  274,   -1,  276,  277,  278,  279,
  280,  281,  264,   -1,   -1,   -1,   -1,  269,   -1,  271,
  272,  273,  274,   -1,  276,  277,  278,  279,  280,  281,
  264,  257,  258,   -1,   -1,  269,  264,  271,  272,  273,
  274,  269,  276,  277,  278,  279,  280,  281,   -1,   -1,
   -1,  279,  280,  281,  257,  258,  259,  260,  261,  262,
   -1,  257,  258,  259,  260,  261,  262,   41,   -1,   -1,
   44,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   59,   60,   61,   62,   -1,
  257,  258,  259,  260,  261,  262,   -1,  257,  258,  259,
  260,  261,  262,   41,   -1,   -1,   44,   -1,   -1,   -1,
   -1,   -1,   -1,   41,   -1,   -1,   44,   -1,   -1,   93,
   -1,   59,   60,   61,   62,  257,  258,  259,  260,  261,
  262,   59,   60,   61,   62,   -1,   -1,   -1,   41,   -1,
   -1,   44,   -1,   -1,   -1,   -1,   -1,   -1,   41,   -1,
   -1,   44,   -1,   -1,   -1,   93,   59,   60,   61,   62,
   41,   -1,   -1,   44,   -1,   93,   59,   60,   61,   62,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   59,   60,
   61,   62,   -1,   42,   43,   -1,   45,   46,   47,   -1,
   93,   42,   43,   -1,   45,   46,   47,   -1,   -1,   -1,
   93,   60,   61,   62,   -1,   -1,   -1,   -1,   59,   60,
   -1,   62,   93,   42,   43,   -1,   45,   46,   47,   42,
   43,   -1,   45,   46,   47,   -1,   -1,   -1,   -1,   -1,
   59,   60,   91,   62,   -1,   -1,   59,   60,   -1,   62,
   91,   42,   43,   -1,   45,   46,   47,   42,   43,   -1,
   45,   46,   47,   42,   43,   -1,   45,   46,   47,   60,
   -1,   62,   91,   -1,   59,   60,   -1,   62,   91,   -1,
   -1,   60,   61,   62,   -1,   -1,   -1,   42,   43,   -1,
   45,   46,   47,  257,  258,  259,  260,  261,  262,   -1,
   91,   -1,   93,   -1,   -1,   60,   91,   62,   -1,   -1,
   -1,   -1,   91,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,  257,
  258,  259,  260,  261,  262,   -1,   91,   -1,   -1,  257,
  258,  259,  260,  261,  262,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   42,   43,   -1,   45,   46,   47,   -1,   -1,
   -1,   -1,   -1,   -1,  257,  258,  259,  260,  261,  262,
   60,   -1,   62,   -1,  257,  258,  259,  260,  261,  262,
   42,   43,   -1,   45,   46,   47,  257,  258,  259,  260,
  261,  262,   -1,   -1,   -1,   -1,   -1,   -1,   60,   -1,
   62,   91,   -1,   -1,   -1,   -1,   -1,   -1,  257,  258,
  259,  260,  261,  262,   -1,   -1,  257,  258,  259,  260,
  261,  262,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   91,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,  257,  258,
  259,  260,  261,  262,  257,  258,  259,  260,  261,  262,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,  257,  258,  259,  260,
  261,  262,  257,  258,  259,  260,  261,  262,  257,  258,
  259,  260,  261,  262,   -1,   -1,   50,   -1,   -1,   -1,
   54,   55,   56,   -1,   -1,   59,   60,   61,   -1,   -1,
   -1,   -1,  257,  258,  259,  260,  261,  262,   72,   -1,
   -1,   -1,   -1,   -1,   78,   79,   -1,   -1,   -1,   -1,
   84,   85,   86,   87,   88,   89,   90,   91,   92,   93,
   94,   95,   -1,   97,   98,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,  128,   -1,   -1,  257,  258,  259,
  260,  261,  262,  137,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,  259,  260,  261,
  262,
};
}
final static short YYFINAL=1;
final static short YYMAXTOKEN=281;
final static String yyname[] = {
"end-of-file",null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,"'!'",null,null,null,null,null,null,"'('","')'","'*'","'+'",
"','","'-'","'.'","'/'",null,null,null,null,null,null,null,null,null,null,"':'",
"';'","'<'","'='","'>'",null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,"'['",null,"']'",null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,"'{'",null,"'}'",null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,"\"AND\"","\"OR\"",
"\"MENORIGUAL\"","\"MAYORIGUAL\"","\"NOTIGUAL\"","\"IGUAL\"","\"VAR\"",
"\"IDENT\"","\"INT\"","\"REAL\"","\"FLOAT\"","\"CHAR\"","\"LITERALINT\"",
"\"STRUCT\"","\"READ\"","\"RETURN\"","\"IF\"","\"WHILE\"","\"ELSE\"",
"\"PRINT\"","\"PRINTSP\"","\"PRINTLN\"","\"LITERALREAL\"","\"CHARACTER\"",
"\"CAST\"",
};
final static String yyrule[] = {
"$accept : programa",
"programa : definiciones",
"definiciones :",
"definiciones : definiciones definicion",
"definicion : defVariable",
"definicion : struct",
"definicion : deffuncion",
"defVariables :",
"defVariables : defVariables defVariable",
"defVariable : \"VAR\" \"IDENT\" ':' tipo ';'",
"tipo : \"INT\"",
"tipo : \"REAL\"",
"tipo : \"FLOAT\"",
"tipo : \"CHAR\"",
"tipo : \"IDENT\"",
"tipo : '[' \"LITERALINT\" ']' tipo",
"struct : \"STRUCT\" \"IDENT\" '{' defCamposStruct '}' ';'",
"defCamposStruct :",
"defCamposStruct : defCamposStruct defCampoStruct",
"defCampoStruct : \"IDENT\" ':' tipo ';'",
"deffuncion : \"IDENT\" '(' paramsFuncOpt ')' valorRetorno '{' defVariables sentencias '}'",
"valorRetorno :",
"valorRetorno : ':' tipo",
"paramsFunc : parametro",
"paramsFunc : paramsFunc ',' parametro",
"paramsFuncOpt :",
"paramsFuncOpt : paramsFunc",
"parametro : \"IDENT\" ':' tipo",
"sentencias :",
"sentencias : sentencias sentencia",
"sentencia : \"READ\" expr ';'",
"sentencia : \"RETURN\" exprOpt ';'",
"sentencia : callFunc ';'",
"sentencia : sentenciaPrint",
"sentencia : expr '=' expr ';'",
"sentencia : \"IF\" '(' exprOpt ')' '{' sentencias '}' elseOpt",
"sentencia : \"WHILE\" '(' exprOpt ')' '{' sentencias '}'",
"elseOpt :",
"elseOpt : \"ELSE\" '{' sentencias '}'",
"sentenciaPrint : \"PRINT\" expr ';'",
"sentenciaPrint : \"PRINTSP\" expr ';'",
"sentenciaPrint : \"PRINTLN\" exprOpt ';'",
"exprOpt :",
"exprOpt : expr",
"expr : \"LITERALINT\"",
"expr : \"LITERALREAL\"",
"expr : \"CHARACTER\"",
"expr : \"CAST\" '<' tipo '>' '(' expr ')'",
"expr : \"IDENT\"",
"expr : exprBinaria",
"expr : '(' exprBinaria ')'",
"expr : '!' expr",
"expr : expr '.' \"IDENT\"",
"expr : expr '[' expr ']'",
"expr : callFunc",
"exprBinaria : expr '*' expr",
"exprBinaria : expr '+' expr",
"exprBinaria : expr '/' expr",
"exprBinaria : expr '-' expr",
"exprBinaria : expr \"OR\" expr",
"exprBinaria : expr \"AND\" expr",
"exprBinaria : expr '<' expr",
"exprBinaria : expr '>' expr",
"exprBinaria : expr \"MAYORIGUAL\" expr",
"exprBinaria : expr \"MENORIGUAL\" expr",
"exprBinaria : expr \"NOTIGUAL\" expr",
"exprBinaria : expr \"IGUAL\" expr",
"callFunc : \"IDENT\" '(' listaParamOpt ')'",
"listaParam : expr",
"listaParam : listaParam ',' expr",
"listaParamOpt :",
"listaParamOpt : listaParam",
};

//#line 132 "sintac.y"
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
//#line 524 "Parser.java"
//###############################################################
// method: yylexdebug : check lexer state
//###############################################################
void yylexdebug(int state,int ch)
{
String s=null;
  if (ch < 0) ch=0;
  if (ch <= YYMAXTOKEN) //check index bounds
     s = yyname[ch];    //now get it
  if (s==null)
    s = "illegal-symbol";
  debug("state "+state+", reading "+ch+" ("+s+")");
}





//The following are now global, to aid in error reporting
int yyn;       //next next thing to do
int yym;       //
int yystate;   //current parsing state from state table
String yys;    //current token string


//###############################################################
// method: yyparse : parse input and execute indicated items
//###############################################################
int yyparse()
{
boolean doaction;
  init_stacks();
  yynerrs = 0;
  yyerrflag = 0;
  yychar = -1;          //impossible char forces a read
  yystate=0;            //initial state
  state_push(yystate);  //save it
  val_push(yylval);     //save empty value
  while (true) //until parsing is done, either correctly, or w/error
    {
    doaction=true;
    if (yydebug) debug("loop"); 
    //#### NEXT ACTION (from reduction table)
    for (yyn=yydefred[yystate];yyn==0;yyn=yydefred[yystate])
      {
      if (yydebug) debug("yyn:"+yyn+"  state:"+yystate+"  yychar:"+yychar);
      if (yychar < 0)      //we want a char?
        {
        yychar = yylex();  //get next token
        if (yydebug) debug(" next yychar:"+yychar);
        //#### ERROR CHECK ####
        if (yychar < 0)    //it it didn't work/error
          {
          yychar = 0;      //change it to default string (no -1!)
          if (yydebug)
            yylexdebug(yystate,yychar);
          }
        }//yychar<0
      yyn = yysindex[yystate];  //get amount to shift by (shift index)
      if ((yyn != 0) && (yyn += yychar) >= 0 &&
          yyn <= YYTABLESIZE && yycheck[yyn] == yychar)
        {
        if (yydebug)
          debug("state "+yystate+", shifting to state "+yytable[yyn]);
        //#### NEXT STATE ####
        yystate = yytable[yyn];//we are in a new state
        state_push(yystate);   //save it
        val_push(yylval);      //push our lval as the input for next rule
        yychar = -1;           //since we have 'eaten' a token, say we need another
        if (yyerrflag > 0)     //have we recovered an error?
           --yyerrflag;        //give ourselves credit
        doaction=false;        //but don't process yet
        break;   //quit the yyn=0 loop
        }

    yyn = yyrindex[yystate];  //reduce
    if ((yyn !=0 ) && (yyn += yychar) >= 0 &&
            yyn <= YYTABLESIZE && yycheck[yyn] == yychar)
      {   //we reduced!
      if (yydebug) debug("reduce");
      yyn = yytable[yyn];
      doaction=true; //get ready to execute
      break;         //drop down to actions
      }
    else //ERROR RECOVERY
      {
      if (yyerrflag==0)
        {
        yyerror("syntax error");
        yynerrs++;
        }
      if (yyerrflag < 3) //low error count?
        {
        yyerrflag = 3;
        while (true)   //do until break
          {
          if (stateptr<0)   //check for under & overflow here
            {
            yyerror("stack underflow. aborting...");  //note lower case 's'
            return 1;
            }
          yyn = yysindex[state_peek(0)];
          if ((yyn != 0) && (yyn += YYERRCODE) >= 0 &&
                    yyn <= YYTABLESIZE && yycheck[yyn] == YYERRCODE)
            {
            if (yydebug)
              debug("state "+state_peek(0)+", error recovery shifting to state "+yytable[yyn]+" ");
            yystate = yytable[yyn];
            state_push(yystate);
            val_push(yylval);
            doaction=false;
            break;
            }
          else
            {
            if (yydebug)
              debug("error recovery discarding state "+state_peek(0)+" ");
            if (stateptr<0)   //check for under & overflow here
              {
              yyerror("Stack underflow. aborting...");  //capital 'S'
              return 1;
              }
            state_pop();
            val_pop();
            }
          }
        }
      else            //discard this token
        {
        if (yychar == 0)
          return 1; //yyabort
        if (yydebug)
          {
          yys = null;
          if (yychar <= YYMAXTOKEN) yys = yyname[yychar];
          if (yys == null) yys = "illegal-symbol";
          debug("state "+yystate+", error recovery discards token "+yychar+" ("+yys+")");
          }
        yychar = -1;  //read another
        }
      }//end error recovery
    }//yyn=0 loop
    if (!doaction)   //any reason not to proceed?
      continue;      //skip action
    yym = yylen[yyn];          //get count of terminals on rhs
    if (yydebug)
      debug("state "+yystate+", reducing "+yym+" by rule "+yyn+" ("+yyrule[yyn]+")");
    if (yym>0)                 //if count of rhs not 'nil'
      yyval = val_peek(yym-1); //get current semantic value
    switch(yyn)
      {
//########## USER-SUPPLIED ACTIONS ##########
case 1:
//#line 24 "sintac.y"
{raiz = new Programa(val_peek(0));		}
break;
case 2:
//#line 27 "sintac.y"
{yyval = new ArrayList();			}
break;
case 3:
//#line 28 "sintac.y"
{yyval = val_peek(1); ((List)val_peek(1)).add(val_peek(0));	}
break;
case 4:
//#line 30 "sintac.y"
{yyval = val_peek(0);	}
break;
case 5:
//#line 31 "sintac.y"
{yyval = val_peek(0);	}
break;
case 6:
//#line 32 "sintac.y"
{yyval = val_peek(0);	}
break;
case 7:
//#line 36 "sintac.y"
{yyval = new ArrayList();			}
break;
case 8:
//#line 37 "sintac.y"
{yyval = val_peek(1); ((List)val_peek(1)).add(val_peek(0));	}
break;
case 9:
//#line 39 "sintac.y"
{yyval = new DefVariable(val_peek(3),val_peek(1));		}
break;
case 10:
//#line 41 "sintac.y"
{yyval = new IntType();			}
break;
case 11:
//#line 42 "sintac.y"
{yyval = new RealType();			}
break;
case 12:
//#line 43 "sintac.y"
{yyval = new FloatType();			}
break;
case 13:
//#line 44 "sintac.y"
{yyval = new CharType();			}
break;
case 14:
//#line 45 "sintac.y"
{yyval = new StructType(val_peek(0));		}
break;
case 15:
//#line 46 "sintac.y"
{yyval = new ArrayType (val_peek(2), val_peek(0));	}
break;
case 16:
//#line 50 "sintac.y"
{yyval = new Struct (val_peek(4), val_peek(2));		}
break;
case 17:
//#line 52 "sintac.y"
{yyval = new ArrayList();			}
break;
case 18:
//#line 53 "sintac.y"
{yyval = val_peek(1); ((List)yyval).add(val_peek(0));	}
break;
case 19:
//#line 55 "sintac.y"
{yyval = new DefCampoStruct(val_peek(3),val_peek(1));}
break;
case 20:
//#line 59 "sintac.y"
{yyval = new DefFuncion(val_peek(8),val_peek(6),val_peek(4),val_peek(2),val_peek(1));	}
break;
case 21:
//#line 61 "sintac.y"
{yyval = null;	}
break;
case 22:
//#line 62 "sintac.y"
{yyval = val_peek(0);	}
break;
case 23:
//#line 64 "sintac.y"
{yyval = new ArrayList(); ((List)yyval).add(val_peek(0)); 	}
break;
case 24:
//#line 65 "sintac.y"
{yyval = val_peek(2); ((List)yyval).add(val_peek(0));				}
break;
case 25:
//#line 67 "sintac.y"
{yyval = new ArrayList();						}
break;
case 26:
//#line 68 "sintac.y"
{yyval = val_peek(0);									}
break;
case 27:
//#line 70 "sintac.y"
{yyval = new Parametro(val_peek(2), val_peek(0));					}
break;
case 28:
//#line 74 "sintac.y"
{yyval = new ArrayList();			}
break;
case 29:
//#line 75 "sintac.y"
{yyval = val_peek(1); ((List)val_peek(1)).add(val_peek(0));	}
break;
case 30:
//#line 77 "sintac.y"
{yyval = new Read(val_peek(1));			}
break;
case 31:
//#line 78 "sintac.y"
{yyval = new Return(val_peek(1));		}
break;
case 32:
//#line 79 "sintac.y"
{yyval = val_peek(1);					}
break;
case 34:
//#line 81 "sintac.y"
{yyval = new Asignacion(val_peek(3), val_peek(1));}
break;
case 35:
//#line 82 "sintac.y"
{yyval = new Condicional(val_peek(5),val_peek(2),val_peek(0));}
break;
case 36:
//#line 83 "sintac.y"
{yyval = new Bucle(val_peek(4),val_peek(1));}
break;
case 37:
//#line 85 "sintac.y"
{yyval = null; }
break;
case 38:
//#line 86 "sintac.y"
{yyval = val_peek(1); 	}
break;
case 39:
//#line 88 "sintac.y"
{yyval = new Print(val_peek(1));	}
break;
case 40:
//#line 89 "sintac.y"
{yyval = new Printsp(val_peek(1));	}
break;
case 41:
//#line 90 "sintac.y"
{yyval = new Println(val_peek(1));	}
break;
case 42:
//#line 92 "sintac.y"
{yyval = null;				}
break;
case 43:
//#line 93 "sintac.y"
{yyval = val_peek(0);				}
break;
case 44:
//#line 97 "sintac.y"
{yyval = new LiteralInt(val_peek(0));	}
break;
case 45:
//#line 98 "sintac.y"
{yyval = new LiteralReal(val_peek(0));	}
break;
case 46:
//#line 99 "sintac.y"
{yyval = new Caracter(val_peek(0));		}
break;
case 47:
//#line 100 "sintac.y"
{yyval = new Cast(val_peek(4), val_peek(1));		}
break;
case 48:
//#line 101 "sintac.y"
{yyval = new Variable(val_peek(0));		}
break;
case 49:
//#line 102 "sintac.y"
{yyval = val_peek(0);					}
break;
case 50:
//#line 103 "sintac.y"
{yyval = val_peek(1);					}
break;
case 51:
//#line 104 "sintac.y"
{yyval = new Negacion(val_peek(0));		}
break;
case 52:
//#line 105 "sintac.y"
{yyval = new CampoStruct(val_peek(2),val_peek(0));}
break;
case 53:
//#line 106 "sintac.y"
{yyval = new CallArray(val_peek(3), val_peek(1));	}
break;
case 54:
//#line 107 "sintac.y"
{yyval = val_peek(0);					}
break;
case 55:
//#line 109 "sintac.y"
{yyval = new ExpresionBinaria(val_peek(2),"*",val_peek(0));		}
break;
case 56:
//#line 110 "sintac.y"
{yyval = new ExpresionBinaria(val_peek(2),"+",val_peek(0));		}
break;
case 57:
//#line 111 "sintac.y"
{yyval = new ExpresionBinaria(val_peek(2),"/",val_peek(0));		}
break;
case 58:
//#line 112 "sintac.y"
{yyval = new ExpresionBinaria(val_peek(2),"-",val_peek(0));		}
break;
case 59:
//#line 113 "sintac.y"
{yyval = new ExpresionBinaria(val_peek(2),"||",val_peek(0));		}
break;
case 60:
//#line 114 "sintac.y"
{yyval = new ExpresionBinaria(val_peek(2),"&&",val_peek(0));		}
break;
case 61:
//#line 115 "sintac.y"
{yyval = new ExpresionBinaria(val_peek(2),"<",val_peek(0));		}
break;
case 62:
//#line 116 "sintac.y"
{yyval = new ExpresionBinaria(val_peek(2),">",val_peek(0));		}
break;
case 63:
//#line 117 "sintac.y"
{yyval = new ExpresionBinaria(val_peek(2),">=",val_peek(0));		}
break;
case 64:
//#line 118 "sintac.y"
{yyval = new ExpresionBinaria(val_peek(2),"<=",val_peek(0));		}
break;
case 65:
//#line 119 "sintac.y"
{yyval = new ExpresionBinaria(val_peek(2),"!=",val_peek(0));		}
break;
case 66:
//#line 120 "sintac.y"
{yyval = new ExpresionBinaria(val_peek(2),"==",val_peek(0));		}
break;
case 67:
//#line 122 "sintac.y"
{yyval = new CallFunc(val_peek(3), val_peek(1));			}
break;
case 68:
//#line 124 "sintac.y"
{yyval = new ArrayList(); ((List)yyval).add(val_peek(0)); 	}
break;
case 69:
//#line 125 "sintac.y"
{yyval = val_peek(2); ((List)yyval).add(val_peek(0));				}
break;
case 70:
//#line 127 "sintac.y"
{yyval = new ArrayList();	}
break;
case 71:
//#line 128 "sintac.y"
{yyval = val_peek(0);				}
break;
//#line 952 "Parser.java"
//########## END OF USER-SUPPLIED ACTIONS ##########
    }//switch
    //#### Now let's reduce... ####
    if (yydebug) debug("reduce");
    state_drop(yym);             //we just reduced yylen states
    yystate = state_peek(0);     //get new state
    val_drop(yym);               //corresponding value drop
    yym = yylhs[yyn];            //select next TERMINAL(on lhs)
    if (yystate == 0 && yym == 0)//done? 'rest' state and at first TERMINAL
      {
      if (yydebug) debug("After reduction, shifting from state 0 to state "+YYFINAL+"");
      yystate = YYFINAL;         //explicitly say we're done
      state_push(YYFINAL);       //and save it
      val_push(yyval);           //also save the semantic value of parsing
      if (yychar < 0)            //we want another character?
        {
        yychar = yylex();        //get next character
        if (yychar<0) yychar=0;  //clean, if necessary
        if (yydebug)
          yylexdebug(yystate,yychar);
        }
      if (yychar == 0)          //Good exit (if lex returns 0 ;-)
         break;                 //quit the loop--all DONE
      }//if yystate
    else                        //else not done yet
      {                         //get next state and push, for next yydefred[]
      yyn = yygindex[yym];      //find out where to go
      if ((yyn != 0) && (yyn += yystate) >= 0 &&
            yyn <= YYTABLESIZE && yycheck[yyn] == yystate)
        yystate = yytable[yyn]; //get new state
      else
        yystate = yydgoto[yym]; //else go to new defred
      if (yydebug) debug("after reduction, shifting from state "+state_peek(0)+" to state "+yystate+"");
      state_push(yystate);     //going again, so push state & val...
      val_push(yyval);         //for next action
      }
    }//main loop
  return 0;//yyaccept!!
}
//## end of method parse() ######################################



//## run() --- for Thread #######################################
/**
 * A default run method, used for operating this parser
 * object in the background.  It is intended for extending Thread
 * or implementing Runnable.  Turn off with -Jnorun .
 */
public void run()
{
  yyparse();
}
//## end of method run() ########################################



//## Constructors ###############################################
/**
 * Default constructor.  Turn off with -Jnoconstruct .

 */
public Parser()
{
  //nothing to do
}


/**
 * Create a parser, setting the debug to true or false.
 * @param debugMe true for debugging, false for no debug.
 */
public Parser(boolean debugMe)
{
  yydebug=debugMe;
}
//###############################################################



}
//################### END OF CLASS ##############################
