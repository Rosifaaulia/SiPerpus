/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package siperpus.repository;

/**
 *
 * @author Dany
 */
import siperpus.model.TransaksiSirkulasi;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MemoryTransaksiRepository implements TransaksiRepository {
    private final List<TransaksiSirkulasi> dataTransaksi = new ArrayList<>();

    @Override
    public void simpan(TransaksiSirkulasi transaksi) {
        if (transaksi != null && cariById(transaksi.getIdTransaksi()) == null) {
            dataTransaksi.add(transaksi);
        }
    }

    @Override
    public TransaksiSirkulasi cariById(String id) {
        for (TransaksiSirkulasi t : dataTransaksi) {
            if (t.getIdTransaksi().equalsIgnoreCase(id)) {
                return t;
            }
        }
        return null;
    }

    @Override
    public List<TransaksiSirkulasi> ambilSemua() {
        return Collections.unmodifiableList(dataTransaksi);
    }
}