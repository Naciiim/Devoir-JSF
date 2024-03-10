package com.example.devoir_jee.Service;

import com.example.devoir_jee.Dao.EmployeDaoImpl;
import com.example.devoir_jee.Model.Employe;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.validator.ValidatorException;

import com.example.devoir_jee.Validator.EmailValidator;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;


@Data
@AllArgsConstructor
@ManagedBean
@SessionScoped
@RequestScoped
public class EmployeService {

    private EmployeDaoImpl empDao;
    private EmailValidator emailValidator;
    List<Employe> employeList;
    private Map<Employe, Boolean> editModeList;
    private int editedEmpId;
    private Employe editedEmploye;
    public static int i=0;
    private String lang="fr";

    List<Employe> fullEmployeList;
    private static boolean form;

    //searchbar :
//    private String searchQuery;
//    public String searchEmployees() {
//        if (searchQuery == null || searchQuery.trim().isEmpty()) {
//            FacesContext context = FacesContext.getCurrentInstance();
//            context.getExternalContext().invalidateSession();
//        } else {
//            fullEmployeList = new ArrayList<>();
//            fullEmployeList = this.getALL();
//            employeList = fullEmployeList.stream()
//                    .filter(e -> e.getName().toLowerCase().contains(searchQuery.toLowerCase()))
//                    .collect(Collectors.toList());
//        }
//        return "listeEmployes.xhtml";
//    }


    public EmployeService(){
        this.empDao = new EmployeDaoImpl();
        this.emailValidator = new EmailValidator();
    }

    @PostConstruct
    public void init() {
        this.employeList = new ArrayList<>();
        this.employeList = this.models();
        this.editModeList = new HashMap<>();
        for (Employe employe :employeList) {
            editModeList.put(employe,false);
        }
        this.editedEmploye = new Employe();
        Map<String, String> params = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
        String langParam = params.get("lang");
        if (langParam != null) {
            this.lang = langParam;
        }
        setLang(this.lang);
    }

    public void setLang(String lang) {
        this.lang=lang;
        FacesContext.getCurrentInstance().getViewRoot().setLocale(new Locale(lang));
    }

    public void deleteEmp(String empId){
        String lang = this.lang;
        empDao.delete(Integer.parseInt(empId));
        FacesContext context = FacesContext.getCurrentInstance();
        context.getExternalContext().invalidateSession();
        try {
            setLang(lang);
            FacesContext.getCurrentInstance().getExternalContext().redirect("listeEmployes.xhtml?lang=" + lang);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void updateEmp(Employe employe) throws IOException {
        String lang = this.lang;
        Employe editedEmploye = empDao.getEmployeById(editedEmpId);
        editedEmploye.setName(employe.getName());
        editedEmploye.setDepartement(employe.getDepartement());
        editedEmploye.setBirthday(employe.getBirthday());
        editedEmploye.setEmail(employe.getEmail());
        empDao.update(editedEmploye);
        FacesContext context = FacesContext.getCurrentInstance();
        context.getExternalContext().invalidateSession();
        setLang(lang);
        FacesContext.getCurrentInstance().getExternalContext().redirect("listeEmployes.xhtml?lang=" + lang);
    }

    public void addEmp(Employe employe) {
        try {
            lang = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("lang");
            emailValidator.validate(FacesContext.getCurrentInstance(), null, employe.getEmail());
            empDao.add(employe);
            FacesContext context = FacesContext.getCurrentInstance();
            context.getExternalContext().invalidateSession();
            form = !form;
            setLang(lang);
            FacesContext.getCurrentInstance().getExternalContext().redirect("listeEmployes.xhtml?lang=" + lang);
        } catch (ValidatorException e) {
            FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, e.getMessage(), null);
            FacesContext.getCurrentInstance().addMessage(null, message);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    public List<Employe> getALL() {
        System.out.println(empDao.getAllEmployes());
        return empDao.getAllEmployes();
    }

    List<Employe> getNextTen(int i){
        return  getALL().subList(Math.max(7*i,0),Math.min(7*i+7, getALL().size()));
    }

    public List<Employe> models() {
        return this.getNextTen(i);
    }

    public void switchNextPage() throws IOException {
        lang = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("lang");
        addOneToI();
        setLang(lang);
        FacesContext.getCurrentInstance().getExternalContext().redirect("listeEmployes.xhtml?lang=" + lang);
    }

    public void switchBackPage() throws IOException {
        lang = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("lang");
        subsOneToI();
        setLang(lang);
        FacesContext.getCurrentInstance().getExternalContext().redirect("listeEmployes.xhtml?lang=" + lang);
    }

    public void addOneToI(){
        if((models().size()/7)>=i)
            i=i+1;
    }

    public void subsOneToI(){
        if((models().size()/7)<=i)
            i=i-1;
    }


    public String toggleEditMode(Employe employe) {
        boolean editedEmploye = editModeList.get(employe);
        editModeList.put(employe, !editedEmploye);
        editedEmpId = employe.getEmpId();
        return "listeEmployes.xhtml";
    }



    public void switchForm(){
        form = !form;
        setLang(FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("lang"));
        System.out.println(lang);
    }


    public Boolean getForm() {
        return form;
    }
}
