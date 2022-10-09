package org.eexor.debtoptimizer.entity;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import lombok.Data;

import javax.persistence.*;
import java.util.Objects;

@Data
@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        property = "type"
)
@JsonSubTypes({
        @JsonSubTypes.Type(value = Debt.class, name = "debt"),
        @JsonSubTypes.Type(value = Investment.class, name = "investment")
})
@MappedSuperclass
public abstract class Deposit implements Comparable<Deposit> {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    protected Long id = (long) -1;

    @Version
    private Integer version = 0;

    @Column(name = "NAME", nullable = false, unique = true)
    protected String name;

    @Column(name = "INTEREST_RATE", nullable = false)
    protected Double interestRate;

    protected double roundDecimal(double number){
        return (double)Math.round(number*100)/100;
    }

    abstract public double pay(double money);
    abstract public void chargeInterest();
    @Override
    public int hashCode() {
        return this.name.hashCode();
    }
    @Override
    public boolean equals(Object obj) {
        return Objects.equals(this.name, ((Deposit) obj).name);
    }
    
    @Override
    public int compareTo(Deposit o) {
        return Double.compare(o.interestRate, this.interestRate);
    }
    @Override
    abstract public String toString();
}
