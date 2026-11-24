/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package siperpus.model;

/**
 *
 * @author Dany
 */
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class TransaksiSirkulasi {
    private static final int TARIF_DENDA_HARIAN = 2000;

    private final String idTransaksi;
    private final LocalDate tanggalPinjam;
    private final LocalDate jatuhTempo;
    private LocalDate tanggalKembali;
    private String status;
    private final Anggota anggota;
    private final Petugas petugas;
    private final List<ItemPeminjaman> daftarItem = new ArrayList<>();
    private Denda denda;

    public TransaksiSirkulasi(String idTransaksi, Petugas petugas, Anggota anggota, LocalDate tanggalPinjam, int lamaPinjamHari) {
        if (anggota == null || !anggota.isAktif()) {
            throw new IllegalStateException("Transaksi ditolak: Anggota tidak ditemukan atau status tidak aktif.");
        }
        if (petugas == null) {
            throw new IllegalArgumentException("Petugas pencatat wajib ada.");
        }
        this.idTransaksi = idTransaksi;
        this.petugas = petugas;
        this.anggota = anggota;
        this.tanggalPinjam = tanggalPinjam;
        this.jatuhTempo = tanggalPinjam.plusDays(lamaPinjamHari);
        this.status = "DIPINJAM";
    }

    public void tambahItem(Buku buku, int jumlah) {
        if (!"DIPINJAM".equals(this.status)) {
            throw new IllegalStateException("Tidak dapat menambah item pada transaksi yang sudah selesai.");
        }
        buku.kurangiStok(jumlah);
        this.daftarItem.add(new ItemPeminjaman(buku, jumlah));
    }

    public void selesaikanPengembalian(LocalDate tglKembali) {
        if ("DIKEMBALIKAN".equals(this.status)) {
            throw new IllegalStateException("Transaksi " + idTransaksi + " sudah dikembalikan sebelumnya.");
        }
        if (daftarItem.isEmpty()) {
            throw new IllegalStateException("Transaksi tidak memiliki item buku.");
        }

        this.tanggalKembali = tglKembali;
        for (ItemPeminjaman item : daftarItem) {
            item.getBuku().tambahStok(item.getJumlah());
        }

        if (tglKembali.isAfter(jatuhTempo)) {
            this.denda = new Denda(TARIF_DENDA_HARIAN);
            this.denda.hitungDenda(jatuhTempo, tglKembali);
        }

        this.status = "DIKEMBALIKAN";
    }

    public String getIdTransaksi() {
        return idTransaksi;
    }

    public String getStatus() {
        return status;
    }

    public Anggota getAnggota() {
        return anggota;
    }

    public Petugas getPetugas() {
        return petugas;
    }

    public LocalDate getTanggalPinjam() {
        return tanggalPinjam;
    }

    public LocalDate getJatuhTempo() {
        return jatuhTempo;
    }

    public LocalDate getTanggalKembali() {
        return tanggalKembali;
    }

    public List<ItemPeminjaman> getDaftarItem() {
        return Collections.unmodifiableList(daftarItem);
    }

    public Denda getDenda() {
        return denda;
    }
}