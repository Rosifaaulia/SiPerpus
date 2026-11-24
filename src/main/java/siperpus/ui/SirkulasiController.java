/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package siperpus.ui;

/**
 *
 * @author Dany
 */
import siperpus.barcode.PembacaBarcode;
import siperpus.model.Anggota;
import siperpus.model.Buku;
import siperpus.model.Petugas;
import siperpus.model.RakBuku;
import siperpus.model.TransaksiSirkulasi;
import siperpus.service.SirkulasiService;
import java.time.LocalDate;
import java.util.List;

public class SirkulasiController {
    private final SirkulasiService service;
    private final List<RakBuku> daftarRak;
    private final List<Anggota> daftarAnggota;
    private final Petugas petugasAktif;

    public SirkulasiController(SirkulasiService service, List<RakBuku> daftarRak,
                               List<Anggota> daftarAnggota, Petugas petugasAktif) {
        this.service = service;
        this.daftarRak = daftarRak;
        this.daftarAnggota = daftarAnggota;
        this.petugasAktif = petugasAktif;
    }

    public String scanBarcode() {
        return service.pindaiKodeBarcode();
    }

    public void gantiPerangkatBarcode(PembacaBarcode scannerBaru) {
        service.setScanner(scannerBaru);
    }

    public Anggota cariAnggotaByBarcode(String barcodeKtm) {
        for (Anggota a : daftarAnggota) {
            if (a.getBarcodeKtm().equalsIgnoreCase(barcodeKtm)) {
                return a;
            }
        }
        return null;
    }

    public Buku cariBukuByBarcode(String barcodeBuku) {
        for (RakBuku rak : daftarRak) {
            for (Buku b : rak.getDaftarBuku()) {
                if (b.getBarcodeBuku().equalsIgnoreCase(barcodeBuku)) {
                    return b;
                }
            }
        }
        return null;
    }

    public TransaksiSirkulasi buatPeminjaman(Anggota anggota, List<Buku> bukuDipinjam) {
        return service.prosesPeminjaman(petugasAktif, anggota, bukuDipinjam);
    }

    public TransaksiSirkulasi kembalikanBuku(String idTransaksi, LocalDate tglKembali) {
        return service.prosesPengembalian(idTransaksi, tglKembali);
    }

    public List<RakBuku> getDaftarRak() {
        return daftarRak;
    }

    public List<TransaksiSirkulasi> getRiwayatTransaksi() {
        return service.ambilSemuaTransaksi();
    }

    public Petugas getPetugasAktif() {
        return petugasAktif;
    }
}
