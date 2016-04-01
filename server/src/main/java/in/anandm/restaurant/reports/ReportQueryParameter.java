package in.anandm.restaurant.reports;

public class ReportQueryParameter {

    private String name;

    private Object defaultValue;

    /**
     * 
     */
    public ReportQueryParameter() {
        super();

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Object getDefaultValue() {
        return defaultValue;
    }

    public void setDefaultValue(Object defaultValue) {
        this.defaultValue = defaultValue;
    }

}
