package app.java.model;


/**
 * UserType enum
 */
public enum UserType {
    Admin, City_Scientist, City_Official;

    @Override
    public String toString() {
        switch(this) {
            case Admin:
                return "Admin";
            case City_Scientist:
                return "City_Scientist";
            case City_Official:
                return "City_Official";
            default:
                throw new IllegalArgumentException();
        }
    }

}