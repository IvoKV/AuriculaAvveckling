package BOI;

import java.util.Objects;

public class BrevpathR7 {
    private String patSSN;
    private String brevPath;

    public BrevpathR7(String patSSN, String brevPath) throws BrevpathExceptionR7 {
        try{
            Objects.requireNonNullElse(patSSN, "patSSN är null");
            Objects.requireNonNullElse(brevPath, "brevPath är null");
        }
        catch(Exception exp){
            throw new BrevpathExceptionR7(exp.getMessage());
        }

        this.patSSN = patSSN;
        this.brevPath = brevPath;
    }

    public String getPatSSN() {
        return patSSN;
    }

    public String getBrevPath() {
        return brevPath;
    }

    @Override
    public String toString() {
        return "BrevpathR7{" +
                "patSSN='" + patSSN + '\'' +
                ", brevPath='" + brevPath + '\'' +
                '}';
    }
}
