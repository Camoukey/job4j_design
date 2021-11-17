package ru.job4j.serialization.xml;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.bind.annotation.*;
import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

@XmlRootElement(name = "commutator")
@XmlAccessorType(XmlAccessType.FIELD)
public class Commutator {
    @XmlAttribute
    private boolean state = false;
    @XmlAttribute
    private int numOfPorts;
    @XmlAttribute
    private String name;
    @XmlElement
    private Configuration config;
    @XmlElementWrapper(name = "stateHistory")
    @XmlElement(name = "state")
    private final List<State> stateHistory = new ArrayList<>();

    public Commutator() {
    }

    public Commutator(int numOfPorts, String name, Configuration config) {
        this.numOfPorts = numOfPorts;
        this.name = name;
        this.config = config;
    }

    public void changeState() {
        this.state = !this.state;
        Timestamp ts = new Timestamp(System.currentTimeMillis());
        stateHistory.add(new State(this.state, new SimpleDateFormat("yyyy.MM.dd hh:mm:ss").format(ts)));
    }

    @Override
    public String toString() {
        return "Commutator{"
                + "state=" + state
                + ", numOfPorts=" + numOfPorts
                + ", name='" + name + '\''
                + ", config=" + config
                + ", stateHistory=" + stateHistory
                + '}';
    }

    public static void main(String[] args) throws InterruptedException, JAXBException, IOException {
        Configuration config = new Configuration("127.0.0.1", 300);
        Commutator commutator = new Commutator(24, "Sportivnaya_21_2", config);
        for (int i = 0; i < 5; i++) {
            Thread.sleep(1000);
            commutator.changeState();
        }
        /* Получаем контекст для доступа АПИ */
        JAXBContext context = JAXBContext.newInstance(Commutator.class);
        /* Создаем сериализатор */
        Marshaller marshaller = context.createMarshaller();
        /* Указываем, что нам нужно форматирование */
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
        String xml;
        try (StringWriter writer = new StringWriter()) {
            /* Сериализуем */
            marshaller.marshal(commutator, writer);
            xml = writer.getBuffer().toString();
            System.out.println(xml);
        }
        /* Для десериализации нам нужно создать десериализатор */
        Unmarshaller unmarshaller = context.createUnmarshaller();
        try (StringReader reader = new StringReader(xml)) {
            /* десериализуем */
            Commutator result = (Commutator) unmarshaller.unmarshal(reader);
            System.out.println(result);
        }
    }
}

@XmlRootElement(name = "state")
class State {
    @XmlAttribute
    private boolean currentState;
    @XmlAttribute
    private String changeTime;

    public State() {
    }

    public State(boolean currentState, String changeTime) {
        this.currentState = currentState;
        this.changeTime = changeTime;
    }

    @Override
    public String toString() {
        return "State{"
                + "state=" + currentState
                + ", changeTime=" + changeTime
                + '}';
    }
}

@XmlRootElement(name = "configuration")
class Configuration {
    @XmlAttribute
    private String ip;
    @XmlAttribute
    private int vlan;

    public Configuration() {
    }

    public Configuration(String ip, int vlan) {
        this.ip = ip;
        this.vlan = vlan;
    }

    @Override
    public String toString() {
        return "Configuration{"
                + "ip='" + ip + '\''
                + ", vlan=" + vlan
                + '}';
    }
}
