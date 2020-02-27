import java.util.List;

public class Department {
    String name;
    String code;
    List<Employer> employees;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public List<Employer> getEmployers() {
        return employees;
    }

    public void setEmployers(List<Employer> employers) {
        this.employees = employers;
    }
}
