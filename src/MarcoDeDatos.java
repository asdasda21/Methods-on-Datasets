import java.util.*;
import java.io.*;

public class MarcoDeDatos {
    private final ArrayList<Dato> DataSet;
    private final ArrayList<ArrayList<Object>> dsMatrix;
    private final String nombreArchivo;

    MarcoDeDatos(String nombreArchivo) {
        this.nombreArchivo = nombreArchivo;
        File archivo = new File(nombreArchivo);
        try {
            this.DataSet = new ArrayList<>();
            this.dsMatrix = new ArrayList<>();
            Scanner input = new Scanner(archivo);
            String linea;
            while (input.hasNextLine()) {
                linea = input.nextLine();
                String[] arrLinea = linea.split("\"");
                for (int i = 0; i < arrLinea.length; i++) {
                    if (arrLinea[i].equals(",,") || arrLinea[i].equals(",,,")) {
                        arrLinea[i] = " ";
                    }
                }
                ArrayList<Object> arrLinea2 = new ArrayList<>();
                for (String s : arrLinea) {
                    if (!Objects.equals(s, ",") && !Objects.equals(s, "")) {
                        if (esNumerico(s)) {
                            Double d = Double.parseDouble(s);
                            arrLinea2.add(d);
                        } else {
                            arrLinea2.add(s);
                        }
                    }
                }
                while (arrLinea2.size()<7) {
                    arrLinea2.add(" ");
                }
                this.dsMatrix.add(arrLinea2);
                this.DataSet.add(new Dato((String) arrLinea2.get(0), (String) arrLinea2.get(1), (String) arrLinea2.get(2), arrLinea2.get(3), arrLinea2.get(4), arrLinea2.get(5), arrLinea2.get(6)));
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public String getNombreArchivo() {
        return this.nombreArchivo;
    }

    public static boolean esNumerico(String s) {
        try {
            double d = Double.parseDouble(s);
        } catch (NumberFormatException nfe) {
            return false;
        }
        return true;
    }

    public void imprimirArchivo() {
        for (Dato dato: this.DataSet) {
            dato.imprimirDato();
        }
        System.out.println();
    }

    public int primerNumerico(int columna) {
        int primerN=0;
        for (int i = 0; i < this.dsMatrix.size(); i++) {
            if (this.dsMatrix.get(i).get(columna) instanceof Double) {
                primerN = i;
                break;
            }
        }
        return primerN;
    }

    //ESTADISTICAS

    public void Contar(int columna) {
        int cuenta = 0;
        for (int i = 0; i < this.dsMatrix.size(); i++) {
            if(this.dsMatrix.get(i).get(columna) instanceof Double) {
                cuenta++;
            }
        }
        System.out.println("Hay " + cuenta + " valores numericos en la columna " + columna + ".\n");
    }

    public void getMinimo(int columna) {
        int minimo = this.primerNumerico(columna);
        for(int i=minimo; i < this.dsMatrix.size(); i++) {
            if(this.dsMatrix.get(i).get(columna) instanceof Double && (Double) this.dsMatrix.get(i).get(columna) < (Double) this.dsMatrix.get(minimo).get(columna)) {
                minimo = i;
            }
        }
        System.out.println("El valor mínimo de la columna " + columna + " es " + this.dsMatrix.get(minimo).get(columna) + ".\n");
    }

    public void getMaximo(int columna) {
        int maximo = this.primerNumerico(columna);
        for(int i=maximo; i < this.dsMatrix.size(); i++) {
            if(this.dsMatrix.get(i).get(columna) instanceof Double && (Double) this.dsMatrix.get(i).get(columna) > (Double) this.dsMatrix.get(maximo).get(columna)) {
                maximo = i;
            }
        }
        System.out.println("El valor máximo de la columna " + columna + " es " + this.dsMatrix.get(maximo).get(columna) + ".\n");
    }

    public void getPromedio(int columna) {
        Double suma = 0.0;
        int total = 0;
        for (int i = 0; i < this.dsMatrix.size(); i++) {
            if(this.dsMatrix.get(i).get(columna) instanceof Double) {
                suma += (Double) this.dsMatrix.get(i).get(columna);
                total++;
            }
        }
        System.out.println("El promedio de la columna " + columna + " es " + suma/total + ".\n");
    }

    public void getModa (int columna) {
        ArrayList<Double []> valores = new ArrayList<>();
        boolean yaIncluido = false;
        for (int i = 0; i < this.dsMatrix.size(); i++) {
            if(this.dsMatrix.get(i).get(columna) instanceof Double) {
                int j;
                for (j=0; j < valores.size(); j++) {
                    if (this.dsMatrix.get(i).get(columna).equals(valores.get(j)[0])) {
                        yaIncluido = true;
                        break;
                    }
                }
                if (yaIncluido) {
                    valores.get(j)[1]++;
                } else {
                    valores.add(new Double[]{(Double) this.dsMatrix.get(i).get(columna), 1.0});
                }
            }
            yaIncluido = false;
        }
        int moda=0;
        for (int i = 0; i < valores.size(); i++) {
            if (valores.get(i)[1] > valores.get(moda)[1]) {
                moda = i;
            }
        }
        System.out.println("La moda de la columna " + columna + " es " + valores.get(moda)[0] + ", con " + valores.get(moda)[1].intValue() + " ocurrencias.\n");
    }

    //FILTRO

    public MarcoDeDatos getMayorQue(int columna, Double valor) {
        String metodo = "MayorQue";
        ArrayList<Dato> newDS = new ArrayList<>();
        newDS.add(this.DataSet.get(0));
        for (int i = 0; i < this.dsMatrix.size(); i++) {
            if (this.dsMatrix.get(i).get(columna) instanceof Double && (Double) this.dsMatrix.get(i).get(columna)>valor) {
                newDS.add(this.DataSet.get(i));
            }
        }
        return nuevoArchivo(newDS, columna, valor, metodo);
    }

    public MarcoDeDatos getMayorOIgualQue(int columna, Double valor) {
        String metodo = "MayorOIgualQue";
        ArrayList<Dato> newDS = new ArrayList<>();
        newDS.add(this.DataSet.get(0));
        for (int i = 0; i < this.dsMatrix.size(); i++) {
            if (this.dsMatrix.get(i).get(columna) instanceof Double && (Double) this.dsMatrix.get(i).get(columna)>=valor) {
                newDS.add(this.DataSet.get(i));
            }
        }
        return nuevoArchivo(newDS, columna, valor, metodo);
    }

    public MarcoDeDatos getMenorQue(int columna, Double valor) {
        String metodo = "MenorQue";
        ArrayList<Dato> newDS = new ArrayList<>();
        newDS.add(this.DataSet.get(0));
        for (int i = 0; i < this.dsMatrix.size(); i++) {
            if (this.dsMatrix.get(i).get(columna) instanceof Double && (Double) this.dsMatrix.get(i).get(columna)<valor) {
                newDS.add(this.DataSet.get(i));
            }
        }
        return nuevoArchivo(newDS, columna, valor, metodo);
    }

    public MarcoDeDatos getMenorOIgualQue(int columna, Double valor) {
        String metodo = "MenorOIgualQue";
        ArrayList<Dato> newDS = new ArrayList<>();
        newDS.add(this.DataSet.get(0));
        for (int i = 0; i < this.dsMatrix.size(); i++) {
            if (this.dsMatrix.get(i).get(columna) instanceof Double && (Double) this.dsMatrix.get(i).get(columna)<=valor) {
                newDS.add(this.DataSet.get(i));
            }
        }
        return nuevoArchivo(newDS, columna, valor, metodo);
    }

    public MarcoDeDatos getIgualA(int columna, Double valor) {
        String metodo = "IgualA";
        ArrayList<Dato> newDS = new ArrayList<>();
        newDS.add(this.DataSet.get(0));
        for (int i = 0; i < this.dsMatrix.size(); i++) {
            if (this.dsMatrix.get(i).get(columna) instanceof Double && this.dsMatrix.get(i).get(columna)==valor) {
                newDS.add(this.DataSet.get(i));
            }
        }
        return nuevoArchivo(newDS, columna, valor, metodo);
    }

    public MarcoDeDatos getDiferenteDe(int columna, Double valor) {
        String metodo = "DiferenteDe";
        ArrayList<Dato> newDS = new ArrayList<>();
        newDS.add(this.DataSet.get(0));
        for (int i = 0; i < this.dsMatrix.size(); i++) {
            if (this.dsMatrix.get(i).get(columna) instanceof Double && this.dsMatrix.get(i).get(columna)!=valor) {
                newDS.add(this.DataSet.get(i));
            }
        }
        return nuevoArchivo(newDS, columna, valor, metodo);
    }

    public MarcoDeDatos nuevoArchivo(ArrayList<Dato> newDS, int columna, Double valor, String metodo) {
        ArrayList<ArrayList<Object>> newDSMatrix = new ArrayList<>();
        for (int i = 0; i < newDS.size(); i++) {
            newDSMatrix.add(new ArrayList<>());
            newDSMatrix.get(i).add(newDS.get(i).getStation());
            newDSMatrix.get(i).add(newDS.get(i).getName());
            newDSMatrix.get(i).add(newDS.get(i).getDate());
            newDSMatrix.get(i).add(newDS.get(i).getPrcp());
            newDSMatrix.get(i).add(newDS.get(i).getTavg());
            newDSMatrix.get(i).add(newDS.get(i).getTmax());
            newDSMatrix.get(i).add(newDS.get(i).getTmin());
        }
        FileWriter myWriter;
        String name=null;
        try {
            name = this.getNombreArchivo().substring(0, this.getNombreArchivo().length()-4) + newDSMatrix.get(0).get(columna)+metodo+valor+".csv";
            myWriter = new FileWriter(name);
            for (int i = 0; i < newDSMatrix.size(); i++) {
                for (int j = 0; j < newDSMatrix.get(i).size(); j++) {
                    myWriter.write("\"" + newDSMatrix.get(i).get(j) +"\",");
                }
                myWriter.write("\n");
            }
            myWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new MarcoDeDatos(name);
    }

}
