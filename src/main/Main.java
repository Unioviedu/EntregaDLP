package main;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.Writer;

import ast.AST;
import generacionDeCodigo.GeneracionDeCodigo;
import semantico.AnalisisSemantico;
import sintactico.Parser;
import sintactico.Yylex;
import visitor.ASTPrinter;


/**
 * Clase que inicia el compilador e invoca a todas sus fases.
 * 
 * No es necesario modificar este fichero. En su lugar hay que modificar:
 * - Para An�lisis Sint�ctico: 'sintactico/sintac.y' y 'sintactico/lexico.l'
 * - Para An�lisis Sem�ntico: 'semantico/Identificacion.java' y 'semantico/ComprobacionDeTipos.java'
 * - Para Generaci�n de C�digo: 'generacionDeCodigo/GestionDeMemoria.java' y 'generacionDeCodigo/SeleccionDeInstrucciones.java'
 *
 * @author Ra�l Izquierdo
 * 
 */
public class Main {
	static String[] entradas = {"entrada-0", "hipoteca-1", "prueba-2", 
			"funciones-3", "estructuras-4", "variables-5", "testTipos-6",
			"testGestionMemoria-7"};
	//public static final String[] programa = entradas;	// Entrada a usar durante el desarrollo
	public static final String[] programa = { entradas[0] };	// Entrada a usar durante el desarrollo
	
	public static void main(String[] args) throws Exception {
		GestorErrores gestor = new GestorErrores();
		
		for (int i = 0; i < programa.length; i++) {
			System.out.println("\n\n\n------ RESULTADO PARA EL PROGRAMA "+programa[i]+"-------");
			AST raiz = compile("src/"+programa[i].split("-")[0]+".txt", gestor); // Poner args[0] en vez de "programa" en la versi�n final
			if (!gestor.hayErrores())
				System.out.println("El programa se ha compilado correctamente.");
	
			ASTPrinter.toHtml("src/"+programa[i].split("-")[0]+".txt", raiz, "Traza arbol"); // Utilidad generada por VGen (opcional)
		}
		System.out.println("\n\n");
	}

	/**
	 * M�todo que coordina todas las fases del compilador
	 */
	public static AST compile(String sourceName, GestorErrores gestor) throws Exception {

		// 1. Fases de An�lisis L�xico y Sint�ctico
		Yylex lexico = new Yylex(new FileReader(sourceName), gestor);
		Parser sintactico = new Parser(lexico, gestor, false);
		sintactico.parse();

		AST raiz = sintactico.getAST();
		if (raiz == null) // Hay errores o el AST no se ha implementado a�n
			return null;

		// 2. Fase de An�lisis Sem�ntico
		AnalisisSemantico semantico = new AnalisisSemantico(gestor);
		semantico.analiza(raiz);
		if (gestor.hayErrores())
			return raiz;

		// 3. Fase de Generaci�n de C�digo
		File sourceFile = new File(sourceName);
		Writer out = new FileWriter(new File(sourceFile.getParent(), "salida.txt"));

		GeneracionDeCodigo generador = new GeneracionDeCodigo();
		generador.genera(sourceFile.getName(), raiz, out);
		out.close();

		return raiz;
	}
}
