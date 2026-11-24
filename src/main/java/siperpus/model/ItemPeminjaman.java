/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package siperpus.model;

/**
 *
 * @author Dany
 */
public class ItemPeminjaman {
    private final Buku buku;
    private final int jumlah;

    ItemPeminjaman(Buku buku, int jumlah) {
        if (buku == null) {
            throw new IllegalArgumentException("Buku tidak boleh null.");
        }
        if (jumlah <= 0) {
            throw new IllegalArgumentException("Jumlah item harus > 0.");
        }
        this.buku = buku;
        this.jumlah = jumlah;
    }

    public int getJumlah() {
        return jumlah;
    }

    public Buku getBuku() {
        return buku;
    }
}