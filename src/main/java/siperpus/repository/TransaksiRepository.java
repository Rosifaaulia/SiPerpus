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
import java.util.List;

public interface TransaksiRepository {
    void simpan(TransaksiSirkulasi transaksi);
    TransaksiSirkulasi cariById(String id);
    List<TransaksiSirkulasi> ambilSemua();
}
