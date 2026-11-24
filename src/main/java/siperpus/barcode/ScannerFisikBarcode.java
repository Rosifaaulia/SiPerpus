/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package siperpus.barcode;

/**
 *
 * @author Dany
 */
import java.util.Scanner;

public class ScannerFisikBarcode implements PembacaBarcode {
    private final String portPerangkat;
    private final Scanner inputStream;

    public ScannerFisikBarcode(String portPerangkat, Scanner inputStream) {
        this.portPerangkat = portPerangkat;
        this.inputStream = inputStream;
    }

    @Override
    public String pindaiKode() {
        System.out.print("[USB Scanner @ " + portPerangkat + " - Silakan tembak barcode]: ");
        return inputStream.nextLine().trim();
    }
}
