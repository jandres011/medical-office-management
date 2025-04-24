package edu.project.medicalofficemanagement.exeption;

public class DoctorNotFoundExeption extends RuntimeException {
    public DoctorNotFoundExeption(String message) {
        super(message);
    }
}
