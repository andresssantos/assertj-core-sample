package io.andressantos.assertjcoresample;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class FilteredListTest {

    @Test
    void shouldAssertThatFilteredListHasSizeOne() {
        Transaction transaction1 = Transaction.builder().id(1L).value(BigDecimal.ONE).date(LocalDate.now()).build();
        Transaction transaction2 = Transaction.builder().id(2L).value(BigDecimal.TEN).date(LocalDate.now().minusDays(1)).build();

        List<Transaction> transactionList = List.of(transaction1, transaction2);

        assertThat(transactionList)
                .filteredOn(transaction -> transaction.getId().equals(1L))
                .hasSize(1);
    }

    @Test
    void shouldAssertThatFilteredOnAssertionListContainsExactlyTransaction() {
        Transaction transaction1 = Transaction.builder().id(1L).value(BigDecimal.ONE).date(LocalDate.now()).build();
        Transaction transaction2 = Transaction.builder().id(2L).value(BigDecimal.TEN).date(LocalDate.now().minusDays(1)).build();

        List<Transaction> transactionList = List.of(transaction1, transaction2);

        assertThat(transactionList)
                .filteredOnAssertions(transaction -> assertThat(transaction.getValue()).isLessThan(BigDecimal.TEN))
                .containsExactly(transaction1);
    }

    @Test
    void shouldAssertThatFilteredOnAssertionListDoesNotContainTransaction() {
        Transaction transaction1 = Transaction.builder().id(1L).value(BigDecimal.ONE).date(LocalDate.now()).build();
        Transaction transaction2 = Transaction.builder().id(2L).value(BigDecimal.TEN).date(LocalDate.now().minusDays(1)).build();

        List<Transaction> transactionList = List.of(transaction1, transaction2);

        assertThat(transactionList)
                .filteredOnAssertions(transaction -> assertThat(transaction.getValue()).isLessThan(BigDecimal.TEN))
                .doesNotContain(transaction2);
    }

}