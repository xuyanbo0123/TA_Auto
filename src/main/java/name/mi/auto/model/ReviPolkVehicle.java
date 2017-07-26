package name.mi.auto.model;

import name.mi.auto.basic.VehicleYear;

public class ReviPolkVehicle {
    long mID;
    private VehicleYear mYear;
    private String mMake;
    private String mModel;

    public ReviPolkVehicle(long iID, VehicleYear iYear, String iMake, String iModel) {
        mID = iID;
        mYear = iYear;
        mMake = iMake;
        mModel = iModel;
    }

    public long getID() {
        return mID;
    }

    public void setID(long iID) {
        mID = iID;
    }

    public VehicleYear getYear() {
        return mYear;
    }

    public void setYear(VehicleYear iYear) {
        mYear = iYear;
    }

    public String getMake() {
        return mMake;
    }

    public void setMake(String iMake) {
        mMake = iMake;
    }

    public String getModel() {
        return mModel;
    }

    public void setModel(String iModel) {
        mModel = iModel;
    }



    @Override
    public String toString() {
        return  mYear +
                " " + mMake.replaceAll(" ", "") +
                " " + mModel.replaceAll(" ", "");
    }
}
