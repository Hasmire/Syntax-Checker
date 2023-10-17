import java.util.*;

public class NestedIfElse{
    //Whitespace
    String whiteSpace = "(([\\s] *) | ((?s). *[\\n\\r].) *)";
    //Separators
    static Set<String> separators = new HashSet<>(Arrays.asList("=", "(", ")", "{", "}", ",", ";"));
    //Comparison operators
    static Set<String> comparisonOperators = new HashSet<>(Arrays.asList("==", "!=", ">=", ">=", "!=", ">", "<"));
    //logical operators
    static Set<String> logicalOperators = new HashSet<>(Arrays.asList("&&", "||"));
    //Letters
    static Set<String> letters = new HashSet<>(Arrays.asList(
    "A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N",
    "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z",
    "a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l", "m", "n",
    "o", "p", "q", "r", "s", "t", "u", "v", "w", "x", "y", "z"
    ));

    public static void main(String[] args) {
    String input = "if ( x == 10.1 ) { if ( y > 0 || y < 1 ) { print(); } else { print(); } } else { print; }\n";
    System.out.println("Input:" + input);
    // split into array
    String[] arrayInput = input.trim().split("\\s+");
    System.out.println("Input Coverted to String:\n" +Arrays.toString(arrayInput));
    
    // pass to tokenizer
    String[] tokenized = tokenize(arrayInput);
    System.out.println("\n" + Arrays.toString(tokenized));
    
    //add parsing logic here to analyze structure
    }
    
    public static String[] tokenize(String[] input) {
        ArrayList<String> tokenList = new ArrayList<>();
        for (int i = 0; i < input.length; i++) {
            //if else literals
            if(input[i].equals("if")){ 
                tokenList.add("if");
            } else if(input[i].equals("else")){
                tokenList.add("else");
            //separators and operators
            } else if (separators.contains(input[i]) || comparisonOperators.contains(input[i])
                        || logicalOperators.contains(input[i])) {
                 tokenList.add(input[i]);
            //number encountered (integer / decimal); will need to expand this to detect more errors
            } else if (input[i].matches("-?\\d+") || input[i].matches("-?\\d+(\\.\\d+)?")) {
                tokenList.add("number");
            //characters encountered; will need to expand this to detect more errors
            } else if (input[i].matches("[a-zA-Z0-9_]+")) {
                if(input[i].length() == 1)
                    tokenList.add("variable");
                else //needs variablename validation
                    tokenList.add("variable");
            //char value encountered
            } else if (input[i].startsWith("'") && input[i].endsWith("'") && input[i].length()==3) {
                tokenList.add("'char'");
            } else if(input[i].trim().endsWith(";")){ 
                    tokenList.add("statement");
            } else
                tokenList.add("invalid");
        }
        return tokenList.toArray(new String[0]);
    } 
}