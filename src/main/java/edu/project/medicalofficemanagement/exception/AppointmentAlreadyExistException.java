package edu.project.medicalofficemanagement.exception;

public class AppointmentAlreadyExistException extends RuntimeException {
    public AppointmentAlreadyExistException(String message) {
        super(message);
    }
}
