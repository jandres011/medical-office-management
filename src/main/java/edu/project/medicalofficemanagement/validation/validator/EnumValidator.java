package edu.project.medicalofficemanagement.validation.validator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import edu.project.medicalofficemanagement.validation.annotation.EnumValid;

public class EnumValidator implements ConstraintValidator<EnumValid, Enum<?>> {
    private Class<? extends Enum<?>> enumClass;

    @Override
    public void initialize(EnumValid annotation) {
        this.enumClass = annotation.value();
    }

    @Override
    public boolean isValid(Enum<?> value, ConstraintValidatorContext context) {
        if (value == null) {
            return false; // Evita valores nulos
        }

        // Verifica que el valor existe en el enum
        for (Enum<?> enumConstant : enumClass.getEnumConstants()) {
            if (enumConstant.name().equals(value.name())) {
                return true;
            }
        }
        return false;
    }
}
