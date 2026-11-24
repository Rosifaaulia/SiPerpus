/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package siperpus.ui;

/**
 *
 * @author Dany
 */
import siperpus.barcode.InputManualBarcode;
import siperpus.barcode.ScannerFisikBarcode;
import siperpus.model.Anggota;
import siperpus.model.Buku;
import siperpus.model.ItemPeminjaman;
import siperpus.model.RakBuku;
import siperpus.model.TransaksiSirkulasi;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class PerpustakaanView {
    private final SirkulasiController controller;
    private final Scanner input;

    public PerpustakaanView(SirkulasiController controller, Scanner input) {
        this.controller = controller;
        this.input = input;
    }

    public void tampilkanMenuUtama() {
        boolean berjalan = true;
        while (berjalan) {
            System.out.println("\n=======================================================");
            System.out.println("  SISTEM PERPUSTAKAAN KAMPUS TERINTEGRASI BARCODE");
            System.out.println("  Petugas Aktif: " + controller.getPetugasAktif().getNama());
            System.out.println("=======================================================");
            System.out.println("1. Lihat Katalog & Rak Buku");
            System.out.println("2. Transaksi Peminjaman Buku (Scan Barcode)");
            System.out.println("3. Transaksi Pengembalian Buku & Cek Denda");
            System.out.println("4. Laporan Riwayat Sirkulasi");
            System.out.println("5. Ganti Mode Pembaca Barcode (USB Scanner / Manual)");
            System.out.println("0. Keluar");
            System.out.print("Pilih menu [0-5]: ");

            String pilihan = input.nextLine().trim();
            switch (pilihan) {
                case "1":
                    tampilkanKatalog();
                    break;
                case "2":
                    formPeminjaman();
                    break;
                case "3":
                    formPengembalian();
                    break;
                case "4":
                    tampilkanLaporan();
                    break;
                case "5":
                    gantiModeScanner();
                    break;
                case "0":
                    berjalan = false;
                    System.out.println("Terima kasih. Sistem ditutup.");
                    break;
                default:
                    System.out.println("Pilihan tidak valid.");
            }
        }
    }

    private void tampilkanKatalog() {
        System.out.println("\n--- KATALOG KOLEKSI BUKU PER RAK ---");
        for (RakBuku rak : controller.getDaftarRak()) {
            System.out.println("Rak [" + rak.getKodeRak() + "] - " + rak.getLokasi());
            for (Buku b : rak.getDaftarBuku()) {
                System.out.printf("  * Barcode: %-10s | ISBN: %-13s | Judul: %-32s | Stok: %d%n",
                        b.getBarcodeBuku(), b.getIsbn(), b.getJudul(), b.getStok());
            }
        }
    }

    private void formPeminjaman() {
        System.out.println("\n--- PEMINJAMAN BUKU VIA BARCODE ---");
        try {
            System.out.println("Langkah 1: Pindai Barcode Kartu Tanda Mahasiswa (KTM)");
            String kodeKtm = controller.scanBarcode();
            Anggota anggota = controller.cariAnggotaByBarcode(kodeKtm);
            if (anggota == null) {
                System.out.println("[GAGAL] Anggota dengan barcode KTM '" + kodeKtm + "' tidak terdaftar.");
                return;
            }
            System.out.println("-> Pemustaka: " + anggota.getNama() + " (" + anggota.getNim() + ") | Status: " + (anggota.isAktif() ? "AKTIF" : "NON-AKTIF"));

            List<Buku> keranjangBuku = new ArrayList<>();
            while (true) {
                System.out.println("\nLangkah 2: Pindai Barcode Buku (atau ketik 'SELESAI' untuk memproses)");
                String kodeBuku = controller.scanBarcode();
                if ("SELESAI".equalsIgnoreCase(kodeBuku)) {
                    break;
                }
                Buku buku = controller.cariBukuByBarcode(kodeBuku);
                if (buku == null) {
                    System.out.println("[PERINGATAN] Buku dengan barcode '" + kodeBuku + "' tidak ditemukan.");
                } else {
                    keranjangBuku.add(buku);
                    System.out.println("-> Ditambahkan: " + buku.getJudul() + " (Stok saat ini: " + buku.getStok() + ")");
                }
            }

            TransaksiSirkulasi trx = controller.buatPeminjaman(anggota, keranjangBuku);
            System.out.println("\n[BERHASIL] Peminjaman tercatat!");
            System.out.println("ID Transaksi : " + trx.getIdTransaksi());
            System.out.println("Jatuh Tempo  : " + trx.getJatuhTempo());
        } catch (Exception e) {
            System.out.println("[ERROR TRANSAKSI] " + e.getMessage());
        }
    }

    private void formPengembalian() {
        System.out.println("\n--- PENGEMBALIAN BUKU & KALKULASI DENDA ---");
        try {
            System.out.print("Masukkan ID Transaksi (contoh: TRX-001): ");
            String idTrx = input.nextLine().trim();

            System.out.print("Simulasi keterlambatan hari (isi 0 jika tepat waktu, atau angka hari terlambat): ");
            int tambahHari = Integer.parseInt(input.nextLine().trim());
            LocalDate tglKembali = (tambahHari > 0) ? LocalDate.now().plusDays(7 + tambahHari) : LocalDate.now();

            TransaksiSirkulasi trx = controller.kembalikanBuku(idTrx, tglKembali);
            System.out.println("\n[BERHASIL] Buku telah dikembalikan.");
            System.out.println("Status Transaksi : " + trx.getStatus());
            System.out.println("Tanggal Kembali  : " + trx.getTanggalKembali());
            if (trx.getDenda() != null && trx.getDenda().getTotalDenda() > 0) {
                trx.getDenda().tandaiLunas();
                System.out.println("Keterlambatan    : " + trx.getDenda().getHariTerlambat() + " hari");
                System.out.println("Total Denda      : Rp " + trx.getDenda().getTotalDenda() + " (LUNAS)");
            } else {
                System.out.println("Total Denda      : Rp 0 (Tepat Waktu)");
            }
        } catch (Exception e) {
            System.out.println("[ERROR PENGEMBALIAN] " + e.getMessage());
        }
    }

    private void tampilkanLaporan() {
        System.out.println("\n--- LAPORAN RIWAYAT SIRKULASI ---");
        List<TransaksiSirkulasi> daftar = controller.getRiwayatTransaksi();
        if (daftar.isEmpty()) {
            System.out.println("Belum ada transaksi sirkulasi.");
            return;
        }
        for (TransaksiSirkulasi t : daftar) {
            int nominalDenda = (t.getDenda() != null) ? t.getDenda().getTotalDenda() : 0;
            System.out.printf("ID: %-8s | Peminjam: %-22s | Status: %-12s | Denda: Rp %d%n",
                    t.getIdTransaksi(), t.getAnggota().getNama(), t.getStatus(), nominalDenda);
            for (ItemPeminjaman item : t.getDaftarItem()) {
                System.out.println("   -> Buku: " + item.getBuku().getJudul() + " (" + item.getJumlah() + " eks)");
            }
        }
    }

    private void gantiModeScanner() {
        System.out.println("\nPilih Perangkat Input Barcode:");
        System.out.println("1. Scanner Fisik Barcode (USB HID - COM3)");
        System.out.println("2. Input Manual Keyboard");
        System.out.print("Pilihan [1-2]: ");
        String pilih = input.nextLine().trim();
        if ("1".equals(pilih)) {
            controller.gantiPerangkatBarcode(new ScannerFisikBarcode("USB-COM3", input));
            System.out.println("Mode diubah ke Scanner Fisik Barcode.");
        } else if ("2".equals(pilih)) {
            controller.gantiPerangkatBarcode(new InputManualBarcode(input));
            System.out.println("Mode diubah ke Input Manual.");
        }
    }
}