package io.github.javathought.clean.bank.model;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

public strictfp class Amount {

    private Double value;
    private Currency currency;

    public Amount(Double value, Currency currency) {
        this.value = value;
        this.currency = currency;
    }

    public Double value() {
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
                .append(value, amount.value)
                .append(currency, amount.currency)
                .isEquals();
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
