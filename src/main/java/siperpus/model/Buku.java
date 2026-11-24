/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package siperpus.model;

/**
 *
 * @author Dany
 */
public class Buku {
    private final String isbn;
    private final String barcodeBuku;
    private final String judul;
    private int stok;

    public Buku(String isbn, String barcodeBuku, String judul, int stok) {
        if (isbn == null || isbn.isBlank() || barcodeBuku == null || barcodeBuku.isBlank()) {
            throw new IllegalArgumentException("ISBN dan Barcode Buku wajib diisi.");
        }
        if (stok < 0) {
            throw new IllegalArgumentException("Stok awal buku tidak boleh negatif.");
        }
        this.isbn = isbn;
        this.barcodeBuku = barcodeBuku;
        this.judul = judul;
        this.stok = stok;
    }

    public boolean isTersedia() {
        return stok > 0;
    }

    public void kurangiStok(int jumlah) {
        if (jumlah <= 0) {
            throw new IllegalArgumentException("Jumlah peminjaman harus lebih dari 0.");
        }
        if (this.stok < jumlah) {
            throw new IllegalStateException("Stok buku '" + judul + "' tidak mencukupi (Sisa: " + stok + ").");
        }
        this.stok -= jumlah;
    }

    public void tambahStok(int jumlah) {
        if (jumlah <= 0) {
            throw new IllegalArgumentException("Jumlah pengembalian harus lebih dari 0.");
        }
        this.stok += jumlah;
    }

    public String getIsbn() {
        return isbn;
    }

    public String getBarcodeBuku() {
        return barcodeBuku;
    }

    public String getJudul() {
        return judul;
    }

    public int getStok() {
        return stok;
    }
}
