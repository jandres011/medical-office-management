package edu.project.medicalofficemanagement.exeption;

public class MedicalRecordNotFoundExeption extends RuntimeException {
    public MedicalRecordNotFoundExeption(String message) {
        super(message);
    }
}
