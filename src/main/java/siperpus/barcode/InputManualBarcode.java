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

public class InputManualBarcode implements PembacaBarcode {
    private final Scanner inputStream;

    public InputManualBarcode(Scanner inputStream) {
        this.inputStream = inputStream;
    }

    @Override
    public String pindaiKode() {
        System.out.print("[Input Manual - Ketik kode barcode KTM/Buku]: ");
        return inputStream.nextLine().trim();
    }
}
