import java.util.*;

public class NestedIfElseCheckConditionsElse {
    // Whitespace
    String whiteSpace = "(([\\s] *) | ((?s). *[\\n\\r].) *)";
    // Separators
    static Set<String> separators = new HashSet<>(Arrays.asList("=", "(", ")", "{", "}", ",", ";"));
    // <Comparison operators>
    static Set<String> comparisonOperators = new HashSet<>(Arrays.asList("==", "!=", ">=", ">=", "!=", ">", "<"));
    // <logical operators>
    static Set<String> logicalOperators = new HashSet<>(Arrays.asList("&&", "||"));
    // Valid <values>
    static Set<String> values = new HashSet<>(Arrays.asList("integer", "double", "float", "'char'", "variable"));

    static String[] arrayInput;

    public static void main(String[] args) {
        // START OF NO ERROR INPUTS
        // NO NESTING
        // String input = "if ( x == 10.1 ) { print(); } else { print; }";

        // NESTED ONCE
        String input = "if ( x == 10.1 ) { if ( y > 0 || y < 1 ) { print(); } else { print; } } else { print; }";

        // NESTED TWICE
        // String input = "if ( x == 10.1 ) { if ( y > 0 || y < 1 ) { print(); } else { if ( x == 0 ) { print(); } else { print(); } print(); } } else { print; }";
        // END OF NO ERROR INPUTS

        // START OF ERROR INPUTS
        // No Space between tokens
        // String input = "if ( x ==10.1 ) { if ( y > 0 || y < 1 ) { print(); } else { print(); } } else { print; }";

        // Not following condition rules
        // String input = "if ( x == 10.1 ) { if ( y > 0 || 10 ) { print(); } else { print(); } } else { print; }";

        // Incorrect statement inside If Statement
        // String input = "if ( x == 10.1 ) { if ( y > 0 || y < 1 ) { 10; } else { if ( x == 0 ) { print(); } else { print(); } print(); } } else { print; }";

        // Imbalanced parentheses and/or curly brackets
        // String input = "if ( x == 10.1 ) { if ( y > 0 || y < 1 ) { print(); } else { if ( y > 0 ) { print(); } } } } else { print(); }";

        // Incorrect sequence for if else
        // String input = "if ( x == 10.1 ) { else ( y > 0 || y < 1 ) { print(); } else { if ( y > 0 ) { print(); } } } else { print(); }";

        // END OF ERROR INPUTS

        // START OF UNRECOGNIZABLE ERROR INPUTS
        // the "true" and "false" should be invalid with the comparison operators
        // String input = "if ( x == 10.1 ) { if ( y > true || y < false ) { print(); } else { print; } } else { print; }";
        
        // null, true, and false are treated as variable names
        // String input = "if ( x == true ) { if ( y == null || y == false ) { print(); } else { print; } } else { print; }";

        // "Else if" is not recognized and will throw an error (not sure dito)
        // String input = "if ( x == 10.1 ) { else if ( y > 0 || y < 1 ) { print(); } else { print; } } else { print; }";
        // END OF UNRECOGNIZABLE ERROR INPUTS

        // START OF PROGRAM
        System.out.println("=================================================");
        System.out.println("Input:\n" + input);
        // split into array
        arrayInput = input.trim().split("\\s+");
        System.out.println("Input Coverted to String:\n" + Arrays.toString(arrayInput));

        // pass to tokenizer
        String[] tokenized = tokenize(arrayInput);
        System.out.println("Tokenized Input:\n" + Arrays.toString(tokenized));
        System.out.println("=================================================");

        // Check For invalid tokens && Check Proper Sequence && Check If, else, and condition statements
        if(!checkTokens(tokenized) || !checkSequence(tokenized) || !checkIfStatement(tokenized)){
            System.out.println("=================================================");
            System.out.println("Incorrect Syntax for Nested If-Else!");
        }else{
            System.out.println("=================================================");
            System.out.println("Correct Overall Syntax for Nested If-Else!");
        }
    }

    // Checks the syntax of ALL if statements
    public static boolean checkIfStatement(String[] token) {
        boolean syntax = true;
        for (int i = 0; i < token.length; i++) {
            if (syntax == false) {
                return syntax;
            }
            if (token[i].equals("if")) {
                syntax = checkConditions(token, i);
                syntax = checkStatement(token, i);
            }
            if (token[i].equals("else")) {
                syntax = checkElseCondition(token, i + 2);
            }
            if (logicalOperators.contains(token[i])) {
                syntax = checkConditions(token, i - 1);
            }
        }
        System.out.println("Correct If-Else Statement Formatting");
        return syntax;
    }

    public static boolean checkStatement(String[] token, int i){
        for (; i<token.length; i++){
            if(token[i].equals("{")){
                if (token[i+1].equals("statement")) {
                    return true;
                }
                if (token[i+1].equals("if")){
                    return true;
                }
                System.out.println("Invalid Statement at index " + (i+1) + " | Incorrect input: " + arrayInput[i+1]);
                return false;
            }
        }
        return false;
    }

    public static boolean checkElseCondition(String[] token, int i) {
        if (token[i].equals("statement")) {
            return true;
        }
        if (token[i].equals("if")) {
            return checkConditions(token, i);
        }
        System.out.println("Incorrect Else Structure at index " + i + " | Incorrect input: " + arrayInput[i]);
        return false;
    }

    // Checks if the syntax of all the conditions are correct
    public static boolean checkConditions(String[] token, int i) {
        // System.out.print("[" + token[i + 1] + "," + token[i + 2] + "," + token[i + 3]
        // + ", " + token[i + 4] + ", " + token[i + 5] + "]\n");
        if ((!logicalOperators.contains(token[i + 1]) && !token[i + 1].equals("(")) || // either ( or logical operator
                !values.contains(token[i + 2]) || // value
                !comparisonOperators.contains(token[i + 3]) || // comparison operator
                !values.contains(token[i + 4]) || // value
                (!logicalOperators.contains(token[i + 5]) && !token[i + 5].equals(")"))) {
            System.out.println(
                    "Incorrect Condition Structure at index " + (i + 2) + " | Incorrect input: " + arrayInput[i + 2]);
            return false;
        }
        System.out.println("Correct Condition Structure at index " + (i + 2));
        return true;
    }

    public static String[] tokenize(String[] input) {
        ArrayList<String> tokenList = new ArrayList<>();
        for (int i = 0; i < input.length; i++) {
            // if else literals
            if (input[i].equals("if")) {
                tokenList.add("if");
            } else if (input[i].equals("else")) {
                tokenList.add("else");
                // separators and operators
            } else if (separators.contains(input[i]) || comparisonOperators.contains(input[i])
                    || logicalOperators.contains(input[i])) {
                tokenList.add(input[i]);
                // number encountered (integer / decimal)
            } else if (input[i].matches("-?\\d+")) {
                tokenList.add("integer");
            } else if (input[i].matches("-?\\d+(\\.\\d+)?")) {
                tokenList.add("double");
                // characters encountered; will need to expand this to detect more errors
            } else if (input[i].matches("[a-zA-Z0-9_]+")) {
                if (input[i].length() == 1)
                    tokenList.add("variable");
                else // needs variablename validation
                    tokenList.add("variable");
                // single quote encountered (char value)
            } else if (input[i].startsWith("'") && input[i].endsWith("'") && input[i].length() == 3) {
                tokenList.add("'char'");
            } else if (input[i].trim().endsWith(";")) {
                tokenList.add("statement");
            } else
                tokenList.add("invalid");
        }
        return tokenList.toArray(new String[0]);
    }

    // Returns true if no invalid tokens, otherwise false
    public static boolean checkTokens(String[] tokens){
        for(int i = 0; i < tokens.length ; i++){
            if(tokens[i].equals("invalid")){
                System.out.println("Invalid Token at index " + i + ". Follow Proper Syntax.");
                return false;
            }
        }
        return true;
    }

    // check for both balanced parentheses/curly braces and the proper nesting of
    // if-else
    public static boolean checkSequence(String[] token) {
        // Initialize
        boolean isBalanced = true;
        Stack<String> braceStack = new Stack<String>();

        // Iterate through the tokens
        for (int i = 0; i < token.length; i++) {
            if (token[i].equals("(") || token[i].equals("{")) {
                // Push opening parentheses and curly braces onto the stack
                braceStack.push(token[i]);
            } else if (token[i].equals(")") || token[i].equals("}")) {
                if (braceStack.empty()) {
                    // If there's no corresponding opening brace, set isBalanced to false
                    isBalanced = false;
                } else {
                    String top = braceStack.pop();
                    // Check if the closing brace matches the top of the stack
                    if ((token[i].equals(")") && !top.equals("(")) || (token[i].equals("}") && !top.equals("{"))) {
                        isBalanced = false;
                    }
                }
            }
        }

        // Check for any leftover opening braces
        if (!braceStack.empty()) {
            isBalanced = false;
        }

        if (isBalanced) {
            int ifCount = 0; // Count of "if" tokens
            int elseCount = 0; // Count of "else" tokens
            for (String t : token) {
                if (t.equals("if")) {
                    ifCount++;
                } else if (t.equals("else")) {
                    elseCount++;
                    if (ifCount < elseCount) {
                        // If there's an "else" without a matching "if," set isBalanced to false
                        System.err.println("Incorrect nested if-else sequence");
                        return false;
                    }
                }
            }

            // If the counts of "if" and "else" match, it's a correct sequence
            if (ifCount == elseCount) {
                System.out.println("Correct for () {} Sequence");
                return true;
            } else {
                System.err.println("Incorrect nested if-else sequence");
            }
        } else {
            System.err.println("Unbalanced parentheses or curly braces");
        }

        // You can add an error counter increment here if needed
        return false;
    }

}