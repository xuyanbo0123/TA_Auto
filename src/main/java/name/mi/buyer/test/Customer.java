package name.mi.buyer.test;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="PriceElement")
public class Customer {
    String name;
    int age;
    int id;

    @XmlElement
    public String getName() {
        return name;
    }


    public void setName(String name) {
        this.name = name;
    }

    @XmlElement
    public int getAge() {
        return age;
    }


    public void setAge(int age) {
        this.age = age;
    }

    @XmlAttribute(name="testID")
    public int getId() {
        return 100;
    }

    public void setId(int iId) {
        id = iId;
    }
}
