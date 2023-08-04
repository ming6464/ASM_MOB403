package com.fpoly.assigment_mob403.DTO;

public class Result {
    private boolean result;
    private String mes;
    private User user;

    public Result(boolean result, String mes, User user) {
        this.result = result;
        this.mes = mes;
        this.user = user;
    }

    public Result() {
    }

    public boolean isResult() {
        return result;
    }

    public void setResult(boolean result) {
        this.result = result;
    }

    public String getMes() {
        return mes;
    }

    public void setMes(String mes) {
        this.mes = mes;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public String toString() {
        return "Result{" +
                "result=" + result +
                ", mes='" + mes + '\'' +
                ", user=" + user +
                '}';
    }
}
