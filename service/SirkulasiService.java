/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package siperpus.service;

/**
 *
 * @author Dany
 */
import siperpus.barcode.PembacaBarcode;
import siperpus.model.Anggota;
import siperpus.model.Buku;
import siperpus.model.Petugas;
import siperpus.model.TransaksiSirkulasi;
import siperpus.repository.TransaksiRepository;
import java.time.LocalDate;
import java.util.List;

public class SirkulasiService {
    private final TransaksiRepository transaksiRepo;
    private PembacaBarcode scanner;
    private int urutanTransaksi = 1;

    public SirkulasiService(TransaksiRepository transaksiRepo, PembacaBarcode scanner) {
        this.transaksiRepo = transaksiRepo;
        this.scanner = scanner;
    }

    public void setScanner(PembacaBarcode scanner) {
        this.scanner = scanner;
    }

    public String pindaiKodeBarcode() {
        return scanner.pindaiKode();
    }

    public TransaksiSirkulasi prosesPeminjaman(Petugas petugas, Anggota anggota, List<Buku> daftarBuku) {
        if (daftarBuku == null || daftarBuku.isEmpty()) {
            throw new IllegalArgumentException("Daftar buku yang dipinjam tidak boleh kosong.");
        }

        String idBaru = String.format("TRX-%03d", urutanTransaksi++);
        TransaksiSirkulasi transaksi = new TransaksiSirkulasi(idBaru, petugas, anggota, LocalDate.now(), 7);

        for (Buku buku : daftarBuku) {
            transaksi.tambahItem(buku, 1);
        }

        transaksiRepo.simpan(transaksi);
        return transaksi;
    }

    public TransaksiSirkulasi prosesPengembalian(String idTransaksi) {
        return prosesPengembalian(idTransaksi, LocalDate.now());
    }

    public TransaksiSirkulasi prosesPengembalian(String idTransaksi, LocalDate tglKembali) {
        TransaksiSirkulasi transaksi = transaksiRepo.cariById(idTransaksi);
        if (transaksi == null) {
            throw new IllegalArgumentException("ID Transaksi '" + idTransaksi + "' tidak ditemukan.");
        }
        transaksi.selesaikanPengembalian(tglKembali);
        return transaksi;
    }

    public List<TransaksiSirkulasi> ambilSemuaTransaksi() {
        return transaksiRepo.ambilSemua();
    }
}
