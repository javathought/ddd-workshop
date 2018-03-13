package io.github.javathought.clean.bank.model;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.math.BigDecimal;

public class Amount {

    private BigDecimal value;
    private Currency currency;

    public Amount(BigDecimal value, Currency currency) {
        this.value = value;
        this.currency = currency;
    }

    public BigDecimal value() {
        return value;
    }

    public Currency currency() {
        return currency;
    }

    public enum Currency {
        EUR,
        USD
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        Amount amount = (Amount) o;

        return new EqualsBuilder()
                .append(value.doubleValue(), amount.value.doubleValue())
                .append(currency, amount.currency)
                .isEquals();
        // BigDecimal.equals : only if they are equal in
        // * value and scale (thus 2.0 is not equal to 2.00)
        // comparaison attendue : fonctionnelle
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(value.doubleValue())
                .append(currency)
                .toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SIMPLE_STYLE)
                .append("value", value)
                .append("currency", currency)
                .toString();
    }

}
