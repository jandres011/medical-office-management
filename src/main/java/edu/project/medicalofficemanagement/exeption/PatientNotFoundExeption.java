package edu.project.medicalofficemanagement.exeption;

public class PatientNotFoundExeption extends RuntimeException {
    public PatientNotFoundExeption(String message) {
        super(message);
    }
}
