/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package siperpus.test;

/**
 *
 * @author Dany
 */
import siperpus.model.Anggota;
import siperpus.model.Buku;
import siperpus.model.Petugas;
import siperpus.model.TransaksiSirkulasi;
import siperpus.repository.MemoryTransaksiRepository;
import siperpus.service.SirkulasiService;
import java.time.LocalDate;
import java.util.List;

public class SirkulasiServiceTest {
    public static void main(String[] args) {
        System.out.println("=== MENJALANKAN PENGUJIAN UNIT SIRKULASI PERPUSTAKAAN ===");

        var repo = new MemoryTransaksiRepository();
        var service = new SirkulasiService(repo, () -> "KTM-0074");

        Petugas petugas = new Petugas("PTG-01", "Dany", "admin", "123");
        Anggota anggotaAktif = new Anggota("AGT-01", "Hanif", "2505060074", "KTM-0074", true);
        Anggota anggotaNonAktif = new Anggota("AGT-02", "Gavin", "2505060073", "KTM-0073", false);

        Buku bukuJava = new Buku("978-01", "BK-101", "Java OOP", 1);

        // Skenario 1A: Berhasil meminjam
        TransaksiSirkulasi trx1 = service.prosesPeminjaman(petugas, anggotaAktif, List.of(bukuJava));
        cek("TRX-001".equals(trx1.getIdTransaksi()), "ID transaksi pertama harus TRX-001");
        cek(bukuJava.getStok() == 0, "Stok buku harus berkurang menjadi 0");
        cek(repo.ambilSemua().size() == 1, "Transaksi harus tersimpan di repository");
        System.out.println("[PASS] Uji 1A: Peminjaman berhasil & stok berkurang.");

        // Skenario 1B: Gagal meminjam karena stok habis
        boolean gagalStok = false;
        try {
            service.prosesPeminjaman(petugas, anggotaAktif, List.of(bukuJava));
        } catch (IllegalStateException e) {
            gagalStok = true;
        }
        cek(gagalStok, "Peminjaman harus gagal jika stok buku = 0");
        System.out.println("[PASS] Uji 1B: Peminjaman ditolak saat stok habis.");

        // Skenario 1C: Gagal meminjam karena anggota non-aktif
        boolean gagalAnggota = false;
        try {
            Buku bukuLain = new Buku("978-02", "BK-102", "Clean Code", 5);
            service.prosesPeminjaman(petugas, anggotaNonAktif, List.of(bukuLain));
        } catch (IllegalStateException e) {
            gagalAnggota = true;
        }
        cek(gagalAnggota, "Peminjaman harus gagal jika anggota tidak aktif");
        System.out.println("[PASS] Uji 1C: Peminjaman ditolak untuk anggota non-aktif.");

        // Skenario 2A: Berhasil mengembalikan terlambat 3 hari
        LocalDate tglTerlambat3Hari = LocalDate.now().plusDays(10);
        TransaksiSirkulasi trxKembali = service.prosesPengembalian("TRX-001", tglTerlambat3Hari);
        cek("DIKEMBALIKAN".equals(trxKembali.getStatus()), "Status harus berubah menjadi DIKEMBALIKAN");
        cek(bukuJava.getStok() == 1, "Stok buku harus kembali menjadi 1");
        cek(trxKembali.getDenda() != null && trxKembali.getDenda().getTotalDenda() == 6000, "Denda harus Rp 6.000");
        System.out.println("[PASS] Uji 2A: Pengembalian terlambat menghitung denda Rp 6.000 & memulihkan stok.");

        // Skenario 2B: Gagal mengembalikan transaksi yang sudah dikembalikan
        boolean gagalDoubleReturn = false;
        try {
            service.prosesPengembalian("TRX-001", LocalDate.now());
        } catch (IllegalStateException e) {
            gagalDoubleReturn = true;
        }
        cek(gagalDoubleReturn, "Pengembalian ganda harus ditolak");
        System.out.println("[PASS] Uji 2B: Pengembalian ganda ditolak dengan benar.");

        System.out.println("\nSEMUA SKENARIO UJI LULUS (100%)!");
    }

    private static void cek(boolean kondisi, String pesanError) {
        if (!kondisi) {
            throw new AssertionError("UJI GAGAL: " + pesanError);
        }
    }
}
