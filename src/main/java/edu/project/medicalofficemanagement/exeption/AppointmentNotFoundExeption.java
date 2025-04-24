package edu.project.medicalofficemanagement.exeption;

public class AppointmentNotFoundExeption extends RuntimeException {
    public AppointmentNotFoundExeption(String message) {
        super(message);
    }
}
