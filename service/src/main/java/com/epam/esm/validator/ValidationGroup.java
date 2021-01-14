package com.epam.esm.validator;

import javax.validation.groups.Default;

public class ValidationGroup {
    public interface CreateValidation extends Default {
    }

    public interface UpdateValidation extends Default {
    }
}
