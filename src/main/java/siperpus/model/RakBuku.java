/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package siperpus.model;

/**
 *
 * @author Dany
 */
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class RakBuku {
    private final String kodeRak;
    private final String lokasi;
    private final List<Buku> daftarBuku = new ArrayList<>();

    public RakBuku(String kodeRak, String lokasi) {
        this.kodeRak = kodeRak;
        this.lokasi = lokasi;
    }

    public void tambahBuku(Buku b) {
        if (b != null && !daftarBuku.contains(b)) {
            daftarBuku.add(b);
        }
    }

    public void hapusBuku(String isbn) {
        daftarBuku.removeIf(b -> b.getIsbn().equalsIgnoreCase(isbn));
    }

    public List<Buku> getDaftarBuku() {
        return Collections.unmodifiableList(daftarBuku);
    }

    public String getKodeRak() {
        return kodeRak;
    }

    public String getLokasi() {
        return lokasi;
    }
}
