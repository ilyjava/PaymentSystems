package com.example.admin.paymentsystems.model;

public class CarData {

    private static CarData instance;

    private String mCarNumber;
    private String mStsNumber;
    private String mDriverLicenseNumber;

    public static String  notEntered = "Пользователь не ввел данные";

    public static boolean passedWizard = false;

    public static CarData getInstance() {
        if (instance == null) {
            instance = new CarData();
        }
        return instance;
    }

    public String getmCarNumber() {
        return mCarNumber;
    }

    public void setmCarNumber(String mCarNumber) {
        this.mCarNumber = mCarNumber;
    }

    public String getmStsNumber() {
        return mStsNumber;
    }

    public void setmStsNumber(String mStsNumber) {
        this.mStsNumber = mStsNumber;
    }

    public String getmDriverLicenseNumber() {
        return mDriverLicenseNumber;
    }

    public void setmDriverLicenseNumber(String mDriverLicenseNumber) {
        this.mDriverLicenseNumber = mDriverLicenseNumber;
    }
    public static String getNotEntered() {
        return notEntered;
    }

    public static boolean isPassedWizard() {
        return passedWizard;
    }

    public static void setPassedWizard(boolean passedWizard) {
        CarData.passedWizard = passedWizard;
    }
}