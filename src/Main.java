import com.sun.security.jgss.GSSUtil;

import java.util.*;
import java.io.*;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        ciclo:
        while (true) {
            File carpeta = new File(System.getProperty("user.dir"));
            File[] listaDeArchivos = carpeta.listFiles();
            System.out.println("Archivos:");

            for (File archivo : listaDeArchivos) {
                if (archivo.isFile() && archivo.getName().endsWith(".csv")) {
                    System.out.println("    " + archivo.getName());
                }
            }

            System.out.println();
            System.out.print("Crear un marco de datos del archivo: ");
            String archivo = sc.next();
            System.out.println();
            MarcoDeDatos archivo1 = new MarcoDeDatos(archivo);
            archivo1.imprimirArchivo();

            anidado:
            while (true) {
                System.out.println("El archivo sobre el que se está operando es " + archivo1.getNombreArchivo() + ".\n");
                System.out.println("Tipos de Operación:");
                System.out.println("    0. Volver a lista de archivos");
                System.out.println("    1. Estadísticas");
                System.out.println("    2. Filtro");
                System.out.print("\nEscribe el número del tipo de operación que deseas realizar o cualquier otro número para detener el programa: ");
                int tipoOperacion = sc.nextInt();
                int columna;

                switch (tipoOperacion) {
                    case 0:
                        break anidado;
                    case 1: {
                        System.out.print("\nEscribe el número de la columna, empezando desde cero, sobre la cual deseas realizar la operación: ");
                        columna = sc.nextInt();
                        System.out.println("\nOperaciones:");
                        System.out.println("    1. Contar");
                        System.out.println("    2. Mínimo");
                        System.out.println("    3. Máximo");
                        System.out.println("    4. Promedio");
                        System.out.println("    5. Moda");
                        System.out.print("\nEscribe el número de la operación que deseas realizar: ");
                        int operacion = sc.nextInt();

                        switch (operacion) {
                            case 1 -> archivo1.Contar(columna);
                            case 2 -> archivo1.getMinimo(columna);
                            case 3 -> archivo1.getMaximo(columna);
                            case 4 -> archivo1.getPromedio(columna);
                            case 5 -> archivo1.getModa(columna);
                            default -> System.out.println("\nOperación no válida");
                        }
                        break;

                    } case 2: {
                        System.out.print("\nEscribe el número de la columna, empezando desde cero, sobre la cual deseas realizar la operación: ");
                        columna = sc.nextInt();
                        System.out.println("\nOperaciones:");
                        System.out.println("    1. Menor que");
                        System.out.println("    2. Mayor que");
                        System.out.println("    3. Menor o igual que");
                        System.out.println("    4. Mayor o igual que");
                        System.out.println("    5. Igual a");
                        System.out.println("    6. Diferente de");
                        System.out.print("\nEscribe el número de la operación que deseas realizar: ");
                        int operacion = sc.nextInt();
                        System.out.print("\nEscribe el valor con el cual deseas realizar la operación: ");
                        double valor = sc.nextDouble();
                        System.out.println();
                        MarcoDeDatos archivo2 = null;

                        switch (operacion) {
                            case 1 -> archivo2 = archivo1.getMenorQue(columna, valor);
                            case 2 -> archivo2 = archivo1.getMayorQue(columna, valor);
                            case 3 -> archivo2 = archivo1.getMenorOIgualQue(columna, valor);
                            case 4 -> archivo2 = archivo1.getMayorOIgualQue(columna, valor);
                            case 5 -> archivo2 = archivo1.getIgualA(columna, valor);
                            case 6 -> archivo2 = archivo1.getDiferenteDe(columna, valor);
                            default -> System.out.println("\nOperación no válida");
                        }
                        archivo2.imprimirArchivo();
                        break;
                    } default:
                        break ciclo;
                }
            }
        }
    }
}