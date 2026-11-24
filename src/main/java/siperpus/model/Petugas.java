/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package siperpus.model;

/**
 *
 * @author Dany
 */
public class Petugas extends Pengguna {
    private final String username;
    private final String passwordHash;

    public Petugas(String idPengguna, String nama, String username, String passwordHash) {
        super(idPengguna, nama);
        if (username == null || username.isBlank()) {
            throw new IllegalArgumentException("Username petugas wajib diisi.");
        }
        this.username = username;
        this.passwordHash = passwordHash;
    }

    public boolean autentikasi(String password) {
        return password != null && password.equals(this.passwordHash);
    }

    public String getUsername() {
        return username;
    }

    @Override
    public String getPeran() {
        return "Petugas Perpustakaan";
    }
}
