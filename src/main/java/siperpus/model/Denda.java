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
import java.time.temporal.ChronoUnit;

public class Denda {
    private int hariTerlambat;
    private final int tarifPerHari;
    private int totalDenda;
    private boolean lunas;

    Denda(int tarifPerHari) {
        this.tarifPerHari = tarifPerHari;
        this.lunas = false;
    }

    public int hitungDenda(LocalDate jatuhTempo, LocalDate tglKembali) {
        if (tglKembali.isAfter(jatuhTempo)) {
            this.hariTerlambat = (int) ChronoUnit.DAYS.between(jatuhTempo, tglKembali);
            this.totalDenda = this.hariTerlambat * this.tarifPerHari;
        } else {
            this.hariTerlambat = 0;
            this.totalDenda = 0;
        }
        return this.totalDenda;
    }

    public void tandaiLunas() {
        this.lunas = true;
    }

    public int getTotalDenda() {
        return totalDenda;
    }

    public int getHariTerlambat() {
        return hariTerlambat;
    }

    public boolean isLunas() {
        return lunas;
    }
}