/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package siperpus.model;

/**
 *
 * @author Dany
 */
public class Anggota extends Pengguna {
    private final String nim;
    private final String barcodeKtm;
    private boolean aktif;

    public Anggota(String idPengguna, String nama, String nim, String barcodeKtm, boolean aktif) {
        super(idPengguna, nama);
        if (nim == null || nim.isBlank()) {
            throw new IllegalArgumentException("NIM anggota wajib diisi.");
        }
        if (barcodeKtm == null || barcodeKtm.isBlank()) {
            throw new IllegalArgumentException("Barcode KTM wajib diisi.");
        }
        this.nim = nim;
        this.barcodeKtm = barcodeKtm;
        this.aktif = aktif;
    }

    public boolean isAktif() {
        return aktif;
    }

    public void setAktif(boolean aktif) {
        this.aktif = aktif;
    }

    public String getNim() {
        return nim;
    }

    public String getBarcodeKtm() {
        return barcodeKtm;
    }

    @Override
    public String getPeran() {
        return "Anggota Pemustaka";
    }
}
