package com.IshanPhadteReserveMate.ReserveMate.Model;


public class ReservationTable {
    private Table table;
    private Reservation reservation;

    public ReservationTable(Table table, Reservation reservation) {
        this.table = table;
        this.reservation = reservation;
    }
}
