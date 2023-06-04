import java.util.List;
import java.util.Stack;

public class Parser {

    static Stack<Token> PilaToken = new Stack<>();
    static Stack<Integer> PilaEstado = new Stack<>();
    private final List<Token> tokens;

    private final Token identificador = new Token(TipoToken.IDENTIFICADOR, "");
    private final Token select = new Token(TipoToken.SELECT, "select");
    private final Token from = new Token(TipoToken.FROM, "from");
    private final Token distinct = new Token(TipoToken.DISTINCT, "distinct");
    private final Token coma = new Token(TipoToken.COMA, ",");
    private final Token punto = new Token(TipoToken.PUNTO, ".");
    private final Token asterisco = new Token(TipoToken.ASTERISCO, "*");
    private final Token finCadena = new Token(TipoToken.EOF, "");
    private final Token Q = new Token(TipoToken.AUX, "Q");
    private final Token D = new Token(TipoToken.AUX, "D");
    private final Token P = new Token(TipoToken.AUX, "P");
    private final Token A = new Token(TipoToken.AUX, "A");
   // private final Token A1 = new Token(TipoToken.AUX, "A1");
    private final Token A2 = new Token(TipoToken.AUX, "A2");
    private final Token A3 = new Token(TipoToken.AUX, "A3");
    private final Token T = new Token(TipoToken.AUX, "T");
    private final Token T1 = new Token(TipoToken.AUX, "T1");
    private final Token T2 = new Token(TipoToken.AUX, "T2");
    private final Token T3 = new Token(TipoToken.AUX, "T3");

    private int i = 0;

    private boolean hayErrores = false;
    private int estadodePila=0;
    private Token preanalisis;

    public Parser(List<Token> tokens) {
        this.tokens = tokens;
    }

    public void parse() {
        i = 0;
        preanalisis = tokens.get(i);
        Analizar();
        if (!hayErrores && !preanalisis.equals(finCadena)) {
            System.out.println(
                    "Error en la posición " + preanalisis.posicion + ". No se esperaba el token " + preanalisis.tipo);
        } else if (!hayErrores && preanalisis.equals(finCadena)) {
            System.out.println("Consulta válida");
        }

    }

    void errorToken() {
        hayErrores= true;
    }

    void shift(int siguiente_estado) {

        PilaToken.push(preanalisis);
        PilaEstado.push(siguiente_estado);
        i++;
        preanalisis = tokens.get(i);

    }

    void Analizar() {
        PilaToken.push(finCadena);
        PilaEstado.push(0);

        while (!hayErrores || !preanalisis.equals(finCadena)) {

             estadodePila = PilaEstado.peek();
            if (preanalisis.equals(select)) {//
                if (estadodePila == 0) {// se compara con el estado en el cual esta la instruccion
                    shift(2); // depende si se llama el shift o reduccion y se pone el numero de la produccion
                }else errorToken();
            } else if (preanalisis.equals(identificador)) {
                if (estadodePila == 2 || estadodePila == 4) {
                    shift(8);
                }
                if (estadodePila == 10 || estadodePila == 21) {
                    shift(17);
                }
                if (estadodePila == 15) {
                    shift(18);
                }
                if (estadodePila == 17) {
                    shift(22);
                }else errorToken();
            } else if (preanalisis.equals(from)) {
                if (estadodePila == 3) {
                    shift(10);
                }else
                if (estadodePila == 5) {
                    Reduccion(4);
                }else
                if (estadodePila == 6) {
                    Reduccion(5);
                }else
                if (estadodePila == 9) {
                    Reduccion(3);
                }else
                if (estadodePila == 11) {
                    Reduccion(2);
                }else
                if (estadodePila == 12) {
                    Reduccion(6);
                }else
                if (estadodePila == 13) {
                    Reduccion(9);
                }else
                if (estadodePila == 18) {
                    Reduccion(10);
                }else errorToken();
            } else if (preanalisis.equals(distinct)) {//
                if (estadodePila == 2) {
                    shift(4);
                }else errorToken();
            } else if (preanalisis.equals(asterisco)) {
                if (estadodePila == 2) {
                    shift(5);
                }else
                if (estadodePila == 4) {
                    shift(5);
                }else errorToken();
            } else if (preanalisis.equals(coma)) {
                if (estadodePila == 7) {
                    shift(7);
                }
                if (estadodePila == 13) {
                    Reduccion(9);
                }
                if (estadodePila == 16) {
                    shift(21);
                }
                if (estadodePila == 18) {
                    Reduccion(10);
                }
                if (estadodePila == 20) {
                    Reduccion(15);
                }
                if (estadodePila == 22) {
                    Reduccion(16);
                }else errorToken();
            } else if (preanalisis.equals(punto)) {
                if (estadodePila == 8) {
                    shift(15);
                }else errorToken();
            }else
            if (preanalisis.equals(finCadena)) {
                if (estadodePila == 1) {
                    System.out.println("La cadena es valida");
                    hayErrores = false;
                }else
                if (estadodePila == 14) {
                    Reduccion(1);
                }else
                if (estadodePila == 19) {
                    Reduccion(12);
                }else
                if (estadodePila == 20) {
                    Reduccion(15);
                }else
                if (estadodePila == 22) {
                    Reduccion(16);
                }else
                if (estadodePila == 23) {
                    Reduccion(13);
                }else errorToken();

                if (!hayErrores) {
                    for (int j = 1; j < PilaToken.size(); j++) {
                        System.out.print(PilaToken.elementAt(j) + ":" + PilaEstado.elementAt(j));
                    }

                }
                if(hayErrores)return;
            }
        }
    }

    // Función que maneja las reducciones
    void Reduccion(int regla) {
        switch (regla) {
            case 1 -> {
                // Q-> select D from T
                PilaToken.pop();
                PilaToken.pop();
                PilaToken.pop();
                PilaToken.pop();
                PilaEstado.pop();
                PilaEstado.pop();
                PilaEstado.pop();
                PilaEstado.pop();
                if (PilaEstado.peek() == 0) { // aqui se compara con el estado
                    PilaToken.push(Q);
                    PilaEstado.push(1); // el numero que se ingresa es el numero de su produccion
                }
            }
            case 4 -> {
                // P->*
                PilaToken.pop();
                PilaEstado.pop();
                if (PilaEstado.peek() == 2) { // aqui se compara con el estado
                    PilaToken.push(P);
                    PilaEstado.push(9); // el numero que se ingresa es el numero de su produccion
                } else if (PilaEstado.peek() == 4) { // aqui se compara con el estado
                    PilaToken.push(P);
                    PilaEstado.push(11); // el numero que se ingresa es el numero de su produccion
                }
            }
            case 5 -> {
                // P->A
                PilaToken.pop();
                PilaEstado.pop();
                if (PilaEstado.peek() == 2) { // aqui se compara con el estado
                    PilaToken.push(P);
                    PilaEstado.push(9); // el numero que se ingresa es el numero de su produccion
                } else if (PilaEstado.peek() == 4) { // aqui se compara con el estado
                    PilaToken.push(P);
                    PilaEstado.push(11); // el numero que se ingresa es el numero de su produccion
                }
            }
            case 3 -> {
                // D->P
                PilaToken.pop();
                PilaEstado.pop();
                if (PilaEstado.peek() == 2) { // aqui se compara con el estado
                    PilaToken.push(D);
                    PilaEstado.push(3);
                }
            }
            case 2 -> {
                // D-> distinct P
                PilaToken.pop();
                PilaToken.pop();
                PilaEstado.pop();
                PilaEstado.pop();
                if (PilaEstado.peek() == 2) { // aqui se compara con el estado
                    PilaToken.push(D);
                    PilaEstado.push(3);
                }
            }
            case 6 -> {
                // A->A2A1
                PilaToken.pop();
                PilaToken.pop();
                PilaEstado.pop();
                PilaEstado.pop();
                if (PilaEstado.peek() == 2 || PilaEstado.peek() == 4) { // aqui se compara con el estado
                    PilaToken.push(A);
                    PilaEstado.push(6);
                }
            }
            case 9 -> {
                // A2->idA3
                PilaToken.pop();
                PilaToken.pop();
                PilaEstado.pop();
                PilaEstado.pop();
                if (PilaEstado.peek() == 2 || PilaEstado.peek() == 4) { // aqui se compara con el estado
                    PilaToken.push(A2);
                    PilaEstado.push(7);
                }
            }
            case 10 -> {
                // A3->.id
                PilaToken.pop();
                PilaToken.pop();
                PilaEstado.pop();
                PilaEstado.pop();
                if (PilaEstado.peek() == 8) { // aqui se compara con el estado
                    PilaToken.push(A3);
                    PilaEstado.push(13);
                }
            }
            case 12 -> {
                // T-> T2T1
                PilaToken.pop();
                PilaToken.pop();
                PilaEstado.pop();
                PilaEstado.pop();
                if (PilaEstado.peek() == 10) { // aqui se compara con el estado
                    PilaToken.push(T);
                    PilaEstado.push(14);
                } else if (PilaEstado.peek() == 21) { // aqui se compara con el estado
                    PilaToken.push(D);
                    PilaEstado.push(23);
                }
            }
            case 13 -> {
                // T1->,T
                PilaToken.pop();
                PilaToken.pop();
                PilaEstado.pop();
                PilaEstado.pop();
                if (PilaEstado.peek() == 16) { // aqui se compara con el estado
                    PilaToken.push(T1);
                    PilaEstado.push(19);
                }
            }
            case 15 -> {
                // T2->idT3
                PilaToken.pop();
                PilaToken.pop();
                PilaEstado.pop();
                PilaEstado.pop();
                if (PilaEstado.peek() == 10 || PilaEstado.peek() == 21) { // aqui se compara con el estado
                    PilaToken.push(T2);
                    PilaEstado.push(16);
                }
            }
            case 17 -> {
                // T3->E
                PilaToken.pop();
                PilaEstado.pop();
                if (PilaEstado.peek() == 17) { // aqui se compara con el estado
                    PilaToken.push(T3);
                    PilaEstado.push(20);
                }
            }
            case 16 -> {
                // T3->id
                PilaToken.pop();
                PilaEstado.pop();
                if (PilaEstado.peek() == 17) { // aqui se compara con el estado
                    PilaToken.push(T3);
                    PilaEstado.push(20);
                }
            }
        }
        estadodePila= PilaEstado.peek();
    }

}
