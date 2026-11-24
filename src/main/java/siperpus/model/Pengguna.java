/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package siperpus.model;

/**
 *
 * @author Dany
 */
public abstract class Pengguna {
    private final String idPengguna;
    private final String nama;

    protected Pengguna(String idPengguna, String nama) {
        if (idPengguna == null || idPengguna.isBlank()) {
            throw new IllegalArgumentException("ID Pengguna tidak boleh kosong.");
        }
        if (nama == null || nama.isBlank()) {
            throw new IllegalArgumentException("Nama pengguna tidak boleh kosong.");
        }
        this.idPengguna = idPengguna;
        this.nama = nama;
    }

    public String getIdPengguna() {
        return idPengguna;
    }

    public String getNama() {
        return nama;
    }

    public abstract String getPeran();
}