package edu.project.medicalofficemanagement.exeption;

import lombok.Data;

public class AppointmentAlreadyExistExeption extends RuntimeException {
    public AppointmentAlreadyExistExeption(String message) {
        super(message);
    }
}
