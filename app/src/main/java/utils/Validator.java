package utils;

public class Validator {
    public boolean validateEmail(String email) {
        String regex = "^[\\w\\-.]+@([\\w-]+\\.)+[\\w-]{2,}$";
        return email.matches(regex);
    }

    public boolean validatePassword(String password) {
        String regex = "^((?=\\S*?[A-Z])(?=\\S*?[a-z])(?=\\S*?[0-9])(?=\\S*?[^\\w\\s]).{6,})\\S$";
        return password.matches(regex);
    }

    public boolean validateConfirmPassword(String password, String confirm_password) {
        return password.equals(confirm_password);
    }

    public boolean validatePhoneNumber(String phoneNumber) {
        String regex = "^0\\d{9}$";
        return phoneNumber.matches(regex);
    }

    public boolean validateAddress(String address) {
        int len = address.length();
        return len > 9 && len < 301;
    }

    public boolean validateName(String name) {
        int len = name.length();
        return len > 6 && len < 121;
    }
}
