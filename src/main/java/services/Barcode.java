package services;

public class Barcode {
    public int ean13ControlCodeCalculator(String code) {
        char[] charDigits = code.toCharArray();
        int[] ean13 = {1, 3};
        int sum = 0;
        for (int i = 0; i < charDigits.length; i++) {
            sum += Character.getNumericValue(charDigits[i]) * ean13[i % 2];
        }
        return (10 - sum % 10) % 10;
    }

    public static void main(String[] args) {
        for (int i = 2; i < 18; i += 2) {
            String base = "8400002";
            if (i < 10) {
                base += "0";
            }
            System.out.println(base + (i) + "000" + new Barcode().ean13ControlCodeCalculator(base + (i) + "000"));
        }
    }

}
