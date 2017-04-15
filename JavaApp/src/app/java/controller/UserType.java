package app.java.controller;

public enum UserType {
    Admin, City_Scientist, City_Official;

    @Override
    public String toString() {
        switch(this) {
            case Admin:
                return "Admin";
            case City_Scientist:
                return "City Scientist";
            case City_Official:
                return "City Official";
            default:
                throw new IllegalArgumentException();
        }
    }

}