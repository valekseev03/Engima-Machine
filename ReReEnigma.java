public class ReReEnigma {
    private String[] storedRotors = new String[] {
          "EKMFLGDQVZNTOWYHXUSPAIBRCJ",
          "AJDKSIRUXBLHWTMCQGZNPYFVOE",
          "BDFHJLCPRTXVZNYEIWGAKMUSQO",
          "ESOVPZJAYQUIRHXLNFTGKDCMWB",
          "VZBRGITYUPSDNHLXAWMJQOFECK",
          "JPGVOUMFYQBENHZRDKASXLICTW",
          "NZJHGRCXMYSWBOUFAIVLPEKQDT",
          "JPGVOUMFYQBENHZRDKASXLICTW"          
    };
    
    private int[] rotors = new int[3];
    private char[] plugboard = new char[26];
    private char[] alphabet = new char[] {'A','B','C','D','E','F','G','H','I','J','K','L','M','N','O','P','Q','R','S','T','U','V','W','X','Y','Z'};
    private String key = "";
    private String reflector  = "YRUHQSLDPXNGOKMIEBFZCWVJAT";
    private String currentplugboard = "YRUHQSLDPXNGOKMIEBFZCWVJAT";
    
    public ReReEnigma() {
        
    }
    
    public String encrypt(String message, boolean showtext) {
        String newmessage = "";
        char[] letter = new char[message.length()];     
        decreaseRotors(message);
        
        for(int i = 0; i < letter.length; i ++) {           
            letter[i] = message.substring(i, i+1).charAt(0);
            
            if(letter[i] == ' ') {
                increaseRotors();               
            }else {
                increaseRotors();
                letter[i] = reflect(letter[i], currentplugboard);
                letter[i] = replaceLetters(letter[i]);
                letter[i] = reflect(letter[i], reflector);
                letter[i] = reversereplaceLetters(letter[i]);       
                letter[i] = reflect(letter[i], currentplugboard);
            }
            newmessage += letter[i];
        }
        
        if(showtext) {
            key = rotors [0] + " " + rotors [1] + " " + rotors [2];
            System.out.println("Encrypted Message: [" + newmessage +  "]; Key: [" + key + "];");
        }
        
        return newmessage;      
    }
    
    private char replaceLetters(char c) {
        char output = c;
        
        for(int j = 0; j < 3; j++) {    
            setPlugboard(storedRotors[rotors[j] - 1]);          
            for(int i = 0; i < plugboard.length; i++) {
                if(alphabet[i] == output) {
                    output = plugboard[i];
                    break;
                }
            }
        }
        
        return output;
    }
    
    private char reversereplaceLetters(char c) {
        char output = c;
        
        for(int j = 0; j < 3; j++) {    
            setPlugboard(storedRotors[rotors[2-j] - 1]);            
            for(int i = 0; i < plugboard.length; i++) {
                if(plugboard[i] == output) {
                    output = alphabet[i];
                    break;
                }
            }
        }
        
        return output;
    }
    
    private char reflect(char c, String newreflector) {
        char output = c;
            
        setPlugboard(newreflector);
        for(int i = 0; i < plugboard.length; i++) {
            if(alphabet[i] == output) {
                output = plugboard[i];
                break;
            }
        }
        
        return output;
    }
    
    //Rotors
    public void setRotors(int first_rotor, int middle_rotor, int last_rotor) {
        if(first_rotor > 8) {
            first_rotor = 8;
        }
        
        if(first_rotor < 1) {
            first_rotor = 1;
        }
        
        if(middle_rotor > 8) {
            middle_rotor = 8;
        }
        
        if(middle_rotor < 1) {
            middle_rotor = 1;
        }
        
        if(last_rotor > 8) {
            last_rotor = 8;
        }
        
        if(last_rotor < 1) {
            last_rotor = 1;
        }
        
        rotors = new int[]{first_rotor, middle_rotor, last_rotor};
    }
    
    private void decreaseRotors(String message) {
        for(int j =0; j < message.length(); j++) {
            if(rotors[2] > 1) {
                rotors[2]--;
                
            }else if (rotors[1] > 1) {
                rotors[1]--;
                rotors[2] = 8;
                
            }else if(rotors[0] > 1) {
                rotors[2] = 8;
                rotors[1] = 8;
                rotors[0]--;
            }else {
                rotors[0] = 8;
                rotors[1] = 8;
                rotors[2] = 8;
            }   
        }
        
    }
    
    private void increaseRotors() {
        if(rotors[2] < 8) {
            rotors[2]++;    
            
        }else if (rotors[1] < 8) {
            rotors[1]++;
            rotors[2] = 1;
            
        }else if(rotors[0] < 8) {
            rotors[2] = 1;
            rotors[1] = 1;
            rotors[0]++;
            
        }else {
            rotors[0] = 1;
            rotors[1] = 1;
            rotors[2] = 1;
        }
    }
    
    //Plugboard
    private void setPlugboard(String newplugboard) {
        for(int i = 0; i < plugboard.length; i++) {         
            plugboard[i] = newplugboard.substring(i,i+1).trim().charAt(0);  
        }
    }
    
    public void setPlugboard(String new_plugboard, boolean showtext) {
        currentplugboard = new_plugboard;
        if(showtext) {
            System.out.println(currentplugboard);
        }
    }
    
    public static void main(String[] args) {
        ReReEnigma enigma = new ReReEnigma();
        enigma.setRotors(8, 8, 8);
        enigma.setPlugboard("FVPJIAOYEDRZXWGCTKUQSBNMHL", false);
        
        enigma.encrypt("GUESS WHAT THE NEXT TWO LINES WILL SAY", true);
        enigma.encrypt("HINT THEYRE STAR WARS QUOTES", true);
        
        enigma.encrypt("MFAOH OTXCX", true);
        enigma.encrypt("XFRROLO DRLFED", true);
        }
}