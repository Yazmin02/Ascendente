import java.util.List;
import java.util.Stack;

public class Parser {

    static Stack<Token> PilaToken = new Stack<Token>();
    static Stack<Integer> PilaEstado = new Stack<Integer>();
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
    private int i = 0;
    private boolean hayErrores = false;

    private Token preanalisis;

    public Parser(List<Token> tokens) {
        this.tokens = tokens;
    }

    public void parse() {
        i = 0;
        preanalisis = tokens.get(i);
        Parser();

        if (!hayErrores && !preanalisis.equals(finCadena)) {
            System.out.println(
                    "Error en la posición " + preanalisis.posicion + ". No se esperaba el token " + preanalisis.tipo);
        } else if (!hayErrores && preanalisis.equals(finCadena)) {
            System.out.println("Consulta válida");
        }
    }

    void errorToken() {
        if (hayErrores)
            return;
        System.out.println(
                "Error en la posición " + preanalisis.posicion + ". No se esperaba el token " + preanalisis.tipo);
    }

    void shift(int siguiente_estado) {
        PilaToken.push(preanalisis);
        PilaEstado.push(siguiente_estado);
        preanalisis = tokens.get(i + 1);
    }

    void Parser() {
        PilaToken.push(finCadena);
        PilaEstado.push(0);
        boolean control = false;
        while (control == false) {
            int estadodePila = PilaEstado.peek();
            if (preanalisis.equals(select)) {//
                if (estadodePila == 2) {// se compara con el estado en el cual esta la instruccion
                    shift(0); // depende si se llama el shift o reduccion y se pone el numero de la produccion
                }
            }
            if (preanalisis.equals(identificador)) {
                if (estadodePila == 2 || estadodePila == 4) {
                    shift(8);
                } else if (estadodePila == 10 || estadodePila == 21) {
                    shift(17);
                } else if (estadodePila == 15) {
                    shift(18);
                } else if (estadodePila == 17) {
                    shift(22);
                }
            }
            if (preanalisis.equals(from)) {
                if (estadodePila == 3) {
                    shift(10);
                } else if (estadodePila == 5) {
                    Reduccion(4);
                } else if (estadodePila == 6) {
                    Reduccion(5);
                } else if (estadodePila == 9) {
                    Reduccion(3);
                } else if (estadodePila == 11) {
                    Reduccion(2);
                } else if (estadodePila == 12) {
                    Reduccion(6);
                } else if (estadodePila == 13) {
                    Reduccion(9);
                } else if (estadodePila == 18) {
                    Reduccion(10);
                }
            }
            if (preanalisis.equals(distinct)) {//
                if (estadodePila == 2) {
                    shift(4);
                }
            }
            if (preanalisis.equals(asterisco)) {
                if (estadodePila == 2 || estadodePila == 4) {
                    shift(5);
                }
            }
            if (preanalisis.equals(coma)) {
                if (estadodePila == 7) {
                    shift(7);
                } else if (estadodePila == 13) {
                    Reduccion(9);
                } else if (estadodePila == 16) {
                    shift(21);
                } else if (estadodePila == 18) {
                    Reduccion(10);
                } else if (estadodePila == 20) {
                    Reduccion(15);
                } else if (estadodePila == 22) {
                    Reduccion(16);
                }
            }
            if (preanalisis.equals(punto)) {
                if (estadodePila == 8) {
                    shift(15);
                }
                if (preanalisis.equals(finCadena)) {
                    if (estadodePila == 1) {
                        System.out.println("La cadena es valida");
                    } else if (estadodePila == 14) {
                        Reduccion(1);
                    } else if (estadodePila == 19) {
                        Reduccion(12);
                    } else if (estadodePila == 20) {
                        Reduccion(15);
                    } else if (estadodePila == 22) {
                        Reduccion(16);
                    } else if (estadodePila == 23) {
                        Reduccion(13);
                    }
                }

                if (control == true) {
                    for (int j = 1; j < PilaToken.size(); j++) {
                        System.out.print(PilaToken.elementAt(j) + ":" + PilaEstado.elementAt(j));
                    }
                }
            }
        }
    }

    void InvalidString() {
        System.out.println("Lo siento, ¡la expresión que ingresaste NO ES VÁLIDA! =(");
        System.exit(-1);
    }

    // Función que maneja las reducciones
    void Reduccion(int regla) {
        switch (regla) {
            case 1: {
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
            case 4: {

            }
            case 5: {

            }
            case 3: {

            }
            case 2: {

            }
            case 6: {

            }
            case 9: {

            }
            case 10: {

            }
            case 12: {

            }
            case 13: {

            }
            case 15: {

            }
            case 17: {

            }
            case 16: {

            }

        }
    }

}
