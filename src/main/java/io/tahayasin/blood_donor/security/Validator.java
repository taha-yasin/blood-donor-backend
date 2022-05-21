package io.tahayasin.blood_donor.security;

import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class Validator {
    public boolean ValidateEmail(String email){
        String EMAIL_REGEX = "^(?=.{1,64}@)[A-Za-z0-9_-]+(\\.[A-Za-z0-9_-]+)*@[^-][A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*(\\.[A-Za-z]{2,})$";
        Pattern pattern = Pattern.compile(EMAIL_REGEX, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }


    public boolean ValidateName(String firstname, String lastname) {
        String name = firstname + lastname;
        return  name.matches("[A-Za-z]+");
    }

    public boolean ValidatePassword(String Passwrd, Map<String, String> pass_msg){

//        TODO add regex restrictions for ""''<>/.&^*():; in Password.
//

        Pattern SpecialCase = Pattern.compile("[^a-z0-9]", Pattern.CASE_INSENSITIVE);
        Pattern UpperCase = Pattern.compile("[A-Z]");
        Pattern LowerCase = Pattern.compile("[a-z]");
        Pattern NumericCase = Pattern.compile("[0-9]");

        boolean flag = true;

        if (Passwrd.isEmpty()) {
            pass_msg.put("errorMsg", "Password must not be empty");
            flag = false;
        }
        else if (Passwrd.length() < 8) {
            pass_msg.put("errorMsg", "Password length must have at-least 8 character");
            flag = false;
        }
        else if (!SpecialCase.matcher(Passwrd).find()) {
            pass_msg.put("errorMsg", "Password must have at-least one Special character");
            flag = false;
        }
        else if (!UpperCase.matcher(Passwrd).find()) {
            pass_msg.put("errorMsg", "Password must have at-least one Uppercase character");
            flag = false;
        }
        else if (!LowerCase.matcher(Passwrd).find()) {
            pass_msg.put("errorMsg", "Password must have at-least one Lowercase character");
            flag = false;
        }
        else if (!NumericCase.matcher(Passwrd).find()) {
            pass_msg.put("errorMsg", "Password must have at-least one Digit character");
            flag = false;
        }

        return flag;
    }
}
