package com.example.devoir_jee.Validator;


import com.example.devoir_jee.Model.Employe;
import com.example.devoir_jee.Service.EmployeService;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;
import java.util.List;


@FacesValidator(value = "emailValidator")
public class EmailValidator implements Validator {

    private EmployeService empService;


    @Override
    public void validate(FacesContext context, UIComponent component, Object value) throws ValidatorException {
        String email = (String) value;
        if (!isEmailUnique(email)) {
            String errorMessage = "Cet email existe deja. Entrer un email unique";
            FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, errorMessage, errorMessage);
            throw new ValidatorException(message);
        }
    }

    private boolean isEmailUnique(String email) {
        empService = new EmployeService();
        List<Employe> allEmployes = empService.getALL();
        for (Employe emp : allEmployes) {
            if (emp.getEmail().equals(email)) {
                return false;
            }
        }
        return true;
    }

}
